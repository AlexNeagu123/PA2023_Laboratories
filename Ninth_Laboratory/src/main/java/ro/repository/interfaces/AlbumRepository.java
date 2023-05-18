package ro.repository.interfaces;

import ro.entity.Album;
import ro.exceptions.FindException;

import java.util.List;

/**
 * Interface that provides all the specific (and CRUD) operations performed on the {@link Album} repository.
 */
public interface AlbumRepository extends Repository<Album> {

    /**
     * Finds all albums that have been written by a specific {@link ro.entity.Artist}
     *
     * @param artistId The id of the specified <tt>Artist</tt>
     * @return A list of all the albums written by the <tt>Artist</tt> with id equals to {@code artistId}
     */
    List<Album> findByArtistId(int artistId) throws FindException;

    /**
     * Finds all albums that have a specific {@link ro.entity.Genre}
     *
     * @param genreId The id of the specified <tt>Genre</tt>
     * @return A list of all the albums that contains the <tt>Genre</tt> with id equals to {@code artistId}
     */
    List<Album> findByGenreId(int genreId) throws FindException;

    /**
     * Finds all albums from a specific {@link ro.entity.Playlist}
     *
     * @param playlistId The id of the specified <tt>Playlist</tt>
     * @return A list of all the albums that are contained in the <tt>Playlist</tt> with id equals to {@code playlistId}
     */
    List<Album> findByPlaylistId(int playlistId) throws FindException;
}
