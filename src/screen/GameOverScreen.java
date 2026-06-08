package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.GameBoard;

/**
 * Represents the Game Over screen displayed when the game ends.
 * Shows the final score and provides options to restart or return to the menu.
 */
public class GameOverScreen implements Screen {
    final SnakeGameMain game;
    private GameBoard board;

    /**
     * Constructs the GameOverScreen with a reference to the main game and the final game board state.
     * @param game The main game instance.
     * @param board The final state of the game board at the time of game over.
     */
    public GameOverScreen(SnakeGameMain game, GameBoard board) {
        this.game = game;
        this.board = board;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        game.batch.begin();
        String winnerMessage = board.getWinnerMessage();
        if (winnerMessage.isEmpty()) {
            winnerMessage = "Game Over!";
        }
        game.font.draw(game.batch, winnerMessage, 200, 300);
        game.font.draw(game.batch, "Final Score: " + board.getScore(), 200, 250);
        game.font.draw(game.batch, "Press R to Restart or M for Menu", 200, 200);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            game.setScreen(new MenuScreen(game));
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