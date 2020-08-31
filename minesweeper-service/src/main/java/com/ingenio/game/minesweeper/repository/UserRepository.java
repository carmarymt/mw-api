package com.ingenio.game.minesweeper.repository;

import com.ingenio.game.minesweeper.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
