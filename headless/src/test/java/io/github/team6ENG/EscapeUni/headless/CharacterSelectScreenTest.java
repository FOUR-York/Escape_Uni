package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.team6ENG.EscapeUni.CharacterSelectScreen;
import io.github.team6ENG.EscapeUni.Main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;


public class CharacterSelectScreenTest extends AbstractHeadlessGdxTest {
    CharacterSelectScreen characterSelectScreen;

    @Test
    public void testCharacterSelectAssets() {
        assertTrue(Gdx.files.internal(CharacterSelectScreen.img1Asset).exists(),
            "The asset for the female sprite should be available");

        assertTrue(Gdx.files.internal(CharacterSelectScreen.img2Asset).exists(),
            "The asset for the male sprite should be available");
    }

    @Test
    public void createCharacterSelectScreenTest() {
        Main main = mock(Main.class);
        main.viewport = new FitViewport(800, 600);
        main.batch = mock(SpriteBatch.class);

        characterSelectScreen = new CharacterSelectScreen(main);
    }

    @Test
    public void testShow() {
        createCharacterSelectScreenTest();

        characterSelectScreen.show();
    }

    @Test
    public void testPause() {
        createCharacterSelectScreenTest();
        characterSelectScreen.pause();
    }

    @Test
    public void testResume() {
        createCharacterSelectScreenTest();
        characterSelectScreen.resume();
    }

    @Test
    public void testHide() {
        createCharacterSelectScreenTest();
        characterSelectScreen.hide();
    }

    @Test
    public void testDispose() {
        createCharacterSelectScreenTest();
        characterSelectScreen.dispose();

        // case when stage = null.
        characterSelectScreen.dispose();
    }
}
