package ro.factory;

import ro.repository.interfaces.AlbumRepository;
import ro.repository.interfaces.ArtistRepository;
import ro.repository.interfaces.GenreRepository;
import ro.repository.interfaces.PlaylistRepository;
import ro.repository.jdbc.AlbumRepositoryImpl;
import ro.repository.jdbc.ArtistRepositoryImpl;
import ro.repository.jdbc.GenreRepositoryImpl;
import ro.repository.jdbc.PlaylistRepositoryImpl;

import java.sql.Connection;

/**
 * This class implements the {@link RepositoryFactory} interfaces and provides
 * JDBC implementations for the {@link AlbumRepository}, {@link ArtistRepository}, {@link GenreRepository} and {@link PlaylistRepository}
 * interfaces.
 *
 * @author Alex Neagu
 */
public class JdbcRepositoryFactory implements RepositoryFactory {
    private final Connection con;

    public JdbcRepositoryFactory(Connection con) {
        this.con = con;
    }

    @Override
    public AlbumRepository createAlbumRepository() {
        return new AlbumRepositoryImpl(con);
    }

    @Override
    public ArtistRepository createArtistRepository() {
        return new ArtistRepositoryImpl(con);
    }

    @Override
    public GenreRepository createGenreRepository() {
        return new GenreRepositoryImpl(con);
    }

    @Override
    public PlaylistRepository createPlaylistRepository() {
        return new PlaylistRepositoryImpl(con);
    }
}
