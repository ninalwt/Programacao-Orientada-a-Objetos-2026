package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Handles the game logic, grid boundaries, collisions, and entity management.
 */
public class GameBoard {
    private Snake snake;
    private Snake snake2;

    private Food food;

    private int score;
    private int score2;

    // Game over state and winner message
    private boolean gameOver;
    private String winnerMessage;
    
    private final int TILE_SIZE = 20;
    private final int GRID_WIDTH = 32; 
    private final int GRID_HEIGHT = 24; 
    
    private float timer = 0;
    private float moveInterval = 0.15f; 

    private SoundManager soundManager;

    /**
     * Initializes the game board, spawning the snake and initial food.
     */
    public GameBoard(SoundManager soundManager) {
        this.soundManager = soundManager;

        snake = new Snake(1, 12, new Vector2(1, 0)); // Start moving right
        snake.getBody().add(new Vector2(0,12)); // starting tail of snake 1

        snake2 = new Snake(31, 13, new Vector2(-1, 0)); // Start moving left
        snake2.getBody().add(new Vector2(32,13)); // starting tail of snake 2
        
        float foodX = MathUtils.random(10, 22);
        float foodY = MathUtils.random(7, 17);
        food = new Food(foodX, foodY);

        score = 0;
        score2 = 0;

        gameOver = false;
        winnerMessage = "";
    }

    /**
     * Updates game state, handling input, movement, and collision.
     * @param delta Time passed since the last frame.
     */
    public void update(float delta) {
        if (gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                resetGame();
            }
            return;
        }

        handleInput();

        timer += delta;
        if (timer >= moveInterval) {
            timer = 0;

            snake.move();
            snake2.move();

            checkWrapAround(snake);
            checkWrapAround(snake2);

            checkCollisions();
        }
    }

    /**
     * Captures user input to change the snake's direction.
     */
    private void handleInput() {
        // Player 1 controls (Arrow keys)
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) snake.setDirection(new Vector2(0, 1));
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) snake.setDirection(new Vector2(0, -1));
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) snake.setDirection(new Vector2(-1, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) snake.setDirection(new Vector2(1, 0));

        // Player 2 controls (WASD keys)
        if (Gdx.input.isKeyPressed(Input.Keys.W)) snake2.setDirection(new Vector2(0, 1));
        if (Gdx.input.isKeyPressed(Input.Keys.S)) snake2.setDirection(new Vector2(0, -1));
        if (Gdx.input.isKeyPressed(Input.Keys.A)) snake2.setDirection(new Vector2(-1, 0));
        if (Gdx.input.isKeyPressed(Input.Keys.D)) snake2.setDirection(new Vector2(1, 0));
    }

    /**
     * Ensures the snake wraps around the screen edges.
     */
    private void checkWrapAround(Snake snake) {
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
        Vector2 head2 = snake2.getBody().getFirst();

        // Check if either snake has eaten the food
        if (head.equals(food.getPosition())) {
            snake.grow();
            score += 10;
            soundManager.playCollectSound();
            respawnFood();
            //food.respawn(GRID_WIDTH, GRID_HEIGHT, snake.getBody());
            //moveInterval = Math.max(0.05f, moveInterval - 0.005f); 
        }

        if (head2.equals(food.getPosition())) {
            snake2.grow();
            score2 += 10;
            soundManager.playCollectSound();
            respawnFood();
            //food.respawn(GRID_WIDTH, GRID_HEIGHT, snake2.getBody());
            //moveInterval = Math.max(0.05f, moveInterval - 0.005f); 
        }

        boolean snakeDie = false;
        boolean snake2Die = false;

        // Check for self-collision
        for (int i = 1; i < snake.getBody().size(); i++) {
            if (head.equals(snake.getBody().get(i))) {
                snakeDie = true;
                System.out.println("Snake collided with itself!");
            }
        }

        for (int i = 1; i < snake2.getBody().size(); i++) {
            if (head2.equals(snake2.getBody().get(i))) {
                snake2Die = true;
                System.out.println("Snake 2 collided with itself!");
            }
        }

        // Check for collision between the two snakes
        for (Vector2 part : snake.getBody()) {
            if (head2.equals(part)) {
                snake2Die = true;
                System.out.println("Snake 2 collided with Snake 1!");
            }
        }
        for (Vector2 part : snake2.getBody()) {
            if (head.equals(part)) {
                snakeDie = true;
                System.out.println("Snake 1 collided with Snake 2!");
            }
        }

        // Determine game over state and winner
        if (snakeDie && snake2Die) {
            gameOver = true;
            soundManager.playDeathSound();

            // if the snakes collide, the winner is the one with the biggest score
            if (score > score2) {
                winnerMessage = "Player 1 wins!";
            } else if (score2 > score) { 
                winnerMessage = "Player 2 wins!";
            } else {
                winnerMessage = "It's a tie!";
            }
        
        // if the snake dies alone, the winner is the one alive
        } else if (snakeDie) {
            gameOver = true;
            winnerMessage = "Player 2 wins!";
        } else if (snake2Die) {
            gameOver = true;
            winnerMessage = "Player 1 wins!";
        }

    }

    private void resetGame() {
        snake = new Snake(10, 10, new Vector2(1, 0)); // Start moving right
        snake2 = new Snake(20, 10, new Vector2(-1, 0)); // Start moving left
        score = 0;
        score2 = 0;
        moveInterval = 0.15f;
        gameOver = false;
        winnerMessage = "";
    }

    private void respawnFood() {
        // Combine both snakes' bodies to ensure food doesn't spawn on either snake
        java.util.LinkedList<Vector2> combinedBodies = new java.util.LinkedList<>();
        combinedBodies.addAll(snake.getBody());
        combinedBodies.addAll(snake2.getBody());
        
        food.respawn(GRID_WIDTH, GRID_HEIGHT, combinedBodies);
        
        // Speeds up the game slightly with each new food spawn
        moveInterval = Math.max(0.05f, moveInterval - 0.005f); 
    }

    /**
     * Renders the game entities on the screen using shapes.
     * @param shapeRenderer The rendering tool.
     */
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(ColorManager.GB_DARKEST); // food
        shapeRenderer.rect(food.getPosition().x * TILE_SIZE, food.getPosition().y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        shapeRenderer.setColor(ColorManager.GB_DARK); // solid snake - player 1
        for (Vector2 part : snake.getBody()) {
            shapeRenderer.rect(part.x * TILE_SIZE, part.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // dithering effect for snake 2 
        for (int i = 0; i < snake2.getBody().size(); i++) {
            Vector2 part = snake2.getBody().get(i);
            float x = part.x * TILE_SIZE;
            float y = part.y * TILE_SIZE;
            
            
            float pixelSize = 4; 
            int cols = (int)(TILE_SIZE / pixelSize);
            int rows = (int)(TILE_SIZE / pixelSize);
            
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {

                    if ((row + col + i) % 2 == 0) {
                        shapeRenderer.setColor(ColorManager.GB_DARK); 
                    } else {
                        shapeRenderer.setColor(ColorManager.GB_LIGHT); 
                    }
                    
                    shapeRenderer.rect(
                        x + col * pixelSize, 
                        y + row * pixelSize, 
                        pixelSize, 
                        pixelSize
                    );
                }
            }
        }
    }

    /**
     * Gets the current player score.
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    public int getScore2() {
        return score2;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinnerMessage() {
        return winnerMessage;
    }
}
