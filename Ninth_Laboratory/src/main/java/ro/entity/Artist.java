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
@Table(name = "artists", schema = "public", catalog = "java_lab8")
@NamedQueries({
        @NamedQuery(name = "Artist.findByName", query = "SELECT a FROM Artist a WHERE a.name = :name"),
        @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a"),
        @NamedQuery(name = "Artist.getCount", query = "SELECT count(a) FROM Artist a")
})
public class Artist implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "artist")
    @ToString.Exclude
    private List<Album> albums;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
