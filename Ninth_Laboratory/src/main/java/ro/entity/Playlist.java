package ro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "playlists", schema = "public", catalog = "java_lab8")
@NamedQueries({
        @NamedQuery(name = "Playlist.findByName", query = "SELECT p FROM Playlist p WHERE p.name = :name"),
        @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p"),
})
public class Playlist {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "creation_timestamp")
    private Timestamp creationTimestamp;
    @Basic
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "playlist_albums_tracking",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id")
    )
    private List<Album> albums;

    public Playlist(Timestamp creationTimestamp, String name, List<Album> albums) {
        this.creationTimestamp = creationTimestamp;
        this.name = name;
        this.albums = albums;
    }
}
