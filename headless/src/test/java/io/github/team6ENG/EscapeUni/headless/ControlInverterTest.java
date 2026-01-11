package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.graphics.Texture;
import io.github.team6ENG.EscapeUni.ControlInverter;
import io.github.team6ENG.EscapeUni.Main;
import io.github.team6ENG.EscapeUni.NewGameScreen;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class ControlInverterTest extends AbstractHeadlessGdxTest {
    ControlInverter testControlInverter;

    /**
     * Creating a test object from ControlInverter, which checks the initialisation
     * function.
     */
    @Test
    public void testCreateControlInverter() {
        Texture mockTexture = mock(Texture.class);

        NewGameScreen.nextRoom = "classRoom.json";
        Main.activeSpritePath = "sprites/femaleSprite.png";
        NewGameScreen.start();

        testControlInverter = new ControlInverter(mockTexture, 0, 0);
    }

    /**
     * Testing the update() function.
     */
    @Test
    public void testUpdate() {
        testCreateControlInverter();

        testControlInverter.update(1/60f);
    }
}
