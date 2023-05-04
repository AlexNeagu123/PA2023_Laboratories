package ro.utils;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.jgrapht.Graph;
import org.jgrapht.alg.clique.BronKerboschCliqueFinder;
import org.jgrapht.generate.ComplementGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import ro.controllers.AlbumController;
import ro.controllers.PlaylistController;
import ro.exceptions.CreateException;
import ro.exceptions.FindException;
import ro.models.Album;
import ro.models.Genre;
import ro.models.Playlist;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is responsible for building playlists (sometimes more than one) that contains the maximum number of
 * <b>unrelated</b> albums.
 * <p>
 * Two albums are called <tt>related</tt> if they share the same artist, the same release year or some common genres.
 * <p>
 * A graph is built, where <tt>Albums</tt> are viewed as nodes and relationships as edges.
 * <p>
 * Bron-Kerbosch Algorithm for finding all cliques in a graph is used on the <b>complement</b> graph, and playlists are
 * created one by one. For performance reasons, the number of albums in the graph and the number of generated playlists are
 * limited to 100.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Bron%E2%80%93Kerbosch_algorithm">Bron-Kerbosch algorithm</a>
 */
@Log4j2
public class PlaylistBuilder {
    private final Connection con;
    private final Graph<Album, DefaultEdge> graph;
    private static final int PLAYLIST_LIMIT = 100;
    private static final int ALBUM_LIMIT = 100;
    PlaylistController playlistController;

    public PlaylistBuilder(Connection con) throws FindException {
        this.con = con;
        this.graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        playlistController = new PlaylistController(con);
        initializeGraph();
    }

    private int countCommonGenres(Album firstAl, Album secondAl) {
        Set<Integer> firstAlGenreIds = new HashSet<>();
        for (Genre genre : firstAl.getGenres()) {
            firstAlGenreIds.add(genre.getId());
        }
        int commonCount = 0;
        for (Genre genre : secondAl.getGenres()) {
            if (firstAlGenreIds.contains(genre.getId())) {
                commonCount++;
            }
        }
        return commonCount;
    }

    private boolean areRelatedAlbums(Album firstAl, Album secondAl) {
        return (firstAl.getArtist().getId() == secondAl.getArtist().getId()
                || firstAl.getReleaseYear() == secondAl.getReleaseYear()
                || countCommonGenres(firstAl, secondAl) > 0);
    }

    private void initializeGraph() throws FindException {
        AlbumController albumController = new AlbumController(con);
        List<Album> albums = albumController.findAll();
        albums = albums.subList(0, Math.min(ALBUM_LIMIT, albums.size()));
        for (Album album : albums) {
            graph.addVertex(album);
        }
        for (int i = 0; i < albums.size(); ++i) {
            for (int j = i + 1; j < albums.size(); ++j) {
                Album source = albums.get(i), destination = albums.get(j);
                if (areRelatedAlbums(source, destination)) {
                    graph.addEdge(source, destination);
                }
            }
        }
    }

    public void buildMany() throws CreateException {
        Faker faker = new Faker();

        ComplementGraphGenerator<Album, DefaultEdge> complementGenerator = new ComplementGraphGenerator<>(graph);
        Graph<Album, DefaultEdge> complementGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        complementGenerator.generateGraph(complementGraph);

        BronKerboschCliqueFinder<Album, DefaultEdge> cliqueFinder = new BronKerboschCliqueFinder<>(complementGraph);
        int cliquesFound = 0;

        for (Set<Album> clique : cliqueFinder) {
            Playlist playlist = new Playlist(faker.name().username(), new Timestamp(System.currentTimeMillis()), new ArrayList<>(clique));
            playlistController.create(playlist);
            log.info("New playlist inserted into the database: " + playlist);
            cliquesFound++;
            if (cliquesFound == PLAYLIST_LIMIT) {
                return;
            }
        }
    }
}
