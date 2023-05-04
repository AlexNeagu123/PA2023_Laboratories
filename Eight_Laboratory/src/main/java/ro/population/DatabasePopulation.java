package ro.population;

import ro.controllers.AlbumController;
import ro.controllers.ArtistController;
import ro.controllers.GenreController;
import ro.exceptions.CreateException;
import ro.exceptions.FindException;
import ro.models.Album;
import ro.models.Artist;
import ro.models.Genre;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * The <tt>DatabasePopulation</tt> class is responsible for populating the database
 * with data extracted from a .csv file - albumlist.csv
 *
 * @see <a href="https://www.kaggle.com/datasets/notgibs/500-greatest-albums-of-all-time-rolling-stone">The source of the .csv file</a>
 */
public class DatabasePopulation {
    private final Scanner scanner;
    private final ArtistController artistController;
    private final AlbumController albumController;
    private final GenreController genreController;

    public DatabasePopulation(Connection con, String csvFilePath) throws FileNotFoundException {
        this.artistController = new ArtistController(con);
        this.albumController = new AlbumController(con);
        this.genreController = new GenreController(con);
        this.scanner = new Scanner(new File(csvFilePath));
    }

    private Artist mapToArtist(String name) throws FindException, CreateException {
        Artist artist = artistController.findByName(name);
        if (artist == null) {
            artist = artistController.create(new Artist(name));
        }
        return artist;
    }

    private Genre mapToGenre(String name) throws FindException, CreateException {
        Genre genre = genreController.findByName(name);
        if (genre == null) {
            genre = genreController.create(new Genre(name));
        }
        return genre;
    }

    /**
     * @param genreName      The token corresponding to the main genre
     * @param subgenresToken The token corresponding to subgenres
     * @return A list of {@link Genre} objects that results after the token parsing
     */
    private List<Genre> obtainGenres(String genreName, String subgenresToken) throws FindException, CreateException {
        List<String> genreNames = new ArrayList<>();
        genreNames.addAll(
                Arrays.asList(genreName.replaceAll("\"", "").split(","))
        );
        genreNames.addAll(
                Arrays.asList(subgenresToken.replaceAll("\"", "").split(","))
        );
        genreNames.replaceAll(String::trim);

        List<Genre> genreList = new ArrayList<>();
        // iterating only through distinct genre names
        for (String name : genreNames.stream().distinct().toList()) {
            genreList.add(mapToGenre(name));
        }
        return genreList;
    }

    /**
     * Split the .csv file into tokens using a regex expression.
     * <p>
     * Each token is then used to create object models that are finally inserted into the database.
     */
    public void populate() throws CreateException, FindException {
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            Artist artist = mapToArtist(tokens[3]);
            List<Genre> genresList = obtainGenres(tokens[4], tokens[5]);
            albumController.create(new Album(Integer.parseInt(tokens[1]), tokens[2], artist, genresList));
        }
    }
}
