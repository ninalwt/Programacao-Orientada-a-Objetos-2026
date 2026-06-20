package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.TextHelper;
import core.ColorManager;
import core.ScoreManager;

/*
 * Screen to insert a name when hits a new record 
 */
public class NewHighScoreScreen implements Screen {
    // Reference to the main game instance and the score manager
    final SnakeGameMain game;
    private ScoreManager scoreManager;
    
    // Data for the new high score entry
    private int finalScore;
    private String winner;
    private StringBuilder name;
    private boolean finished;
    private float blinkTimer;
    private boolean showCursor;
    
    /**
     * Constructs the NewHighScoreScreen.
     * @param game The main game instance.
     * @param finalScore The final score achieved.
     * @param winner The name of the winner.
     */
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

    /**
     * Renders the new high score screen.
     * @param delta The time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        // background
        ScreenUtils.clear(ColorManager.GB_LIGHTEST.r, ColorManager.GB_LIGHTEST.g, ColorManager.GB_LIGHTEST.b, 1);
        
        // blinks the cursor
        blinkTimer += delta;
        if (blinkTimer >= 0.5f) {
            blinkTimer = 0;
            showCursor = !showCursor;
        }
        
        game.batch.begin();
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawCentered(game.batch, game.font, "~ NEW HIGH SCORE!", 370);
        
        TextHelper.drawCentered(game.batch, game.font, "Your score: " + finalScore + " points", 320);
        TextHelper.drawCentered(game.batch, game.font, "Winner: : " + winner, 290);

        
        // Instructions
        TextHelper.drawCentered(game.batch, game.font, "ENTER YOUR NAME - 3 LETTERS:", 240);

        
        // name blinking while writing
        String displayName = name.toString();
        if (displayName.length() < 3 && showCursor) {
            displayName += "_";
        }
        
        // name in the middle of the screen
        game.font.setColor(ColorManager.GB_DARK);
        TextHelper.drawCentered(game.batch, game.font, displayName, 190);
        
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawCentered(game.batch, game.font, "A-Z keys - BACKSPACE - ENTER to save", 140);
        
        game.batch.end();
        
        if (!finished) {
            handleInput();
        } else {
            game.setScreen(new MenuScreen(game));
            dispose();
        }
    }
    
    /**
     * Handles input for name entry.
     */
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
