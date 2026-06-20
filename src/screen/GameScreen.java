package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.ColorManager;
import core.GameBoard;
import core.TextHelper;
/**
 * Represents the main gameplay screen.
 * Initializes and renders the GameBoard where the core mechanics happen.
 */
public class GameScreen implements Screen {
    final SnakeGameMain game;
    private GameBoard board;

    /**
     * Constructs the GameScreen and initializes the game board.
     * @param game The main game instance.
     */
    public GameScreen(SnakeGameMain game) {
        this.game = game;
        this.board = new GameBoard(game.soundManager);
    }

    @Override
    public void render(float delta) {
        // background
        ScreenUtils.clear(ColorManager.GB_LIGHTEST.r, ColorManager.GB_LIGHTEST.g, ColorManager.GB_LIGHTEST.b, 1);


        board.update(delta);

        if (board.isGameOver()) {
            game.setScreen(new GameOverScreen(game, board));
            dispose();
            return;
        }

        game.shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        board.render(game.shapeRenderer);
        game.shapeRenderer.end();

        // renders the score panel
        game.batch.begin();
        TextHelper.drawLeft(game.batch, game.font, "Player 1: " + board.getScore(), 470);
        TextHelper.drawRight(game.batch, game.font, "Player 2: " + board.getScore2(), 470);
        TextHelper.drawCentered(game.batch, game.font, "Press M for Menu", 30);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
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