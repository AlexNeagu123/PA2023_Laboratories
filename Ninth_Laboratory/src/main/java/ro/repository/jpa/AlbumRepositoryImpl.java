package ro.repository.jpa;

import jakarta.persistence.EntityManager;
import ro.entity.Album;
import ro.repository.interfaces.AlbumRepository;

import java.util.List;

/**
 * DAO class responsible
 * for executing {@link Album} related queries
 * in the database (using JPA).
 *
 * @author Alex Neagu
 */
public class AlbumRepositoryImpl extends AbstractRepository<Album> implements AlbumRepository {
    public AlbumRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Album.class);
    }

    public List<Album> findByArtistId(int artistId) {
        return entityManager.createNamedQuery("Album.findByArtist").setParameter("id", artistId).getResultList();
    }

    public List<Album> findByGenreId(int genreId) {
        return entityManager.createNamedQuery("Album.findByGenre").setParameter("id", genreId).getResultList();
    }

    public List<Album> findByPlaylistId(int playlistId) {
        return entityManager.createNamedQuery("Album.findByPlaylist").setParameter("id", playlistId).getResultList();
    }
}
