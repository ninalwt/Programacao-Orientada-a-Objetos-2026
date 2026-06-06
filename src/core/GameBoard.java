package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Handles the game logic, grid boundaries, collisions, and entity management.
 */
public class GameBoard {
    private Snake snake;
    private Food food;
    private int score;
    
    private final int TILE_SIZE = 20;
    private final int GRID_WIDTH = 32; 
    private final int GRID_HEIGHT = 24; 
    
    private float timer = 0;
    private float moveInterval = 0.15f; 

    /**
     * Initializes the game board, spawning the snake and initial food.
     */
    public GameBoard() {
        snake = new Snake(10, 10);
        food = new Food(20, 10);
        score = 0;
    }

    /**
     * Updates game state, handling input, movement, and collision.
     * @param delta Time passed since the last frame.
     */
    public void update(float delta) {
        handleInput();

        timer += delta;
        if (timer >= moveInterval) {
            timer = 0;
            snake.move();
            checkWrapAround();
            checkCollisions();
        }
    }

    /**
     * Captures user input to change the snake's direction.
     */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) snake.setDirection(new Vector2(0, 1));
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) snake.setDirection(new Vector2(0, -1));
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) snake.setDirection(new Vector2(-1, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snake.setDirection(new Vector2(1, 0));
    }

    /**
     * Ensures the snake wraps around the screen edges.
     */
    private void checkWrapAround() {
        Vector2 head = snake.getBody().getFirst();
        if (head.x >= GRID_WIDTH) head.x = 0;
        if (head.x < 0) head.x = GRID_WIDTH - 1;
        if (head.y >= GRID_HEIGHT) head.y = 0;
        if (head.y < 0) head.y = GRID_HEIGHT - 1;
    }

    /**
     * Checks for interactions between the snake, food, and its own body.
     */
    private void checkCollisions() {
        Vector2 head = snake.getBody().getFirst();

        if (head.equals(food.getPosition())) {
            snake.grow();
            score += 10;
            food.respawn(GRID_WIDTH, GRID_HEIGHT, snake.getBody());
            // Speeds up the game slightly
            moveInterval = Math.max(0.05f, moveInterval - 0.005f); 
        }

        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.equals(snake.getBody().get(i))) {
                // Game Over logic will go here
                System.out.println("Snake collided with itself!");
            }
        }
    }

    /**
     * Renders the game entities on the screen using shapes.
     * @param shapeRenderer The rendering tool.
     */
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(1, 0, 0, 1); // Red food
        shapeRenderer.rect(food.getPosition().x * TILE_SIZE, food.getPosition().y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        shapeRenderer.setColor(0, 1, 0, 1); // Green snake
        for (Vector2 part : snake.getBody()) {
            shapeRenderer.rect(part.x * TILE_SIZE, part.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    /**
     * Gets the current player score.
     * @return The score.
     */
    public int getScore() {
        return score;
    }
}
