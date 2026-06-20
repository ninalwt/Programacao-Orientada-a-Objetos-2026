package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.TextHelper;
import core.ColorManager;

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
        // background
        ScreenUtils.clear(ColorManager.GB_LIGHTEST.r, ColorManager.GB_LIGHTEST.g, ColorManager.GB_LIGHTEST.b, 1);

        game.batch.begin();
        
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawCentered(game.batch, game.font, "SNAKE GAME", 350);
        TextHelper.drawCentered(game.batch, game.font, "Press ENTER to Play", 280);
        TextHelper.drawCentered(game.batch, game.font, "Press I for Instructions", 255);
        TextHelper.drawCentered(game.batch, game.font, "Press H for High Scores", 230);
        TextHelper.drawCentered(game.batch, game.font, "Press ESC to Exit", 205);

        TextHelper.drawCentered(game.batch, game.font, "Natalia - Nina - Thiago", 75);
        TextHelper.drawCentered(game.batch, game.font, "2026 ©", 50);
        
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            game.setScreen(new HighScoreScreen(game));
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