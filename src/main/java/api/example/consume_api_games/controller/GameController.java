package api.example.consume_api_games.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.example.consume_api_games.model.entity.Game;
import api.example.consume_api_games.services.GameService;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")  // Permitir llamadas desde el frontend
public class GameController {
    
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public String getGames(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(required = false) String genres
    ) {
        return gameService.getGames(page, genres);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Game>> searchGames(@RequestParam String query) {
        List<Game> games = gameService.searchGames(query);

        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    @GetMapping("/{id}")
    public String getGameDetails(@PathVariable int id) {
        return gameService.getGameDetails(id);
    }

    @GetMapping("/genres")
    public String getGenres() {
    return gameService.getGenres();
}

}
