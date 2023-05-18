package ro.utils;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ro.entity.Album;
import ro.entity.Artist;
import ro.entity.Genre;
import ro.exceptions.ConnectionException;
import ro.exceptions.CreateException;
import ro.exceptions.FindException;
import ro.factory.JdbcRepositoryFactory;
import ro.factory.JpaRepositoryFactory;
import ro.factory.RepositoryFactory;
import ro.repository.interfaces.AlbumRepository;
import ro.repository.interfaces.ArtistRepository;
import ro.repository.interfaces.GenreRepository;
import ro.repository.jdbc.db.Database;
import ro.repository.jpa.manager.EntityManagerSingleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

/**
 * This class contains static methods for inserting {@link Artist}, {@link Album} and {@link Genre} methods using both,
 * <tt>JDBC</tt> and <tt>JPA</tt> repositories.
 * <p>
 * For demonstration purposes only, the methods are implemented in a similar way, the only difference being the <tt>Repository</tt> used.
 */
@Log4j2
public class InsertionUtils {
    public static void insertArtistsWithJPA(int artistCount) {
        try {
            EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManagerFactory().createEntityManager();
            RepositoryFactory repositoryFactory = new JpaRepositoryFactory(entityManager);
            ArtistRepository artistRepository = repositoryFactory.createArtistRepository();
            Faker faker = new Faker();
            for (int i = 0; i < artistCount; ++i) {
                Artist artist = artistRepository.create(new Artist(faker.name().fullName()));
                log.info("A new artist have been inserted into the database (JPA implementation): " + artist);
            }
        } catch (CreateException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void insertArtistsWithJDBC(int artistCount) {
        try {
            Connection con = Database.getInstance().getConnection();
            RepositoryFactory repositoryFactory = new JdbcRepositoryFactory(con);
            ArtistRepository artistRepository = repositoryFactory.createArtistRepository();
            Faker faker = new Faker();
            for (int i = 0; i < artistCount; ++i) {
                Artist artist = artistRepository.create(new Artist(faker.name().fullName()));
                log.info("A new artist have been inserted into the database (JDBC implementation): " + artist);
            }
            con.commit();
        } catch (ConnectionException | CreateException | SQLException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void insertGenresWithJPA(int genreCount) {
        try {
            EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManagerFactory().createEntityManager();
            RepositoryFactory repositoryFactory = new JpaRepositoryFactory(entityManager);
            GenreRepository genreRepository = repositoryFactory.createGenreRepository();
            Faker faker = new Faker();
            for (int i = 0; i < genreCount; ++i) {
                Genre genre = genreRepository.create(new Genre(faker.music().genre()));
                log.info("A new genre have been inserted into the database (JPA implementation): " + genre);
            }
        } catch (CreateException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void insertGenresWithJDBC(int genreCount) {
        try {
            Connection con = Database.getInstance().getConnection();
            RepositoryFactory repositoryFactory = new JdbcRepositoryFactory(con);
            GenreRepository genreRepository = repositoryFactory.createGenreRepository();
            Faker faker = new Faker();
            for (int i = 0; i < genreCount; ++i) {
                Genre genre = genreRepository.create(new Genre(faker.music().genre()));
                log.info("A new genre have been inserted into the database (JDBC implementation): " + genre);
            }
        } catch (ConnectionException | CreateException ex) {
            log.error(ex.getMessage());
        }
    }

    private static Artist extractRandomArtist(ArtistRepository artistRepository) throws FindException {
        Long artistCount = artistRepository.getArtistCount();
        int artistId = (int) (Math.random() * (artistCount - 1) + 1);
        return artistRepository.findById(artistId);
    }

    private static Genre extractRandomGenre(GenreRepository genreRepository) throws FindException {
        Long genreCount = genreRepository.getGenreCount();
        int genreId = (int) (Math.random() * (genreCount - 1) + 1);
        return genreRepository.findById(genreId);
    }

    public static void insertAlbumsWithJPA(int albumCount) {
        try {
            EntityManager entityManager = EntityManagerSingleton.getInstance().getEntityManagerFactory().createEntityManager();
            RepositoryFactory repositoryFactory = new JpaRepositoryFactory(entityManager);
            AlbumRepository albumRepository = repositoryFactory.createAlbumRepository();
            ArtistRepository artistRepository = repositoryFactory.createArtistRepository();
            GenreRepository genreRepository = repositoryFactory.createGenreRepository();
            Faker faker = new Faker();
            for (int i = 0; i < albumCount; ++i) {
                Artist artist = extractRandomArtist(artistRepository);
                Genre genre = extractRandomGenre(genreRepository);
                Album album = albumRepository.create(new Album(2023, faker.book().title(), artist, Collections.singletonList(genre)));
                log.info("A new album have been inserted into the database (JPA implementation): " + album);
            }
        } catch (FindException | CreateException ex) {
            log.error(ex.getMessage());
        }
    }

    public static void insertAlbumsWithJDBC(int albumCount) {
        try {
            Connection con = Database.getInstance().getConnection();
            RepositoryFactory repositoryFactory = new JdbcRepositoryFactory(con);
            AlbumRepository albumRepository = repositoryFactory.createAlbumRepository();
            ArtistRepository artistRepository = repositoryFactory.createArtistRepository();
            GenreRepository genreRepository = repositoryFactory.createGenreRepository();
            Faker faker = new Faker();
            for (int i = 0; i < albumCount; ++i) {
                Artist artist = extractRandomArtist(artistRepository);
                Genre genre = extractRandomGenre(genreRepository);
                Album album = albumRepository.create(new Album(2023, faker.book().title(), artist, Collections.singletonList(genre)));
                log.info("A new album have been inserted into the database (JDBC implementation): " + album);
            }
        } catch (ConnectionException | FindException | CreateException ex) {
            log.error(ex.getMessage());
        }
    }
}
