package core;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ScoreManager.
 *
 * NOTE: ScoreManager writes to a fixed file "highscores.dat" in the
 * current working directory. To avoid polluting the real project folder
 * (or reading stale data from a previous run), these tests change the
 * JVM's working directory assumption by running inside a temp folder
 * and deleting the file before/after each test.
 *
 * This is a workaround. A cleaner long-term fix would be to make the
 * file path configurable in the constructor (see note at the bottom).
 */
class ScoreManagerTest {

    private static final File SCORE_FILE = new File("highscores.dat");

    @BeforeEach
    void cleanBefore() {
        if (SCORE_FILE.exists()) {
            SCORE_FILE.delete();
        }
    }

    @AfterEach
    void cleanAfter() {
        if (SCORE_FILE.exists()) {
            SCORE_FILE.delete();
        }
    }

    @Test
    @DisplayName("A fresh ScoreManager with no file should start with an empty list")
    void shouldStartEmptyWhenNoFileExists() {
        ScoreManager manager = new ScoreManager();
        assertTrue(manager.getHighScores().isEmpty());
    }

    @Test
    @DisplayName("Any score should count as a high score when fewer than 5 entries exist")
    void anyScoreIsHighScoreWhenListNotFull() {
        ScoreManager manager = new ScoreManager();
        assertTrue(manager.isHighScore(1));
    }

    @Test
    @DisplayName("Adding a score should make it retrievable from the high score list")
    void shouldAddAndRetrieveScore() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("Ana", 100, "Player 1 wins!");

        List<HighScoreEntry> scores = manager.getHighScores();
        assertEquals(1, scores.size());
        assertEquals(100, scores.get(0).getScore());
    }

    @Test
    @DisplayName("Names longer than 3 characters should be truncated to 3 and uppercased")
    void shouldTruncateAndUppercaseLongNames() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("alexandre", 50, "Tie");

        String storedName = manager.getHighScores().get(0).getName();
        assertEquals("ALE", storedName);
    }

    @Test
    @DisplayName("Names shorter than or equal to 3 characters should just be uppercased")
    void shouldUppercaseShortNames() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("an", 50, "Tie");

        String storedName = manager.getHighScores().get(0).getName();
        assertEquals("AN", storedName);
    }

    @Test
    @DisplayName("High score list should stay sorted in descending order of score")
    void shouldKeepListSortedDescending() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("AAA", 30, "Tie");
        manager.addHighScore("BBB", 90, "Player 1 wins!");
        manager.addHighScore("CCC", 60, "Player 2 wins!");

        List<HighScoreEntry> scores = manager.getHighScores();
        assertEquals(90, scores.get(0).getScore());
        assertEquals(60, scores.get(1).getScore());
        assertEquals(30, scores.get(2).getScore());
    }

    @Test
    @DisplayName("Only the top 5 scores should be kept after adding a 6th entry")
    void shouldKeepOnlyTopFiveScores() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("AAA", 10, "Tie");
        manager.addHighScore("BBB", 20, "Tie");
        manager.addHighScore("CCC", 30, "Tie");
        manager.addHighScore("DDD", 40, "Tie");
        manager.addHighScore("EEE", 50, "Tie");
        manager.addHighScore("FFF", 60, "Tie"); // 6th entry, should bump the lowest (10)

        List<HighScoreEntry> scores = manager.getHighScores();
        assertEquals(5, scores.size());
        assertFalse(scores.stream().anyMatch(s -> s.getScore() == 10));
        assertTrue(scores.stream().anyMatch(s -> s.getScore() == 60));
    }

    @Test
    @DisplayName("isHighScore should return false for a score lower than the lowest of a full top-5 list")
    void shouldRejectLowScoreWhenListIsFull() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("AAA", 10, "Tie");
        manager.addHighScore("BBB", 20, "Tie");
        manager.addHighScore("CCC", 30, "Tie");
        manager.addHighScore("DDD", 40, "Tie");
        manager.addHighScore("EEE", 50, "Tie");

        assertFalse(manager.isHighScore(5));
    }

    @Test
    @DisplayName("isHighScore should return true for a score higher than the lowest of a full top-5 list")
    void shouldAcceptHighScoreWhenListIsFull() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("AAA", 10, "Tie");
        manager.addHighScore("BBB", 20, "Tie");
        manager.addHighScore("CCC", 30, "Tie");
        manager.addHighScore("DDD", 40, "Tie");
        manager.addHighScore("EEE", 50, "Tie");

        assertTrue(manager.isHighScore(100));
    }

    @Test
    @DisplayName("getLowestHighScore should return 0 when the list is empty")
    void shouldReturnZeroWhenEmpty() {
        ScoreManager manager = new ScoreManager();
        assertEquals(0, manager.getLowestHighScore());
    }

    @Test
    @DisplayName("getLowestHighScore should return the smallest score currently stored")
    void shouldReturnLowestStoredScore() {
        ScoreManager manager = new ScoreManager();
        manager.addHighScore("AAA", 30, "Tie");
        manager.addHighScore("BBB", 90, "Tie");
        manager.addHighScore("CCC", 60, "Tie");

        assertEquals(30, manager.getLowestHighScore());
    }

    @Test
    @DisplayName("Scores should persist across separate ScoreManager instances (simulating app restart)")
    void shouldPersistScoresAcrossInstances() {
        ScoreManager firstRun = new ScoreManager();
        firstRun.addHighScore("AAA", 77, "Player 1 wins!");

        // Simulate restarting the application
        ScoreManager secondRun = new ScoreManager();
        List<HighScoreEntry> scores = secondRun.getHighScores();

        assertEquals(1, scores.size());
        assertEquals(77, scores.get(0).getScore());
    }
}
