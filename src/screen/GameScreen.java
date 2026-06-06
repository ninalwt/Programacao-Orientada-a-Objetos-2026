package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.GameBoard;

/**
 * Represents the main gameplay screen.
 * Initializes and renders the GameBoard where the core mechanics happen.
 */
public class GameScreen implements Screen {
    final SnakeGaameMain game;
    private GameBoard board;

    /**
     * Constructs the GameScreen and initializes the game board.
     * @param game The main game instance.
     */
    public GameScreen(SnakeGameMain game) {
        this.game = game;
        this.board = new GameBoard();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        board.update(delta);

        game.shapeRenderer.begin(com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled);
        board.render(game.shapeRenderer);
        game.shapeRenderer.end();

        game.batch.begin();
        game.font.draw(game.batch, "Score: " + board.getScore(), 10, 470);
        game.font.draw(game.batch, "Press M to return to Menu", 10, 450);
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
