package ro.repository.interfaces;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.exceptions.ConnectionException;
import ro.factory.JdbcRepositoryFactory;
import ro.factory.JpaRepositoryFactory;
import ro.factory.RepositoryFactory;
import ro.repository.jdbc.db.Database;
import ro.repository.jpa.manager.EntityManagerSingleton;

import java.sql.Connection;

@Transactional
class AlbumRepositoryTest {

    AlbumRepository albumRepositoryJpa;

    AlbumRepository albumRepositoryJdbc;

    @BeforeEach
    public void setup() throws ConnectionException {
        EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManagerFactory().createEntityManager();
        RepositoryFactory repositoryFactoryJpa = new JpaRepositoryFactory(entityManager);
        this.albumRepositoryJpa = repositoryFactoryJpa.createAlbumRepository();

        Connection connection = Database.getInstance().getConnection();
        RepositoryFactory repositoryFactoryJdbc = new JdbcRepositoryFactory(connection);
        this.albumRepositoryJdbc = repositoryFactoryJdbc.createAlbumRepository();
    }

    @Test
    public void givenJpaAlbumRepositoryImpl_whenFindAllAlbumsCalled_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJpa.findAll().size());
        });
    }

    @Test
    public void givenJdbcAlbumRepositoryImpl_whenFindAllAlbumsCalled_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJdbc.findAll().size());
        });
    }

    @Test
    public void givenJpaAlbumRepositoryImpl_whenFindByArtistId2Called_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJpa.findByArtistId(2).size());
        });
    }

    @Test
    public void givenJdbcAlbumRepositoryImpl_whenFindByArtistId2Called_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJdbc.findByArtistId(2).size());
        });
    }

    @Test
    public void givenJpaAlbumRepositoryImpl_whenFindByGenreId2Called_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJpa.findByGenreId(2).size());
        });
    }

    @Test
    public void givenJdbcAlbumRepositoryImpl_whenFindByGenreId2Called_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJdbc.findByGenreId(2).size());
        });
    }

    @Test
    public void givenJpaAlbumRepositoryImpl_whenFindById43Called_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJpa.findById(43));
        });
    }

    @Test
    public void givenJdbcAlbumRepositoryImpl_whenFindById43Called_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(albumRepositoryJdbc.findById(43));
        });
    }
}