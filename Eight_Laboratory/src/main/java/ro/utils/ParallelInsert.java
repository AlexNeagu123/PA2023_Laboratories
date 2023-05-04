package ro.utils;

import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import ro.controllers.ArtistController;
import ro.db.ConnectionPool;
import ro.exceptions.CreateException;
import ro.models.Artist;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This runnable class represents a thread that is being executed by the {@link ro.ConnectionPoolTest} class.
 * <p>
 * An artist with a random generated name is being inserted into the database.
 */
@Log4j2
public class ParallelInsert implements Runnable {
    private final int threadId;

    public ParallelInsert(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {
        try {
            Connection con = ConnectionPool.getInstance().getConnection();
            log.info("Thread with id " + threadId + " started running and got a connection from the connection pool");
            ArtistController artistController = new ArtistController(con);
            Faker faker = new Faker();
            Artist artist = artistController.create(new Artist(
                    faker.name().fullName()
            ));
            con.commit();
            con.close();
            log.info("Thread with id " + threadId + " successfully inserted artist " + artist + " to the database");
        } catch (SQLException | CreateException e) {
            log.error(e);
        }
    }
}
