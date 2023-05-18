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
@Table(name = "genres", schema = "public", catalog = "java_lab8")
@NamedQueries({
        @NamedQuery(name = "Genre.findByName", query = "SELECT g FROM  Genre g WHERE g.name = :name"),
        @NamedQuery(name = "Genre.findAll", query = "SELECT g FROM Genre g"),
        @NamedQuery(name = "Genre.findByAlbumId", query = "SELECT g FROM Genre g JOIN g.albums al WHERE al.id = :id"),
        @NamedQuery(name = "Genre.getCount", query = "SELECT COUNT(g) FROM Genre g")
})
public class Genre implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres")
    @ToString.Exclude
    private List<Album> albums;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
