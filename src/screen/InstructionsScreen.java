package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import core.ColorManager;
import core.SnakeGameMain;
import core.TextHelper;

/*
 * Screen to show the game instructions and rules.
 */
public class InstructionsScreen implements Screen{
    final SnakeGameMain game;
    private ShapeRenderer shapeRenderer;

    public InstructionsScreen(SnakeGameMain game) {
        this.game = game;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        // background
        ScreenUtils.clear(ColorManager.GB_LIGHTEST.r, ColorManager.GB_LIGHTEST.g, ColorManager.GB_LIGHTEST.b, 1);

        game.batch.begin();

        // title - centered
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawCentered(game.batch, game.font, "=== INSTRUCTIONS ===", 450);

        // controls section - with left offset
        TextHelper.drawLeftWithOffset(game.batch, game.font, "CONTROLS:", 400, 25);

        TextHelper.drawLeftWithOffset(game.batch, game.font, "Player 1 - Solid Snake", 370, 25);

        TextHelper.drawLeftWithOffset(game.batch, game.font, "Arrow Keys:  UP LEFT DOWN RIGHT", 340, 45);

        TextHelper.drawLeftWithOffset(game.batch, game.font, "Player 2 - Dithered Snake", 300, 25);

        TextHelper.drawLeftWithOffset(game.batch, game.font, "WASD Keys:  W A S D", 270, 45);

        // rules section - with left offset
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawLeftWithOffset(game.batch, game.font, "RULES:", 220, 25);

        game.font.getData().setScale(0.85f);
        TextHelper.drawLeftWithOffset(game.batch, game.font, "- Eat red food to grow and gain 10 points", 190, 45);
        TextHelper.drawLeftWithOffset(game.batch, game.font, "- Game speeds up slightly after each food eaten", 165, 45);
        TextHelper.drawLeftWithOffset(game.batch, game.font, "- Avoid hitting yourself or the other snake", 140, 45);
        TextHelper.drawLeftWithOffset(game.batch, game.font, "- Walls wrap around - go out one side,", 115, 45);
       
        TextHelper.drawLeftWithOffset(game.batch, game.font, "  come in the opposite side!", 90, 45);
        // text to go back to menu - centered
        game.font.getData().setScale(1f);
        TextHelper.drawCentered(game.batch, game.font, "Press ESC to return to Main Menu", 50);

        
        game.batch.end();

        // verifies if ESC was pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

   
}

