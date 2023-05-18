package ro.repository.jpa;

import jakarta.persistence.EntityManager;
import ro.entity.Genre;
import ro.repository.interfaces.GenreRepository;

import java.util.List;

/**
 * DAO class responsible
 * for executing {@link Genre} related queries
 * in the database (using JPA).
 *
 * @author Alex Neagu
 */
public class GenreRepositoryImpl extends AbstractRepository<Genre> implements GenreRepository {
    public GenreRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Genre.class);
    }

    public List<Genre> findByAlbumId(int albumId) {
        return entityManager.createNamedQuery("Genre.findByAlbumId").setParameter("id", albumId).getResultList();
    }

    @Override
    public Long getGenreCount() {
        return (Long) entityManager.createNamedQuery("Genre.getCount").getSingleResult();
    }
}
