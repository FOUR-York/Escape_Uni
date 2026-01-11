package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CharacterSelectScreenTest extends AbstractHeadlessGdxTest {
    CharacterSelectScreen characterSelectScreen;

    /**
     * Checking that the assets needed by Character Select Screen are available.
     */
    @Test
    public void testCharacterSelectAssets() {
        assertTrue(Gdx.files.internal(CharacterSelectScreen.img1Asset).exists(),
            "The asset for the female sprite should be available");

        assertTrue(Gdx.files.internal(CharacterSelectScreen.img2Asset).exists(),
            "The asset for the male sprite should be available");
    }

    /**
     * Creating a test CharacterSelectScreen object.
     */
    @Test
    public void createCharacterSelectScreenTest() {
        Main main = mock(Main.class);
        main.menuFont = new BitmapFont();

        main.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        main.viewport.setCamera(mockCamera);

        main.batch = mock(SpriteBatch.class);
        when(main.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(main.batch.getTransformMatrix()).thenReturn(new Matrix4());

        characterSelectScreen = new CharacterSelectScreen(main);
    }

    /**
     * Testing the show() function.
     */
    @Test
    public void testShow() {
        createCharacterSelectScreenTest();

        characterSelectScreen.show();
    }

    /**
     * Testing the pause function.
     */
    @Test
    public void testPause() {
        createCharacterSelectScreenTest();
        characterSelectScreen.pause();
    }

    /**
     * Testing the resume function.
     */
    @Test
    public void testResume() {
        createCharacterSelectScreenTest();
        characterSelectScreen.resume();
    }

    /**
     * Testing the hide function.
     */
    @Test
    public void testHide() {
        createCharacterSelectScreenTest();
        characterSelectScreen.hide();
    }

    /**
     * Testing the dispose function.
     */
    @Test
    public void testDispose() {
        createCharacterSelectScreenTest();
        characterSelectScreen.dispose();

        // case when stage = null.
        characterSelectScreen.dispose();
    }

    /**
     * Testing the render function.
     */
    @Test
    public void testRender() {
        createCharacterSelectScreenTest();

        characterSelectScreen.render(1/60f);
    }

    /**
     * Testing the resize function.
     */
    @Test
    public void testResize() {
        createCharacterSelectScreenTest();

        characterSelectScreen.resize(200, 200);
    }

    @Test
    public void testCharacterButtons() {

    }
}
