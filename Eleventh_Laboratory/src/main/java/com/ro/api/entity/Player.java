package com.ro.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "players", schema = "public", catalog = "java_lab11")
public class Player implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "firstPlayer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Game> gamesAsPlayer1;

    @OneToMany(mappedBy = "secondPlayer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Game> gamesAsPlayer2;

    public Player(String username) {
        this.username = username;
    }

    public Player(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
