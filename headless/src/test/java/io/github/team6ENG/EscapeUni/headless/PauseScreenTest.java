package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PauseScreenTest extends AbstractHeadlessGdxTest {
    PauseScreen pauseScreen;

    /**
     * Creating a test pauseScreen object.
     */
    @Test
    public void testPauseScreen() {
        Main game = mock(Main.class);
        game.menuFont = new BitmapFont();

        game.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        game.viewport.setCamera(mockCamera);

        game.batch = mock(SpriteBatch.class);
        when(game.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(game.batch.getTransformMatrix()).thenReturn(new Matrix4());
        when(game.batch.getColor()).thenReturn(Color.BLACK);

        game.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        Screen playScreen = new MainMenuScreen(game);

        pauseScreen = new PauseScreen(game, playScreen, mock(AudioManager.class));
    }

    /**
     * Testing the render function.
     */
    @Test
    public void testPauseScreenRender() {
        testPauseScreen();

        pauseScreen.render(1/60f);
    }

    /**
     * Testing the resize function.
     */
    @Test
    public void testResize() {
        testPauseScreen();

        pauseScreen.resize(200, 200);
    }

    /**
     * Testing the dispose function.
     */
    @Test
    public void testDispose() {
        testPauseScreen();
        pauseScreen.dispose();
    }

    /**
     * Testing arbitrary functions.
     */
    @Test
    public void testArbitraryFunctions() {
        testPauseScreen();

        pauseScreen.show();
        pauseScreen.hide();
        pauseScreen.pause();
        pauseScreen.resume();
    }
}
