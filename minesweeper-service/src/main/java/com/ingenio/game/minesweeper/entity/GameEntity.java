package com.ingenio.game.minesweeper.entity;

import com.ingenio.game.minesweeper.entity.converter.InstantToTimestampConverter;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

import static lombok.AccessLevel.PRIVATE;

@Getter
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@Entity
@Table(name = "game")
public class GameEntity {

    @Id
    @Column(name = "game_id", insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @Column(name = "status_id", nullable = false)
    private int status;

    @Column(name = "board", nullable = false)
    private String board;

    @Column(name = "num_rows", nullable = false)
    private int numberRows;

    @Column(name = "num_columns", nullable = false)
    private int numberColumns;

    @Column(name = "num_mines", nullable = false)
    private int numberMines;

    @Column(name = "mines_left", nullable = false)
    private int minesLeft;

    @Column(name = "date_created", nullable = false)
    @Convert(converter = InstantToTimestampConverter.class)
    private Instant dateCreated;

    @Column(name = "last_updated", nullable = false)
    @Convert(converter = InstantToTimestampConverter.class)
    private Instant lastUpdated;

    @Column(name = "time_duration_sec", nullable = false)
    private int timeDurationSeconds;

    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private UserEntity user;
}
