package ro.repository.jpa;

import jakarta.persistence.EntityManager;
import ro.entity.Playlist;
import ro.repository.interfaces.PlaylistRepository;

/**
 * DAO class responsible
 * for executing {@link Playlist} related queries
 * in the database (using JPA).
 *
 * @author Alex Neagu
 */
public class PlaylistRepositoryImpl extends AbstractRepository<Playlist> implements PlaylistRepository {
    public PlaylistRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Playlist.class);
    }
}
