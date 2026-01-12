package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.team6ENG.EscapeUni.InstructionsScreen;
import io.github.team6ENG.EscapeUni.WinScreen;
import io.github.team6ENG.EscapeUni.Main;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WinScreenTest extends AbstractHeadlessGdxTest {
    WinScreen winScreen;
    Main game;

    /**
     * Creating a test WinScreen object.
     */
    @Test
    public void testWinScreen() {
        game = mock(Main.class);
        game.menuFont = new BitmapFont();

        game.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        game.viewport.setCamera(mockCamera);

        game.batch = mock(SpriteBatch.class);
        when(game.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(game.batch.getTransformMatrix()).thenReturn(new Matrix4());
        when(game.batch.getColor()).thenReturn(Color.BLACK);

        game.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        winScreen = new WinScreen(game);
    }

    /**
     * Testing the show function.
     */
    @Test
    public void testShow() {
        testWinScreen();

        winScreen.show();
    }

    /**
     * Testing the resize function.
     */
    @Test
    public void testResize() {
        testWinScreen();

        winScreen.resize(200, 200);

        winScreen.show();
        winScreen.resize(200, 200);
    }

    /**
     * Testing the dispose function.
     */
    @Test
    public void testDispose() {
        testWinScreen();

        winScreen.show();
        winScreen.dispose();
        winScreen.dispose();
    }

    /**
     * Testing the hide function.
     */
    @Test
    public void testHide() {
        testWinScreen();

        winScreen.hide();

        winScreen.show();
        winScreen.hide();
    }

    /**
     * Testing the render function.
     */
    @Test
    public void testRender() {
        testWinScreen();
        winScreen.show();
        winScreen.render(1/60f);

        winScreen.dispose();
        game.menuFont = null;
        winScreen.render(1/60f);
    }

    /**
     * Testing the arbitrary functions.
     */
    @Test
    public void testArbitraryFunctions() {
        testWinScreen();
        winScreen.pause();
        winScreen.resume();
    }
}
