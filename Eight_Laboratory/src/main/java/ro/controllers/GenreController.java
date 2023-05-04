package ro.controllers;

import ro.exceptions.CreateException;
import ro.exceptions.DeleteException;
import ro.exceptions.FindException;
import ro.exceptions.UpdateException;
import ro.models.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class responsible
 * for executing {@link Genre} related queries
 * in the database.
 *
 * @author Alex Neagu
 */
public class GenreController extends BaseController implements CRUDController<Genre> {
    private static final String INSERT_UPDATE = "insert into genres (name) values (?)";
    private static final String FIND_BY_NAME_QUERY = "select * from genres where UPPER(TRIM(name)) = UPPER(TRIM(?))";
    private static final String FIND_BY_ID_QUERY = "select * from genres where id = ?";
    private static final String DELETE_BY_ID = "delete from genres where id = ?";
    private static final String UPDATE_BY_ID = "update genres set name = ? WHERE id = ?";
    private static final String FIND_GENRES_BY_ALBUM_QUERY = "select genre_id from album_genre where album_id = ?";
    private static final String FIND_ALL = "select * from genres";

    public GenreController(Connection con) {
        super(con);
    }

    /**
     * Inserts a new genre into the database
     *
     * @return A {@link Genre} object with a specific id attribute assigned by the database
     */
    @Override
    public Genre create(Genre genre) throws CreateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            return resultSet.next() ? new Genre(resultSet.getInt("id"), genre.getName()) : null;
        } catch (SQLException sqlException) {
            throw new CreateException(sqlException.getMessage(), "genres");
        }
    }

    /**
     * @param name A genre name
     * @return A {@link Genre} object stored in the database with the given {@code name}
     */
    @Override
    public Genre findByName(String name) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    new Genre(resultSet.getInt("id"), resultSet.getString("name")) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findByName", sqlException.getMessage(), "genres");
        }
    }

    /**
     * Updates an <tt>Genre</tt> entity from the database.
     * <p>
     * The id attribute should be found in the database.
     *
     * @param genre The {@link Genre} that should be updated.
     */
    @Override
    public void update(Genre genre) throws UpdateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_BY_ID)) {
            if (findById(genre.getId()) == null) {
                throw new UpdateException("genres", "genre with id" + genre.getId() + " does not exist in the database");
            }
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setInt(2, genre.getId());
            preparedStatement.executeUpdate();
        } catch (FindException | SQLException ex) {
            throw new UpdateException("genres", ex.getMessage());
        }
    }

    /**
     * @param id The id of a <tt>Genre</tt>
     * @return The {@link Genre} object that corresponds to the database entry with {@code id}
     */
    @Override
    public Genre findById(int id) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    new Genre(resultSet.getInt("id"), resultSet.getString("name")) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findById", sqlException.getMessage(), "genres");
        }
    }

    private List<Genre> extractGenres(ResultSet resultSet) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        while (resultSet.next()) {
            genres.add(new Genre(resultSet.getInt("id"), resultSet.getString("name")));
        }
        return genres;
    }

    /**
     * @return A list of {@link Genre} objects corresponding to all the genres stored in the database
     */
    @Override
    public List<Genre> findAll() throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL)) {
            return extractGenres(preparedStatement.executeQuery());
        } catch (SQLException sqlException) {
            throw new FindException("findAll", sqlException.getMessage(), "genres");
        }
    }

    /**
     * Deletes a <tt>Genre</tt> entity from the database.
     *
     * @param id The id of the {@link Genre} that should be deleted
     */
    @Override
    public void deleteById(int id) throws DeleteException {
        try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DeleteException(sqlException.getMessage(), "genres");
        }
    }

    /**
     * Finds all genres that corresponds to a specific {@link ro.models.Album}
     *
     * @param albumId The id of the specified <tt>Album</tt>
     * @return A list of all the genres that corresponds to the <tt>Album</tt> entity with the {@code albumId}
     */
    public List<Genre> findByAlbumId(int albumId) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_GENRES_BY_ALBUM_QUERY)) {
            preparedStatement.setInt(1, albumId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Genre> foundGenres = new ArrayList<>();
            while (resultSet.next()) {
                foundGenres.add(findById(resultSet.getInt("genre_id")));
            }
            return foundGenres;
        } catch (SQLException sqlException) {
            throw new FindException("findByAlbum", sqlException.getMessage(), "album_genre");
        }
    }
}
