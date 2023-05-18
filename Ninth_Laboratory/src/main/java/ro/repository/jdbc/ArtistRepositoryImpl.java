package ro.repository.jdbc;

import lombok.extern.log4j.Log4j2;
import ro.entity.Artist;
import ro.exceptions.CreateException;
import ro.exceptions.DeleteException;
import ro.exceptions.FindException;
import ro.exceptions.UpdateException;
import ro.repository.interfaces.ArtistRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class responsible
 * for executing {@link Artist} related queries
 * in the database (using JDBC).
 *
 * @author Alex Neagu
 */
@Log4j2
public class ArtistRepositoryImpl extends BaseController implements ArtistRepository {
    private static final String INSERT_UPDATE = "insert into artists (name) values (?)";
    private static final String FIND_BY_NAME_QUERY = "select * from artists where UPPER(TRIM(name)) = UPPER(TRIM(?))";
    private static final String FIND_BY_ID_QUERY = "select * from artists where id = ?";
    private static final String DELETE_BY_ID = "delete from artists where id = ?";
    private static final String UPDATE_BY_ID = "update artists set name = ? where id = ?";
    private static final String FIND_ALL = "select * from artists";
    private static final String FIND_COUNT = "select count(*) from artists";

    public ArtistRepositoryImpl(Connection con) {
        super(con);
    }

    /**
     * Inserts a new artist into the database
     *
     * @return An {@link Artist} object with a specific id attribute assigned by the database
     */
    @Override
    public Artist create(Artist artist) throws CreateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, artist.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return resultSet.next() ?
                    new Artist(resultSet.getInt("id"), artist.getName()) : null;
        } catch (SQLException sqlException) {
            throw new CreateException(sqlException.getMessage(), "artists");
        }
    }

    /**
     * @param name An album name
     * @return An {@link Artist} object stored in the database with the given {@code name}
     */
    @Override
    public Artist findByName(String name) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    new Artist(resultSet.getInt("id"), resultSet.getString("name")) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findByName", sqlException.getMessage(), "artists");
        }
    }

    /**
     * Updates an <tt>Artist</tt> entity from the database.
     * <p>
     * The id attribute should be found in the database.
     *
     * @param artist The {@link Artist} that should be updated.
     */
    @Override
    public void update(Artist artist) throws UpdateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_BY_ID)) {
            if (findById(artist.getId()) == null) {
                throw new UpdateException("artists", "artist with id" + artist.getId() + " does not exist in the database");
            }
            preparedStatement.setString(1, artist.getName());
            preparedStatement.setInt(2, artist.getId());
            preparedStatement.executeUpdate();
        } catch (FindException | SQLException ex) {
            throw new UpdateException("artists", ex.getMessage());
        }
    }

    /**
     * @param id The id of an <tt>Artist</tt>
     * @return The {@link Artist} object that corresponds to the database entry with {@code id}
     */
    @Override
    public Artist findById(int id) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    new Artist(resultSet.getInt("id"), resultSet.getString("name")) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findById", sqlException.getMessage(), "artists");
        }
    }

    private List<Artist> extractArtists(ResultSet resultSet) throws SQLException {
        List<Artist> artists = new ArrayList<>();
        while (resultSet.next()) {
            artists.add(new Artist(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return artists;
    }

    /**
     * @return A list of {@link Artist} objects corresponding to all the artists stored in the database
     */
    @Override
    public List<Artist> findAll() throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL)) {
            return extractArtists(preparedStatement.executeQuery());
        } catch (SQLException sqlException) {
            throw new FindException("findAll", sqlException.getMessage(), "artists");
        }
    }

    /**
     * Deletes an <tt>Artist</tt> entity from the database.
     *
     * @param id The id of the {@link Artist} that should be deleted
     */
    @Override
    public void deleteById(int id) throws DeleteException {
        try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DeleteException(sqlException.getMessage(), "artists");
        }
    }

    @Override
    public Long getArtistCount() throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_COUNT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new SQLException("Artist count couldn't be calculated");
            }
            return resultSet.getLong("count");
        } catch (SQLException sqlException) {
            throw new FindException("findCount", sqlException.getMessage(), "artists");
        }
    }
}
