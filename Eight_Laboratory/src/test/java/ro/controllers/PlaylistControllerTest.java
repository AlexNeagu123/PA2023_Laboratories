package ro.controllers;

import org.junit.Assert;
import org.junit.Test;
import ro.db.Database;
import ro.exceptions.ConnectionException;
import ro.exceptions.DeleteException;
import ro.exceptions.FindException;

import java.sql.Connection;

public class PlaylistControllerTest {
    @Test
    public void whenFindAllPlaylistsCalled_thenShouldReturn100Playlists() throws ConnectionException, FindException {
        Connection con = Database.getInstance().getConnection();
        PlaylistController playlistController = new PlaylistController(con);
        Assert.assertEquals(playlistController.findAll().size(), 100);
    }

    @Test
    public void whenDeleteByIdCalledOnId1_thenItShouldDelete() throws ConnectionException, DeleteException, FindException {
        Connection con = Database.getInstance().getConnection();
        PlaylistController playlistController = new PlaylistController(con);
        playlistController.deleteById(1);
        Assert.assertNull(playlistController.findById(1));
    }
}