package ro.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artist {
    private int id;
    private String name;

    public Artist(String name) {
        this.name = name;
        this.id = 0;
    }
}
