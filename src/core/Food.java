package core;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;

/**
 * Represents the collectible food item on the grid.
 */
public class Food {
    private Vector2 position;

    /**
     * Constructs food at an initial coordinate.
     * @param x Initial X grid position.
     * @param y Initial Y grid position.
     */
    public Food(float x, float y) {
        position = new Vector2(x, y);
    }

    /**
     * Randomly repositions the food within the grid boundaries.
     * Ensures the food does not spawn inside the snake's body.
     * @param gridWidth Maximum width of the grid.
     * @param gridHeight Maximum height of the grid.
     * @param snakeBody The current body parts of the snake.
     */
    public void respawn(int gridWidth, int gridHeight, LinkedList<Vector2> snakeBody) {
        boolean validPosition = false;
        while (!validPosition) {
            int newX = MathUtils.random(0, gridWidth - 1);
            int newY = MathUtils.random(0, gridHeight - 1);
            position.set(newX, newY);
            
            validPosition = true;
            for (Vector2 part : snakeBody) {
                if (part.equals(position)) {
                    validPosition = false;
                    break;
                }
            }
        }
    }

    /**
     * Gets the current position of the food.
     * @return The vector coordinate of the food.
     */
    public Vector2 getPosition() {
        return position;
    }
}
