package ro.repository.interfaces;

import ro.entity.Artist;
import ro.exceptions.FindException;

/**
 * Interface that provides all the specific (and CRUD) operations performed on the {@link Artist} repository.
 */
public interface ArtistRepository extends Repository<Artist> {
    Long getArtistCount() throws FindException;
}
