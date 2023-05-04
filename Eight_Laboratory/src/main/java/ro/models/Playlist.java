package ro.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class Playlist {
    private int id;
    private Timestamp timestamp;
    private String name;
    private List<Album> albums;

    public Playlist(String name, Timestamp timestamp, List<Album> albums) {
        this.name = name;
        this.timestamp = timestamp;
        this.albums = albums;
    }
}
