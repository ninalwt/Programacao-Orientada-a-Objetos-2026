package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.ScoreManager;

/*
 * Screen to insert a name when hits a new record 
 */
public class NewHighScoreScreen implements Screen {
    final SnakeGameMain game;
    private ScoreManager scoreManager;
    private int finalScore;
    private String winner;

    private StringBuilder name;
     private boolean finished;
    private float blinkTimer;
    private boolean showCursor;
    
    public NewHighScoreScreen(SnakeGameMain game, int finalScore, String winner) {
        this.game = game;
        this.scoreManager = new ScoreManager();
        this.finalScore = finalScore;
        this.winner = winner;
        this.name = new StringBuilder();
        this.finished = false;
        this.blinkTimer = 0;
        this.showCursor = true;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.2f, 1);
        
        // blinks the cursor
        blinkTimer += delta;
        if (blinkTimer >= 0.5f) {
            blinkTimer = 0;
            showCursor = !showCursor;
        }
        
        game.batch.begin();
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "NEW HIGH SCORE!", 240, 420);
        
        game.font.setColor(Color.WHITE); 
        game.font.draw(game.batch, "Your score: " + finalScore + " points", 250, 370);
        game.font.draw(game.batch, "Winner: " + winner, 280, 340);
        
        // Instructions
        game.font.setColor(Color.CYAN);
        game.font.draw(game.batch, "ENTER YOUR NAME (3 LETTERS)", 220, 290);
        
        // name blinking while writing
        game.font.setColor(Color.GREEN);
        String displayName = name.toString();
        if (displayName.length() < 3 && showCursor) {
            displayName += "_";
        }
        
        // name in the middle of the screen
        float textWidth = displayName.length() * 12; // Aproximação
        float x = (640 - textWidth) / 2;
        game.font.draw(game.batch, displayName, x, 240);
        
        game.font.setColor(Color.GRAY);
        game.font.draw(game.batch, "A-Z keys | BACKSPACE | ENTER to save", 190, 160);
        
        game.batch.end();
        
        if (!finished) {
            handleInput();
        } else {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }
    
    private void handleInput() {
        // backspace to delete
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && name.length() > 0) {
            name.deleteCharAt(name.length() - 1);
        }
        
        // enter to save the name
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && name.length() > 0) {
            // blank characters
            while (name.length() < 3) {
                name.append(" ");
            }
            scoreManager.addHighScore(name.toString(), finalScore, winner);
            finished = true;
            return;
        }
        
        // only three characters (just as arcades)
        if (name.length() >= 3) {
            return;
        }

        for (int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                char c = (char) ('A' + (key - Input.Keys.A));
                name.append(c);
            }
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}

}
