package core;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Food class.
 * Focuses on respawn boundaries and avoidance of snake body overlap.
 */
class FoodTest {

    @Test
    @DisplayName("Food should store its initial position correctly")
    void shouldStoreInitialPosition() {
        Food food = new Food(3, 4);
        assertEquals(new Vector2(3, 4), food.getPosition());
    }

    @RepeatedTest(20)
    @DisplayName("Respawned food should always stay within grid boundaries")
    void respawnShouldStayWithinBounds() {
        Food food = new Food(0, 0);
        LinkedList<Vector2> emptyBody = new LinkedList<>();

        food.respawn(10, 8, emptyBody);

        Vector2 pos = food.getPosition();
        assertTrue(pos.x >= 0 && pos.x < 10, "x should be within [0,10)");
        assertTrue(pos.y >= 0 && pos.y < 8, "y should be within [0,8)");
    }

    @Test
    @DisplayName("Respawn should never place food on top of the snake's body")
    void respawnShouldAvoidSnakeBody() {
        // Fill almost the entire 2x2 grid with snake body, leaving only (1,1) free
        LinkedList<Vector2> snakeBody = new LinkedList<>();
        snakeBody.add(new Vector2(0, 0));
        snakeBody.add(new Vector2(0, 1));
        snakeBody.add(new Vector2(1, 0));

        Food food = new Food(5, 5);
        food.respawn(2, 2, snakeBody);

        assertEquals(new Vector2(1, 1), food.getPosition());
    }

    @Test
    @DisplayName("Respawn with an empty snake body should not throw and should land within bounds")
    void respawnWithEmptyBodyShouldWork() {
        Food food = new Food(0, 0);
        assertDoesNotThrow(() -> food.respawn(5, 5, new LinkedList<>()));
    }
}
