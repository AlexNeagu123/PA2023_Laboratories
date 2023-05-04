package ro;

import lombok.extern.log4j.Log4j2;
import ro.db.Database;
import ro.exceptions.ConnectionException;
import ro.exceptions.CreateException;
import ro.exceptions.FindException;
import ro.population.DatabasePopulation;
import ro.utils.PlaylistBuilder;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The <tt>albumlist.csv</tt> file is parsed and the database is being populated.
 * <p>
 * After the database is populated, a number of playlists (100 maximum) that contains
 * a maximum number of <tt>unrelated</tt> albums are inserted into the <tt>playlists</tt> table.
 */
@Log4j2
public class Main {
    public static void main(String[] args) {
        try {
            Connection con = Database.getInstance().getConnection();
            initializeDatabase(con);
            buildPlaylist(con);
            con.commit();
        } catch (ConnectionException ex) {
            log.error("Error when obtaining databse connection: " + ex.getMessage());
        } catch (SQLException sqlException) {
            log.error("Error when commiting: " + sqlException.getMessage());
        }
    }

    private static void initializeDatabase(Connection con) {
        try {
            DatabasePopulation databasePopulation = new DatabasePopulation(con, "src/main/resources/albumlist.csv");
            databasePopulation.populate();
        } catch (CreateException | FindException ex) {
            log.error(ex.getMessage());
        } catch (FileNotFoundException fileEx) {
            log.error("File not found: " + fileEx.getMessage());
        }
    }

    private static void buildPlaylist(Connection con) {
        try {
            PlaylistBuilder playlistBuilder = new PlaylistBuilder(con);
            playlistBuilder.buildMany();
        } catch (FindException ex) {
            log.error("Graph Initialization: " + ex.getMessage());
        } catch (CreateException createEx) {
            log.error(createEx.getMessage());
        }
    }
}