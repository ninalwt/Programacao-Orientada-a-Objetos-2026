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

        // Mostra o diretório de trabalho atual
        String workingDir = System.getProperty("user.dir");
        System.out.println("Working directory: " + workingDir);
        
        // Lista arquivos na raiz
        String[] rootFiles = new java.io.File(workingDir).list();
        System.out.println("Arquivos na raiz:");
        for (String file : rootFiles) {
            System.out.println("  - " + file);
        }
        
        // Verifica se a pasta assets existe
        java.io.File assetsDir = new java.io.File(workingDir + "/assets");
        System.out.println("Assets directory exists: " + assetsDir.exists());
        
        if (assetsDir.exists()) {
            String[] assetsFiles = assetsDir.list();
            System.out.println("Conteúdo de assets/:");
            for (String file : assetsFiles) {
                System.out.println("  - " + file);
            }
        }
        
        // Agora tenta carregar o som
        try {
            collectFx = Gdx.audio.newSound(Gdx.files.internal("sounds/collect_item.mp3"));
            System.out.println("✓ Sound loaded!");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
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
