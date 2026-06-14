package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.GameBoard;
import core.ScoreManager;

/**
 * Represents the Game Over screen displayed when the game ends.
 * Shows the final score and provides options to restart or return to the menu.
 */
public class GameOverScreen implements Screen {
    final SnakeGameMain game;
    private GameBoard board;
    private ScoreManager scoreManager;

    /**
     * Constructs the GameOverScreen with a reference to the main game and the final game board state.
     * @param game The main game instance.
     * @param board The final state of the game board at the time of game over.
     * @param scoreManager The top 5 highscores appearing
     */
    public GameOverScreen(SnakeGameMain game, GameBoard board) {
        this.game = game;
        this.board = board;
        this.scoreManager = new ScoreManager();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        // determines winner score
        String winnerMessage = board.getWinnerMessage();
        int winnerScore = 0;
        String winnerName = "";

        if (winnerMessage.contains("Player 1")) {
            winnerScore = board.getScore();
            winnerName = "Player 1";
        } else if (winnerMessage.contains("Player 2")) {
            winnerScore = board.getScore2();
            winnerName = "Player 2";
        } else {
            winnerScore = Math.max(board.getScore(), board.getScore2());
            winnerName = "Tie";
        }
        
        boolean isRecorde = scoreManager.isHighScore(winnerScore);


        // main message
        game.batch.begin();
        
        if (winnerMessage.isEmpty()) {
            winnerMessage = "Game Over!";
        }

        game.font.draw(game.batch, winnerMessage, 260, 380);


        game.font.draw(game.batch, "Player 1 (Green): " + board.getScore() + " points", 220, 320);
        game.font.draw(game.batch, "Player 2 (Blue): " + board.getScore2() + " points", 220, 290);
        game.font.draw(game.batch, "---------------------", 240, 260);

        // text based if it is a record or isnt
        if (isRecorde && !winnerName.equals("Tie")) {
            game.font.setColor(Color.YELLOW);
            game.font.draw(game.batch, "★ NEW HIGH SCORE! ★", 245, 230);
            game.font.setColor(Color.CYAN);
            game.font.draw(game.batch, "Press N to save your name!", 235, 195);
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch, "Press R to Restart", 255, 160);
            game.font.draw(game.batch, "Press M for Menu", 260, 135);
        } else {
            game.font.draw(game.batch, "Press R to Restart", 255, 200);
            game.font.draw(game.batch, "Press M for Menu", 260, 170);
        }

        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        } else if (isRecorde && !winnerName.equals("Tie") && Gdx.input.isKeyJustPressed(Input.Keys.N)) {
            game.setScreen(new NewHighScoreScreen(game, winnerScore, winnerName));
            dispose();
        }
    }


    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}