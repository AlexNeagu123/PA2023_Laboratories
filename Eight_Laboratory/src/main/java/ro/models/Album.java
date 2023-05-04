package ro.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Album {
    private int id;
    private int releaseYear;
    private String title;
    private Artist artist;
    List<Genre> genres;

    public Album(int release_year, String title, Artist artist, List<Genre> genres) {
        this.releaseYear = release_year;
        this.title = title;
        this.artist = artist;
        this.genres = genres;
        this.id = 0;
    }
}
