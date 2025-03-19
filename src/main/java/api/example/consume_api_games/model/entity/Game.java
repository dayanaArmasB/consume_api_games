package api.example.consume_api_games.model.entity;


public class Game {
    private int id;
    private String name;
    private String description;
    private String backgroundImage;

    // Constructor vacío
    public Game() {}

    // Constructor con parámetros
    public Game(int id, String name, String description, String backgroundImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundImage = backgroundImage;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBackgroundImage() { return backgroundImage; }
    public void setBackgroundImage(String backgroundImage) { this.backgroundImage = backgroundImage; }
}

