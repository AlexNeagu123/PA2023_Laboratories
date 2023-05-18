package ro.factory;

import ro.repository.interfaces.*;

/**
 * Interface for the <tt>RepositoryFactory</tt>
 * in order to create the repositories.
 *
 * @author Alex Neagu
 */
public interface RepositoryFactory {
    AlbumRepository createAlbumRepository();

    ArtistRepository createArtistRepository();

    GenreRepository createGenreRepository();

    PlaylistRepository createPlaylistRepository();
}
