package core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import screen.MenuScreen;

/**
 * Main entry point for the LibGDX game.
 * Manages global resources like SpriteBatch, ShapeRenderer, and fonts.
 */
public class SnakeGameMain extends Game {
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;
    public BitmapFont font;
    public SoundManager soundManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(); 
        soundManager = new SoundManager();
        
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); 
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        soundManager.dispose();
    }
}