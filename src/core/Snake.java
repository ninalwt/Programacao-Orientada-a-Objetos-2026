package core;

import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;

/**
 * Represents the snake entity in the game.
 * Manages movement, growth, and direction state.
 */
public class Snake {
    private LinkedList<Vector2> body;
    private Vector2 direction;
    private boolean justAte;

    /**
     * Initializes the snake at a specific starting position.
     * @param startX Initial X coordinate on the grid.
     * @param startY Initial Y coordinate on the grid.
     */
    public Snake(float startX, float startY) {
        body = new LinkedList<>();
        body.add(new Vector2(startX, startY));
        direction = new Vector2(1, 0); 
        justAte = false;
    }

    /**
     * Updates the snake's position based on its current direction.
     */
    public void move() {
        Vector2 head = body.getFirst();
        Vector2 newHead = new Vector2(head.x + direction.x, head.y + direction.y);
        
        body.addFirst(newHead);

        if (!justAte) {
            body.removeLast();
        } else {
            justAte = false;
        }
    }

    /**
     * Flags the snake to grow on its next movement step.
     */
    public void grow() {
        justAte = true;
    }

    /**
     * Updates the movement direction.
     * Prevents the snake from reversing directly into itself.
     * @param newDir The new direction vector.
     */
    public void setDirection(Vector2 newDir) {
        if (direction.x + newDir.x != 0 || direction.y + newDir.y != 0) {
            this.direction = newDir;
        }
    }

    /**
     * Gets the collection of positions making up the snake's body.
     * @return A linked list of body coordinates.
     */
    public LinkedList<Vector2> getBody() {
        return body;
    }
}
