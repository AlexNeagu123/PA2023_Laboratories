package ro.repository.jpa;

import jakarta.persistence.EntityManager;
import ro.entity.Artist;
import ro.repository.interfaces.ArtistRepository;

/**
 * DAO class responsible
 * for executing {@link Artist} related queries
 * in the database (using JPA).
 *
 * @author Alex Neagu
 */
public class ArtistRepositoryImpl extends AbstractRepository<Artist> implements ArtistRepository {
    public ArtistRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Artist.class);
    }

    @Override
    public Long getArtistCount() {
        return (Long) entityManager.createNamedQuery("Artist.getCount").getSingleResult();
    }
}
