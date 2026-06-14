package screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import core.SnakeGameMain;
import core.ScoreManager;
import core.HighScoreEntry;

import java.util.List;

/*
 * Shows the top 5
 */
public class HighScoreScreen implements Screen {
    final SnakeGameMain game;
    private ScoreManager scoreManager;
    
    public HighScoreScreen(SnakeGameMain game) {
        this.game = game;
        this.scoreManager = new ScoreManager();
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        
        game.batch.begin();
        
        game.font.setColor(Color.YELLOW);
        game.font.draw(game.batch, "=== HIGH SCORES ===", 240, 450);
        
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, "RANK", 180, 400);
        game.font.draw(game.batch, "NAME", 280, 400);
        game.font.draw(game.batch, "SCORE", 370, 400);
        game.font.draw(game.batch, "WINNER", 450, 400);
                
        List<HighScoreEntry> scores = scoreManager.getHighScores();
        int y = 360;
        int rank = 1;
        
        for (HighScoreEntry entry : scores) {
            if (rank == 1) {
                game.font.setColor(Color.GOLD);
            } else if (rank == 2) {
                game.font.setColor(Color.LIGHT_GRAY);
            } else if (rank == 3) {
                game.font.setColor(0.8f, 0.5f, 0.2f, 1); // Bronze
            } else {
                game.font.setColor(Color.WHITE);
            }
            
            game.font.draw(game.batch, "#" + rank, 180, y);
            game.font.draw(game.batch, entry.getName(), 280, y);
            game.font.draw(game.batch, String.valueOf(entry.getScore()), 370, y);
            game.font.draw(game.batch, entry.getWinner(), 450, y);
            
            y -= 35;
            rank++;
        }
        
        if (scores.isEmpty()) {
            game.font.setColor(Color.GRAY);
            game.font.draw(game.batch, "No high scores yet!", 260, 330);
            game.font.draw(game.batch, "Play a game to set a record!", 240, 290);
        }
        
        game.font.setColor(Color.CYAN);
        game.font.draw(game.batch, "Press ESC to return to Main Menu", 200, 100);
        
        game.batch.end();
        
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
