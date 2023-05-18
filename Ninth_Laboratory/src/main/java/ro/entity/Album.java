package ro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "albums", schema = "public", catalog = "java_lab8")
@NamedQueries({
        @NamedQuery(name = "Album.findByArtist", query = "SELECT al FROM Album al WHERE al.artist.id = :id"),
        @NamedQuery(name = "Album.findAll", query = "SELECT al FROM Album al"),
        @NamedQuery(name = "Album.findByName", query = "SELECT al FROM Album al WHERE al.title = :name"),
        @NamedQuery(name = "Album.findByGenre", query = "SELECT al FROM Album al JOIN al.genres g WHERE g.id = :id"),
        @NamedQuery(name = "Album.findByPlaylist", query = "SELECT al FROM Album al JOIN al.playlists p WHERE p.id = :id")
})
public class Album implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "release_year")
    private Integer releaseYear;
    @Basic
    @Column(name = "title")
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @ManyToMany
    @JoinTable(name = "album_genre",
            joinColumns = @JoinColumn(name = "album_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
    )
    private List<Genre> genres;

    @ManyToMany(mappedBy = "albums")
    @ToString.Exclude
    private List<Playlist> playlists;

    public Album(Integer releaseYear, String title, Artist artist, List<Genre> genres) {
        this.releaseYear = releaseYear;
        this.title = title;
        this.artist = artist;
        this.genres = genres;
    }

    public Album(int albumId, Integer releaseYear, String title, Artist artist, List<Genre> genres) {
        this.id = albumId;
        this.releaseYear = releaseYear;
        this.title = title;
        this.artist = artist;
        this.genres = genres;
    }
}
