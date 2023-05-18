package ro.repository.interfaces;

import jakarta.persistence.EntityManager;
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

class ArtistRepositoryTest {
    ArtistRepository artistRepositoryJpa;

    ArtistRepository artistRepositoryJdbc;

    @BeforeEach
    public void setup() throws ConnectionException {
        EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManagerFactory().createEntityManager();
        RepositoryFactory repositoryFactoryJpa = new JpaRepositoryFactory(entityManager);
        this.artistRepositoryJpa = repositoryFactoryJpa.createArtistRepository();

        Connection connection = Database.getInstance().getConnection();
        RepositoryFactory repositoryFactoryJdbc = new JdbcRepositoryFactory(connection);
        this.artistRepositoryJdbc = repositoryFactoryJdbc.createArtistRepository();
    }

    @Test
    public void givenJpaAlbumRepositoryImpl_whenGetArtistCountCalled_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(artistRepositoryJpa.getArtistCount());
        });
    }

    @Test
    public void givenJdbcAlbumRepositoryImpl_whenGetArtistCountCalled_thenShouldRun() {
        Assertions.assertDoesNotThrow(() -> {
            System.out.println(artistRepositoryJdbc.getArtistCount());
        });
    }
}