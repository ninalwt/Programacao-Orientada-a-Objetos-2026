package core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Changes the high score file
 */
public class ScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.dat";
    private List<HighScoreEntry> highScores;
    
    public ScoreManager() {
        highScores = new ArrayList<>();
        loadHighScores();
    }
    
    // load the highscores from the file.
    @SuppressWarnings("unchecked")
    private void loadHighScores() {
        File file = new File(HIGH_SCORE_FILE);
        if (!file.exists()) {
            highScores = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            highScores = (List<HighScoreEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("error loading highscores: " + e.getMessage());
            highScores = new ArrayList<>();
        }
    }


    // save the highscores on the file
    private void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            System.err.println("error saving highscores: " + e.getMessage());
        }
    }

    // verifies if the score would be within the top 5
    public boolean isHighScore(int score) {
        if (highScores.size() < 5) {
            return true;
        }

        List<HighScoreEntry> sorted = new ArrayList<>(highScores);
        sorted.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        
        int lowestHighScore = sorted.get(sorted.size() - 1).getScore();

        return score > lowestHighScore;

    }

    // adds a new score if it is bigger than the ones in the file
    public void addHighScore(String name, int score, String winner) {
        // name with max 3 characters
        String shortName = name.length() > 3 ? name.substring(0, 3).toUpperCase() : name.toUpperCase();
        
        highScores.add(new HighScoreEntry(shortName, score, winner));
        highScores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));

        
        // only top 5
        while (highScores.size() > 5) {
            highScores.remove(highScores.size() - 1);
        }
        
        saveHighScores();
    }

    // returns the sorted highscore list
    public List<HighScoreEntry> getHighScores() {
        List<HighScoreEntry> sorted = new ArrayList<>(highScores);
        sorted.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return sorted;
    }

    public int getLowestHighScore() {
        if (highScores.isEmpty()) {
            return 0;
        }
        List<HighScoreEntry> sorted = getHighScores();
        return sorted.get(sorted.size() - 1).getScore();
    }

}
