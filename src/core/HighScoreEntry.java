package core;

import java.io.Serializable;

/*
 * Represents an entry on high score entry
 */
public class HighScoreEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int score;
    private String winner; 

    // constructors
    public HighScoreEntry(String name, int score, String winner) {
        this.name = name;
        this.score = score;
        this.winner = winner;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getWinner() {
        return winner;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("%-3s %4d (%s)", name, score, winner);
    }
}
