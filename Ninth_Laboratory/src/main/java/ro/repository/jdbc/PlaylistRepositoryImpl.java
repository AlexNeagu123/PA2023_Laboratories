package ro.repository.jdbc;

import ro.entity.Album;
import ro.entity.Playlist;
import ro.exceptions.CreateException;
import ro.exceptions.DeleteException;
import ro.exceptions.FindException;
import ro.exceptions.UpdateException;
import ro.repository.interfaces.PlaylistRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class responsible
 * for executing {@link Playlist} related queries
 * in the database (using JDBC).
 *
 * @author Alex Neagu
 */
public class PlaylistRepositoryImpl extends BaseController implements PlaylistRepository {
    private static final String INSERT_UPDATE = "insert into playlists (name, creation_timestamp) values (?, ?)";
    private static final String INSERT_JUNCTION = "insert into playlist_albums_tracking(playlist_id, album_id) values(?, ?)";
    private static final String FIND_BY_NAME_QUERY = "select * from playlists where upper(trim(name)) = upper(trim(?))";
    private static final String FIND_BY_ID_QUERY = "select * from playlists where id = ?";
    private static final String DELETE_BY_ID = "delete from playlists where id = ?";
    private static final String UPDATE_BY_ID = "update playlists set name = ?, creation_timestamp = ? where id = ?";
    private static final String FIND_ALL = "select * from playlists";

    private final AlbumRepositoryImpl albumDao;

    public PlaylistRepositoryImpl(Connection con) {
        super(con);
        albumDao = new AlbumRepositoryImpl(con);
    }

    private Playlist insertIntoPlaylists(Playlist playlist) throws CreateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setTimestamp(2, playlist.getCreationTimestamp());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                playlist.setId(resultSet.getInt("id"));
                return playlist;
            }
            throw new CreateException("Key generation error on insert", "playlists");
        } catch (SQLException sqlException) {
            throw new CreateException(sqlException.getMessage(), "playlists");
        }
    }

    private void insertJunction(int playlistId, int albumId) throws CreateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_JUNCTION)) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, albumId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new CreateException(sqlException.getMessage(), "playlists_albums_tracking");
        }
    }

    private Playlist mapResultSetToPlaylist(ResultSet resultSet) throws SQLException, FindException {
        int playlistId = resultSet.getInt("id");
        return new Playlist(
                playlistId,
                resultSet.getTimestamp("creation_timestamp"),
                resultSet.getString("name"),
                albumDao.findByPlaylistId(playlistId)
        );
    }

    /**
     * Inserts a new playlist into the database
     *
     * @return A {@link Playlist} object with a specific id attribute assigned by the database
     */
    @Override
    public Playlist create(Playlist playlist) throws CreateException {
        List<Album> albums = playlist.getAlbums();
        Playlist insertedPlaylist = insertIntoPlaylists(playlist);
        for (Album album : albums) {
            insertJunction(insertedPlaylist.getId(), album.getId());
        }
        return insertedPlaylist;
    }

    /**
     * @param id The id of a <tt>Playlist</tt>
     * @return The {@link Playlist} object that corresponds to the database entry with {@code id}
     */
    @Override
    public Playlist findById(int id) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    mapResultSetToPlaylist(resultSet) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findById", sqlException.getMessage(), "playlists");
        }
    }

    /**
     * @param name A playlist name
     * @return A {@link Playlist} object stored in the database with the given {@code name}
     */
    @Override
    public Playlist findByName(String name) throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_BY_NAME_QUERY)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ?
                    mapResultSetToPlaylist(resultSet) : null;
        } catch (SQLException sqlException) {
            throw new FindException("findByName", sqlException.getMessage(), "playlists");
        }
    }

    private List<Playlist> extractPlaylists(ResultSet resultSet) throws SQLException, FindException {
        List<Playlist> playlists = new ArrayList<>();
        while (resultSet.next()) {
            playlists.add(mapResultSetToPlaylist(resultSet));
        }
        return playlists;
    }

    /**
     * @return A list of {@link Playlist} objects corresponding to all the genres stored in the database
     */
    @Override
    public List<Playlist> findAll() throws FindException {
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_ALL)) {
            return extractPlaylists(preparedStatement.executeQuery());
        } catch (SQLException sqlException) {
            throw new FindException("findAll", sqlException.getMessage(), "playlists");
        }
    }

    /**
     * Updates an <tt>Playlist</tt> entity from the database.
     * <p>
     * The id attribute should be found in the database.
     *
     * @param playlist The {@link Playlist} that should be updated.
     */
    @Override
    public void update(Playlist playlist) throws UpdateException {
        try (PreparedStatement preparedStatement = con.prepareStatement(UPDATE_BY_ID)) {
            if (findById(playlist.getId()) == null) {
                throw new UpdateException("playlist with id" + playlist.getId() + " does not exist in the database", "playlists");
            }
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setTimestamp(2, playlist.getCreationTimestamp());
            preparedStatement.executeUpdate();
        } catch (SQLException | FindException sqlException) {
            throw new UpdateException(sqlException.getMessage(), "playlists");
        }
    }

    /**
     * Deletes a <tt>Playlist</tt> entity from the database.
     *
     * @param id The id of the {@link Playlist} that should be deleted
     */
    @Override
    public void deleteById(int id) throws DeleteException {
        try (PreparedStatement preparedStatement = con.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DeleteException(sqlException.getMessage(), "playlists");
        }
    }
}
