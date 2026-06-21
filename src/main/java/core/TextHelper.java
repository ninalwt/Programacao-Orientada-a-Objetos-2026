package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Utility class for drawing aligned text on screen.
 */
public class TextHelper {
    private static GlyphLayout layout = new GlyphLayout();
    
    /**
     * Draws text centered horizontally on the screen.
     * @param batch The SpriteBatch
     * @param font The font to use
     * @param text The text to draw
     * @param y The Y position (vertical)
     */
    public static void drawCentered(SpriteBatch batch, BitmapFont font, String text, float y) {
        layout.setText(font, text);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        font.draw(batch, text, x, y);
    }

    /**
     * Draws text centered horizontally with an X offset.
     * @param batch The SpriteBatch
     * @param font The font to use
     * @param text The text to draw
     * @param y The Y position (vertical)
     * @param xOffset The X offset from center (positive = right, negative = left)
     */
    public static void drawCenteredWithOffset(SpriteBatch batch, BitmapFont font, String text, float y, float xOffset) {
        layout.setText(font, text);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2 + xOffset;
        font.draw(batch, text, x, y);
    }
    
    /**
     * Draws text aligned to the right side of the screen.
     * @param batch The SpriteBatch
     * @param font The font to use
     * @param text The text to draw
     * @param y The Y position (vertical)
     */
    public static void drawRight(SpriteBatch batch, BitmapFont font, String text, float y) {
        layout.setText(font, text);
        float x = Gdx.graphics.getWidth() - layout.width - 10;
        font.draw(batch, text, x, y);
    }
    
    /**
     * Draws text aligned to the left side of the screen.
     * @param batch The SpriteBatch
     * @param font The font to use
     * @param text The text to draw
     * @param y The Y position (vertical)
     */
    public static void drawLeft(SpriteBatch batch, BitmapFont font, String text, float y) {
        font.draw(batch, text, 10, y);
    }

    // same thing with the centered offset
    public static void drawLeftWithOffset(SpriteBatch batch, BitmapFont font, String text, float y, float xOffSet) {
        font.draw(batch, text, 10 + xOffSet, y);
    }
    
    /**
     * Draws text at a specific position.
     * @param batch The SpriteBatch
     * @param font The font to use
     * @param text The text to draw
     * @param x The X position (horizontal)
     * @param y The Y position (vertical)
     */
    public static void draw(SpriteBatch batch, BitmapFont font, String text, float x, float y) {
        font.draw(batch, text, x, y);
    }
}