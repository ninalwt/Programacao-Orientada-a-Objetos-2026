package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.ColorManager;
import core.GameBoard;
import core.ScoreManager;
import core.TextHelper;

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
        // background
        ScreenUtils.clear(ColorManager.GB_LIGHTEST.r, ColorManager.GB_LIGHTEST.g, ColorManager.GB_LIGHTEST.b, 1);


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
        
        boolean isItAHighScore = scoreManager.isHighScore(winnerScore);


        // main message
        game.batch.begin();
        
        if (winnerMessage.isEmpty()) {
            winnerMessage = "Game Over!";
        }

        // Draw centered text using TextHelper
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawCentered(game.batch, game.font, winnerMessage, 380);
        TextHelper.drawCentered(game.batch, game.font, "Player 1 - Green: " + board.getScore() + " points", 320);
        TextHelper.drawCentered(game.batch, game.font, "Player 2 - Blue: " + board.getScore2() + " points", 290);
        TextHelper.drawCentered(game.batch, game.font, "---------------------", 260);

        // text based if it is a record or isn't
        if (isItAHighScore && !winnerName.equals("Tie")) {

            TextHelper.drawCentered(game.batch, game.font, "( NEW HIGH SCORE! (", 230);

            TextHelper.drawCentered(game.batch, game.font, ") Press N to save your name! ) ", 195);

            TextHelper.drawCentered(game.batch, game.font, "Press R to Restart", 160);
            TextHelper.drawCentered(game.batch, game.font, "Press M for Menu", 135);
        } else {
            TextHelper.drawCentered(game.batch, game.font, "Press R to Restart", 200);
            TextHelper.drawCentered(game.batch, game.font, "Press M for Menu", 170);
        }


        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        } else if (isItAHighScore && !winnerName.equals("Tie") && Gdx.input.isKeyJustPressed(Input.Keys.N)) {
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