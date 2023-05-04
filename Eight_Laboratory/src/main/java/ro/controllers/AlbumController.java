package ro.controllers;

import ro.exceptions.CreateException;
import ro.exceptions.DeleteException;
import ro.exceptions.FindException;
import ro.exceptions.UpdateException;
import ro.models.Album;
import ro.models.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class responsible
 * for executing {@link Album} related queries
 * in the database.
 *
 * @author Alex Neagu
 */
public class AlbumController extends BaseController implements CRUDController<Album> {
    private static final String INSERT_ALBUM = "insert into albums (release_year, title, artist_id) values (?, ?, ?)";
    private static final String FIND_BY_TITLE_QUERY = "select * from albums where upper(trim(title)) = upper(trim(?))";
    private static final String FIND_BY_ARTIST_QUERY = "select id from albums where artist_id = ?";
    private static final String FIND_BY_ID_QUERY = "select * from albums where id = ?";
    private static final String DELETE_BY_ID = "delete from albums where id = ?";
    private static final String UPDATE_BY_ID = "update albums set release_year = ?, title = ?, artist_id = ? where id = ?";
    private static final String FIND_ALBUMS_BY_GENRE_QUERY = "select album_id from album_genre where genre_id = ?";
    private static final String FIND_ALBUMS_BY_PLAYLIST_QUERY = "select album_id from playlist_albums_tracking where playlist_id = ?";
    private static final String INSERT_JUNCTION = "insert into album_genre(album_id, genre_id) values(?, ?)";
    private static final String FIND_ALL = "select id from albums";
    private final ArtistController artistController;
    private final GenreController genreController;

    public AlbumController(Connection con) {
        super(con);
        artistController = new ArtistController(con);
        genreController = new GenreController(con);
    }

    private Album insertIntoAlbums(Album album) throws CreateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, album.getReleaseYear());
            preparedStatement.setString(2, album.getTitle());
            preparedStatement.setInt(3, album.getArtist().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                album.setId(resultSet.getInt("id"));
                return album;
            }
            throw new CreateException("Key generation error on insert", "albums");
        } catch (SQLException sqlException) {
            throw new CreateException(sqlException.getMessage(), "albums");
        }
    }

    private void insertJunction(int albumId, int genreId) throws CreateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_JUNCTION)) {
            preparedStatement.setInt(1, albumId);
            preparedStatement.setInt(2, genreId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new CreateException(sqlException.getMessage(), "album_genre");
        }
    }

    /**
     * Inserts a new album into the database
     *
     * @return An {@link Album} object with a specific id attribute assigned by the database
     */
    @Override
    public Album create(Album album) throws CreateException {
        List<Genre> genres = album.getGenres();
        Album insertedAlbum = insertIntoAlbums(album);
        for (Genre genre : genres) {
            insertJunction(insertedAlbum.getId(), genre.getId());
        }
        return insertedAlbum;
    }

    private Album mapResultSetToAlbum(ResultSet resultSet) throws SQLException, FindException {
        int albumId = resultSet.getInt("id");
        return new Album(
                albumId,
                resultSet.getInt("release_year"),
                resultSet.getString("title"),
                artistController.findById(resultSet.getInt("artist_id")),
                genreController.findByAlbumId(albumId)
        );
    }

    private List<Album> extractAlbumList(ResultSet resultSet, String columnLabel) throws SQLException, FindException {
        List<Album> foundAlbums = new ArrayList<>();
        while (resultSet.next()) {
            foundAlbums.add(findById(resultSet.getInt(columnLabel)));
        }
        return foundAlbums;
    }

    /**
     * @param title An album title
     * @return An {@link Album} object stored in the database with the given {@code title}
     */
    @Override
    public Album findByName(String title) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_TITLE_QUERY)) {
            preparedStatement.setString(1, title);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    mapResultSetToAlbum(resultSet) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findByTitle", sqlException.getMessage(), "albums");
        }
    }

    /**
     * @return A list of {@link Album} objects corresponding to all the albums stored in the database
     */
    @Override
    public List<Album> findAll() throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL)) {
            return extractAlbumList(preparedStatement.executeQuery(), "id");
        } catch (SQLException sqlException) {
            throw new FindException("findAll", sqlException.getMessage(), "albums");
        }
    }

    /**
     * Updates an <tt>Album</tt> entity from the database.
     * <p>
     * The id attribute should be found in the database.
     *
     * @param album The {@link Album} that should be updated.
     */
    @Override
    public void update(Album album) throws UpdateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_BY_ID)) {
            if (findById(album.getId()) == null) {
                throw new UpdateException("album with id" + album.getId() + " does not exist in the database", "albums");
            }
            preparedStatement.setInt(1, album.getReleaseYear());
            preparedStatement.setString(2, album.getTitle());
            preparedStatement.setInt(3, album.getArtist().getId());
            preparedStatement.setInt(4, album.getId());
            preparedStatement.executeUpdate();
        } catch (FindException | SQLException ex) {
            throw new UpdateException("albums", ex.getMessage());
        }
    }

    /**
     * @param albumId The id of an <tt>Album</tt>
     * @return The {@link Album} object that corresponds to the database entry with {@code albumId}
     */
    @Override
    public Album findById(int albumId) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, albumId);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    mapResultSetToAlbum(resultSet) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findById", sqlException.getMessage(), "albums");
        }
    }

    /**
     * Deletes an <tt>Album</tt> entity from the database.
     *
     * @param albumId The id of the {@link Album} that should be deleted
     */
    @Override
    public void deleteById(int albumId) throws DeleteException {
        try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, albumId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DeleteException(sqlException.getMessage(), "albums");
        }
    }

    /**
     * Finds all albums that have been written by a specific {@link ro.models.Artist}
     *
     * @param artistId The id of the specified <tt>Artist</tt>
     * @return A list of all the albums written by the <tt>Artist</tt> with id equals to {@code artistId}
     */
    public List<Album> findByArtistId(int artistId) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_ARTIST_QUERY)) {
            preparedStatement.setInt(1, artistId);
            return extractAlbumList(preparedStatement.executeQuery(), "id");
        } catch (SQLException sqlException) {
            throw new FindException("findByTitle", sqlException.getMessage(), "albums");
        }
    }

    /**
     * Finds all albums that have a specific {@link ro.models.Genre}
     *
     * @param genreId The id of the specified <tt>Genre</tt>
     * @return A list of all the albums that contains the <tt>Genre</tt> with id equals to {@code artistId}
     */
    public List<Album> findByGenreId(int genreId) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_ALBUMS_BY_GENRE_QUERY)) {
            preparedStatement.setInt(1, genreId);
            return extractAlbumList(preparedStatement.executeQuery(), "album_id");
        } catch (SQLException sqlException) {
            throw new FindException("findByGenreId", sqlException.getMessage(), "album_genre");
        }
    }

    /**
     * Finds all albums from a specific {@link ro.models.Playlist}
     *
     * @param playlistId The id of the specified <tt>Playlist</tt>
     * @return A list of all the albums that are contained in the <tt>Playlist</tt> with id equals to {@code playlistId}
     */
    public List<Album> findByPlaylistId(int playlistId) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_ALBUMS_BY_PLAYLIST_QUERY)) {
            preparedStatement.setInt(1, playlistId);
            return extractAlbumList(preparedStatement.executeQuery(), "album_id");
        } catch (SQLException sqlException) {
            throw new FindException("findByGenreId", sqlException.getMessage(), "album_genre");
        }
    }
}
