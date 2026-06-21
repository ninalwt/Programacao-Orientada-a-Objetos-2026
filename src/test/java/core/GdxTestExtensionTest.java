package core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * JUnit 5 extension that boots a minimal headless LibGDX application
 * before any tests run. This is required so that static Gdx.* references
 * (used internally by Vector2 in some LibGDX versions, MathUtils, etc.)
 * do not throw NullPointerExceptions when running outside the real game.
 *
 * Usage: @ExtendWith(GdxTestExtensionTest.class) on the test class.
 */
 class GdxTestExtensionTest implements BeforeAllCallback {

    private static boolean initialized = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!initialized) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationListener() {
                @Override public void create() {}
                @Override public void resize(int width, int height) {}
                @Override public void render() {}
                @Override public void pause() {}
                @Override public void resume() {}
                @Override public void dispose() {}
            }, config);
            initialized = true;
        }
    }
}
