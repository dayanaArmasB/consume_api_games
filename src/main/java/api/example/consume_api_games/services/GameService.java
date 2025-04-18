package api.example.consume_api_games.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.example.consume_api_games.model.entity.Game;

@Service
public class GameService {

    @Value("${rawg.api.key}")
    private String apiKey;

    @Value("${rawg.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getGames(
    @RequestParam(defaultValue = "1") int page,
    @RequestParam(required = false) String genres) {
    String url = apiUrl + "/games?key=" + apiKey + "&page_size=30&page=" + page;

    if (genres != null && !genres.isEmpty()) {
        url += "&genres=" + genres;
    }

    return restTemplate.getForObject(url, String.class);
    }


    public List<Game> searchGames(String query) {
        String url = apiUrl + "/games?key=" + apiKey + "&search=" + query + "&page_size=10"; // Limita los resultados
    
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode results = root.path("results");
    
            List<Game> games = new ArrayList<>();
            for (JsonNode node : results) {
                String gameName = node.get("name").asText();
    
                // Permitir coincidencias parciales y dar prioridad a juegos con el término en el nombre
                if (gameName.toLowerCase().contains(query.toLowerCase())) {
                    Game game = new Game();
                    game.setId(node.get("id").asInt());
                    game.setName(gameName);
    
                    // Manejo seguro de la imagen
                    JsonNode backgroundImageNode = node.get("background_image");
                    game.setBackgroundImage((backgroundImageNode != null && !backgroundImageNode.isNull()) 
                        ? backgroundImageNode.asText() 
                        : "");
    
                    games.add(game);
                }
            }
    
            // Si la lista está vacía, buscar nuevamente pero sin filtrar tanto
            if (games.isEmpty() && results.isArray() && results.size() > 0) {
                for (JsonNode node : results) {
                    Game game = new Game();
                    game.setId(node.get("id").asInt());
                    game.setName(node.get("name").asText());
    
                    JsonNode backgroundImageNode = node.get("background_image");
                    game.setBackgroundImage((backgroundImageNode != null && !backgroundImageNode.isNull()) 
                        ? backgroundImageNode.asText() 
                        : "");
    
                    games.add(game);
                }
            }
    
            return games;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
  
    public String getGameDetails(int id) {
        String url = apiUrl + "/games/" + id + "?key=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }

    public String getGenres() {
        String url = apiUrl + "/genres?key=" + apiKey;
        return restTemplate.getForObject(url, String.class);
    }
}
