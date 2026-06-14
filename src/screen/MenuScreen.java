package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;

/**
 * Represents the main menu of the game.
 * Allows the user to start the game or exit the application.
 */
public class MenuScreen implements Screen {
    final SnakeGameMain game;

    /**
     * Constructs the MenuScreen.
     * @param game The main game instance.
     */
    public MenuScreen(SnakeGameMain game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        game.batch.begin();
        game.font.draw(game.batch, "SNAKE GAME", 250, 350);
        game.font.draw(game.batch, "Press ENTER to Play", 230, 250);
        game.font.draw(game.batch, "Press I to Instructions Screen", 200, 225);
        game.font.draw(game.batch, "Press ESC to Exit", 240, 200);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        
        // instructions screen
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            game.setScreen(new InstructionsScreen(game));
            dispose();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}