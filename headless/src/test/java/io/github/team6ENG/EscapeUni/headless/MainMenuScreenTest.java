package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class MainMenuScreenTest extends AbstractHeadlessGdxTest {
    MainMenuScreen mainMenuScreen;
    Main game;

    /**
     * Creating a test MainMenuScreen object.
     */
    @Test
    public void testMainMenuScreen() {
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

        mainMenuScreen = new MainMenuScreen(game);
    }

    /**
     * Testing the show function.
     */
    @Test
    public void testMainMenuShow() {
        testMainMenuScreen();

        mainMenuScreen.show();
    }

    /**
     * Testing the resize function.
     */
    @Test
    public void testResize() {
        // this sets the stage.
        testMainMenuShow();

        mainMenuScreen.resize(200, 200);

        // make stage = null.
        mainMenuScreen.dispose();
        mainMenuScreen.dispose();
        mainMenuScreen.resize(200, 200);
    }

    /**
     * testing the hide function.
     */
    @Test
    public void testHide() {
        testMainMenuScreen();
        mainMenuScreen.hide();

        mainMenuScreen.show();
        mainMenuScreen.hide();
    }

    /**
     * Testing the render function.
     */
    @Test
    public void testRender() {
        testMainMenuScreen();

        mainMenuScreen.show();
        mainMenuScreen.render(1/60f);

        mainMenuScreen.dispose();
        game.menuFont = null;
        Input mockInput = mock(Input.class);
        Gdx.input = mockInput;
        when(mockInput.isKeyJustPressed(Input.Keys.SPACE)).thenReturn(true);
        mainMenuScreen.render(1/60f);
    }

    /**
     * Testing arbitrary functions.
     */
    @Test
    public void testArbitraryFunctions() {
        testMainMenuScreen();

        mainMenuScreen.pause();
        mainMenuScreen.resume();
    }
}
