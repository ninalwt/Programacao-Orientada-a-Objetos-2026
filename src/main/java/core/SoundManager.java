package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/*
 * Sound effects of the game
 */
public class SoundManager {
    private Sound collectFx;
    private Sound deathFx;

    public SoundManager() {
        try {
            collectFx = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/collect_item.mp3"));
            deathFx = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/death.mp3"));
        } catch (Exception e) {
            System.err.println("error catching sounds: " + e.getMessage());
        }
    } 

    // plays the sound fx when the snake eats
    public void playCollectSound() {
        if (collectFx != null) {
            collectFx.play(0.5f);
        }
    }

    // plays the sound fx when the snake dies
    public void playDeathSound() {
        if (deathFx != null) {
            deathFx.play(0.7f);
        }
    }

    public void dispose() {
        if (collectFx != null) collectFx.dispose();
        if (deathFx != null) deathFx.dispose();
    }
}
