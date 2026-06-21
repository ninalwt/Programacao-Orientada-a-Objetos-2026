package core;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Snake class.
 * Covers movement, growth, direction changes, and body integrity.
 */
@ExtendWith(GdxTestExtension.class)
class SnakeTest {

    private Snake snake;

    @BeforeEach
    void setUp() {
        // Snake starts at (5,5) moving right
        snake = new Snake(5, 5, new Vector2(1, 0));
    }

    @Test
    @DisplayName("New snake should start with a single body segment at its initial position")
    void shouldStartWithSingleSegment() {
        LinkedList<Vector2> body = snake.getBody();
        assertEquals(1, body.size());
        assertEquals(new Vector2(5, 5), body.getFirst());
    }

    @Test
    @DisplayName("Moving right should advance the head by one cell on the X axis")
    void shouldMoveRightAlongXAxis() {
        snake.move();
        assertEquals(new Vector2(6, 5), snake.getBody().getFirst());
    }

    @Test
    @DisplayName("Snake should keep the same length when moving without eating")
    void shouldKeepSameLengthWhenNotEating() {
        int sizeBefore = snake.getBody().size();
        snake.move();
        assertEquals(sizeBefore, snake.getBody().size());
    }

    @Test
    @DisplayName("Snake should grow by one segment on the move right after eating")
    void shouldGrowAfterEating() {
        int sizeBefore = snake.getBody().size();
        snake.grow();
        snake.move();
        assertEquals(sizeBefore + 1, snake.getBody().size());
    }

    @Test
    @DisplayName("Growth flag should only apply to a single move, not persist")
    void growthFlagShouldNotPersist() {
        snake.grow();
        snake.move(); // grows here
        int sizeAfterGrowth = snake.getBody().size();
        snake.move(); // should NOT grow again
        assertEquals(sizeAfterGrowth, snake.getBody().size());
    }

    @Test
    @DisplayName("Snake should preserve its old tail position correctly while growing")
    void tailShouldBePreservedWhenGrowing() {
        Vector2 originalTail = new Vector2(snake.getBody().getLast());
        snake.grow();
        snake.move();
        // After growing, the old tail position should still be in the body
        assertTrue(snake.getBody().contains(originalTail));
    }

    @Test
    @DisplayName("Setting a perpendicular direction should change the snake's heading")
    void shouldAllowPerpendicularTurn() {
        snake.setDirection(new Vector2(0, 1)); // turn up while moving right
        snake.move();
        assertEquals(new Vector2(5, 6), snake.getBody().getFirst());
    }

    @Test
    @DisplayName("Setting the exact opposite direction should be ignored (no 180-degree turn)")
    void shouldIgnoreOppositeDirection() {
        // snake is moving right (1,0); attempt to reverse to left (-1,0)
        snake.setDirection(new Vector2(-1, 0));
        snake.move();
        // if reversal was blocked, snake continues moving right
        assertEquals(new Vector2(6, 5), snake.getBody().getFirst());
    }

    @Test
    @DisplayName("Setting the same direction again should have no negative effect")
    void shouldAllowSettingSameDirection() {
        snake.setDirection(new Vector2(1, 0));
        snake.move();
        assertEquals(new Vector2(6, 5), snake.getBody().getFirst());
    }

    @Test
    @DisplayName("Multiple consecutive moves should advance the head correctly")
    void shouldAdvanceCorrectlyOverMultipleMoves() {
        snake.move();
        snake.move();
        snake.move();
        assertEquals(new Vector2(8, 5), snake.getBody().getFirst());
    }

    @Test
    @DisplayName("Snake moving up should increase Y and keep X unchanged")
    void shouldMoveUpAlongYAxis() {
        Snake upSnake = new Snake(10, 10, new Vector2(0, 1));
        upSnake.move();
        assertEquals(new Vector2(10, 11), upSnake.getBody().getFirst());
    }

    @Test
    @DisplayName("Snake growing multiple times in sequence should add one segment per growth")
    void shouldGrowMultipleTimes() {
        int sizeBefore = snake.getBody().size();

        snake.grow();
        snake.move(); // size + 1

        snake.grow();
        snake.move(); // size + 2

        assertEquals(sizeBefore + 2, snake.getBody().size());
    }
}
