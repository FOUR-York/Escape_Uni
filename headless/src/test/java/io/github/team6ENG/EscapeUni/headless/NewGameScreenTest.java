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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class NewGameScreenTest extends AbstractHeadlessGdxTest {
    Main main;

    /**
     * Creating a NewGameScreen object.
     */
    @Test
    public void NewGameScreenTest(){
        main = mock(Main.class);
        main.menuFont = new BitmapFont();
        main.gameFont = new BitmapFont();

        Main.activeSpritePath = "sprites/femaleSprite.png";

        main.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        main.viewport.setCamera(mockCamera);

        main.batch = mock(SpriteBatch.class);
        when(main.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(main.batch.getTransformMatrix()).thenReturn(new Matrix4());
        when(main.batch.getColor()).thenReturn(Color.BLACK);

        main.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));
        NewGameScreen.nextRoom = "classRoom.json";
        Main.activeSpritePath = "sprites/femaleSprite.png";

        NewGameScreen.start();
    }

    /**
     * Testing the error message function works.
     */
    @Test
    public void testErrorMsg() {
        NewGameScreenTest();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String msg = "Testing an error message.";

        NewGameScreen.errorMsg(msg);

        String expectedMsg = "[ERROR]: Testing an error message." + System.lineSeparator();
        assertEquals(expectedMsg, outputStream.toString(),
            "The error message does not work as expected.");
    }

    /**
     * Testing the info message function works.
     */
    @Test
    public void testInfoMsg() {
        NewGameScreenTest();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String msg = "Testing an info message.";

        NewGameScreen.infoMsg(msg);

        String expectedMsg = "[INFO]: Testing an info message." + System.lineSeparator();
        assertEquals(expectedMsg, outputStream.toString(),
            "The info message does not work as expected.");
    }
}
