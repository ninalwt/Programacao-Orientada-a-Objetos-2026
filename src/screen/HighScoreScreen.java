package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.ScoreManager;
import core.ColorManager;
import core.HighScoreEntry;
import core.TextHelper;

import java.util.List;

/*
 * Shows the top 5 high scores.
 */
public class HighScoreScreen implements Screen {
    final SnakeGameMain game;
    private ScoreManager scoreManager;
    
    // Column positions (centered with offsets)
    private static final float COL_RANK = -160;
    private static final float COL_NAME = -60;
    private static final float COL_SCORE = 40;
    private static final float COL_WINNER = 140;
    
    public HighScoreScreen(SnakeGameMain game) {
        this.game = game;
        this.scoreManager = new ScoreManager();
    }
    
    @Override
    public void render(float delta) {
        // background
        ScreenUtils.clear(ColorManager.GB_LIGHTEST.r, ColorManager.GB_LIGHTEST.g, ColorManager.GB_LIGHTEST.b, 1);

        
        game.batch.begin();
        
        // Title
        game.font.setColor(ColorManager.GB_DARKEST);
        TextHelper.drawCentered(game.batch, game.font, "=== HIGH SCORES ===", 450);
        
        // Header
        //game.font.setColor(Color.WHITE);
        game.font.getData().setScale(0.8f);
        TextHelper.drawCenteredWithOffset(game.batch, game.font, "RANK", 400, COL_RANK);
        TextHelper.drawCenteredWithOffset(game.batch, game.font, "NAME", 400, COL_NAME);
        TextHelper.drawCenteredWithOffset(game.batch, game.font, "SCORE", 400, COL_SCORE);
        TextHelper.drawCenteredWithOffset(game.batch, game.font, "WINNER", 400, COL_WINNER);
        game.font.getData().setScale(1f);
        
        // Separator line
        //game.font.setColor(Color.GRAY);
        TextHelper.drawCentered(game.batch, game.font, "-----------------------------", 385);
        
        // High scores list
        List<HighScoreEntry> scores = scoreManager.getHighScores();
        int y = 350;
        int rank = 1;
        
        for (HighScoreEntry entry : scores) {
            // Colors for top 3
            if (rank == 1) {
                game.font.setColor(ColorManager.GB_DARKEST);
            } else if (rank == 2) {
                game.font.setColor(ColorManager.GB_DARKEST);
            } else if (rank == 3) {
                game.font.setColor(ColorManager.GB_DARKEST); 
            } else {
                game.font.setColor(ColorManager.GB_DARK);
            }
            
            // Draw each column
            game.font.getData().setScale(0.9f);
            TextHelper.drawCenteredWithOffset(game.batch, game.font, "#" + rank, y, COL_RANK);
            TextHelper.drawCenteredWithOffset(game.batch, game.font, entry.getName(), y, COL_NAME);
            TextHelper.drawCenteredWithOffset(game.batch, game.font, String.valueOf(entry.getScore()), y, COL_SCORE);
            TextHelper.drawCenteredWithOffset(game.batch, game.font, entry.getWinner(), y, COL_WINNER);
            game.font.getData().setScale(1f);
            
            y -= 35;
            rank++;
        }
        
        // No scores message
        if (scores.isEmpty()) {
            game.font.setColor(Color.GRAY);
            game.font.getData().setScale(0.9f);
            TextHelper.drawCentered(game.batch, game.font, "No high scores yet!", 330);
            TextHelper.drawCentered(game.batch, game.font, "Play a game to set a record!", 290);
            game.font.getData().setScale(1f);
        }
        
        // Back to menu
        game.font.setColor(ColorManager.GB_DARKEST);
        game.font.getData().setScale(0.8f);
        TextHelper.drawCentered(game.batch, game.font, "Press ESC to return to Main Menu", 100);
        game.font.getData().setScale(1f);
        
        game.batch.end();
        
        // Input handling
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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