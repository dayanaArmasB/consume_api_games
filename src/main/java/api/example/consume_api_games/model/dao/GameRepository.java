package api.example.consume_api_games.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.example.consume_api_games.model.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

     List<Game> findByNameContainingIgnoreCase(String name);
}
