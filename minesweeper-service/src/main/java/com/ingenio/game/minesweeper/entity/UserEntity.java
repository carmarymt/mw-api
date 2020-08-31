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
@Table(name = "user_account")
public class UserEntity {

    @Id
    @Column(name = "user_id", insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_created", nullable = false)
    @Convert(converter = InstantToTimestampConverter.class)
    private Instant dateCreated;

}