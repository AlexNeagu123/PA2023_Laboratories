package ro.repository.interfaces;

import ro.entity.Genre;
import ro.exceptions.FindException;

import java.util.List;

/**
 * Interface that provides all the specific (and CRUD) operations performed on the {@link Genre} repository.
 */
public interface GenreRepository extends Repository<Genre> {

    /**
     * Finds all genres that corresponds to a specific {@link ro.entity.Album}
     *
     * @param albumId The id of the specified <tt>Album</tt>
     * @return A list of all the genres that corresponds to the <tt>Album</tt> entity with the {@code albumId}
     */
    List<Genre> findByAlbumId(int albumId) throws FindException;

    Long getGenreCount() throws FindException;
}
