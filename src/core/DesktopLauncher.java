package core;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * The entry point for the desktop version of the game.
 * This file contains the main method required by Java to run the application.
 */
public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        
        // Sets the frame rate and window properties
        config.setForegroundFPS(20);
        config.setTitle("SnakeGame");
        config.setWindowedMode(640, 480);
        
        // Starts the game using main class
        new Lwjgl3Application(new SnakeGameMain(), config);
    }
}