package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.team6ENG.EscapeUni.*;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InstructionsScreenTest extends AbstractHeadlessGdxTest {
    InstructionsScreen instructionsScreen;
    @Test
    public void testInstructionsScreen() {
        Main game = mock(Main.class);
        game.menuFont = null;

        game.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        game.viewport.setCamera(mockCamera);

        game.batch = mock(SpriteBatch.class);
        when(game.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(game.batch.getTransformMatrix()).thenReturn(new Matrix4());
        when(game.batch.getColor()).thenReturn(Color.BLACK);

        game.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        Gdx.input = mock(Input.class);

        instructionsScreen = new InstructionsScreen(game);
    }

    @Test
    public void testShow() {
        testInstructionsScreen();

        instructionsScreen.show();
    }

    @Test
    public void testResize() {
        testInstructionsScreen();

        instructionsScreen.show();
        instructionsScreen.resize(200, 200);

        instructionsScreen.dispose();
        instructionsScreen.dispose();
        instructionsScreen.resize(200, 200);
    }

    @Test
    public void testHide() {
        testInstructionsScreen();
        instructionsScreen.hide();

        instructionsScreen.show();
        instructionsScreen.hide();
    }

    @Test
    public void testRender() {
        testInstructionsScreen();

        instructionsScreen.show();
        when(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)).thenReturn(true);

        instructionsScreen.render(1/60f);
    }

}
