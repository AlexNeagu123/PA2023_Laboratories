package com.ro.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "games", schema = "public", catalog = "java_lab11")
public class Game {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1_id")
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "player2_id")
    private Player secondPlayer;

    @Basic
    @Column(name = "moves")
    private String moves;

    @Basic
    @Column(name = "winner")
    private String winner;

    public Game(Player firstPlayer, Player secondPlayer, String moves, String winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.moves = moves;
        this.winner = winner;
    }
}
