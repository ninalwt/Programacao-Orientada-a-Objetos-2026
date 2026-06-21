package core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HighScoreEntry data class.
 */
class HighScoreEntryTest {

    @Test
    @DisplayName("Constructor should correctly set name, score, and winner")
    void shouldStoreConstructorValues() {
        HighScoreEntry entry = new HighScoreEntry("ABC", 150, "Player 1 wins!");

        assertEquals("ABC", entry.getName());
        assertEquals(150, entry.getScore());
        assertEquals("Player 1 wins!", entry.getWinner());
    }

    @Test
    @DisplayName("setName should update the stored name")
    void shouldUpdateName() {
        HighScoreEntry entry = new HighScoreEntry("ABC", 100, "Tie");
        entry.setName("XYZ");
        assertEquals("XYZ", entry.getName());
    }

    @Test
    @DisplayName("setScore should update the stored score")
    void shouldUpdateScore() {
        HighScoreEntry entry = new HighScoreEntry("ABC", 100, "Tie");
        entry.setScore(999);
        assertEquals(999, entry.getScore());
    }

    @Test
    @DisplayName("toString should contain name, score, and winner information")
    void toStringShouldContainAllFields() {
        HighScoreEntry entry = new HighScoreEntry("AB", 42, "Player 2 wins!");
        String result = entry.toString();

        assertTrue(result.contains("AB"));
        assertTrue(result.contains("42"));
        assertTrue(result.contains("Player 2 wins!"));
    }

    @Test
    @DisplayName("toString should pad the score field to a fixed width")
    void toStringShouldFormatScoreWithPadding() {
        HighScoreEntry entry = new HighScoreEntry("AB", 5, "Tie");
        String expected = String.format("%-3s %4d (%s)", "AB", 5, "Tie");
        assertEquals(expected, entry.toString());
    }
}
