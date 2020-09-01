package com.ingenio.game.minesweeper.repository;

import com.ingenio.game.minesweeper.entity.GameEntity;
import com.ingenio.game.minesweeper.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findGamesByUser(UserEntity userEntity);
}
