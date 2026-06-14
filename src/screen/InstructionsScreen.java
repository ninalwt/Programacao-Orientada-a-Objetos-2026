package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;

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
        // clears the screen 
        ScreenUtils.clear(0, 0, 0.1f, 1);

        // draws a semi-transparent for better reading
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0,0,0,0.7f);
        shapeRenderer.rect(50,50,540,380);
        shapeRenderer.end();

        game.batch.begin();

        // title
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "INSTRUCTIONS:", 220, 450);

        // controls section
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "CONTROLS:", 100, 400);

        game.font.setColor(Color.GREEN);
        game.font.draw(game.batch, "Player 1 (Green Snake)", 120, 370);
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "Arrow Keys:  UP LEFT DOWN RIGHT", 140, 340);
        
        game.font.setColor(Color.BLUE);
        game.font.draw(game.batch, "Player 2 (Blue Snake)", 120, 300);
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "WASD Keys:  W A S D", 140, 270);
        
        // rules section
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "RULES:", 100, 220);
        
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "- Eat red food to grow and gain 10 points", 120, 190);
        game.font.draw(game.batch, "- Game speeds up slightly after each food eaten", 120, 165);
        game.font.draw(game.batch, "- Avoid hitting yourself or the other snake", 120, 140);
        game.font.draw(game.batch, "- Walls wrap around - go out one side,", 120, 115);
        game.font.draw(game.batch, "  come in the opposite side!", 140, 90);
        
        // text to go back to menu
        game.font.setColor(Color.CYAN);
        game.font.draw(game.batch, "Press ESC to return to Main Menu", 200, 50);
        
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

