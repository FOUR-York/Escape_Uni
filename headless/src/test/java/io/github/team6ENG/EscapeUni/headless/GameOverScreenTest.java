package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.team6ENG.EscapeUni.GameOverScreen;
import io.github.team6ENG.EscapeUni.Main;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameOverScreenTest extends AbstractHeadlessGdxTest {
    GameOverScreen gameOverScreen;

    @Test
    public void createGameOverScreenTest() {
        Main main = mock(Main.class);
        main.menuFont = new BitmapFont();

        main.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        main.viewport.setCamera(mockCamera);

        main.batch = mock(SpriteBatch.class);
        when(main.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(main.batch.getTransformMatrix()).thenReturn(new Matrix4());

        main.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        gameOverScreen = new GameOverScreen(main, "Sorry you missed the bus, better luck next time");
    }

    @Test
    public void testArbitraryFunctions() {
        createGameOverScreenTest();

        gameOverScreen.pause();
        gameOverScreen.resume();
    }

    @Test
    public void testShow() {
        createGameOverScreenTest();

        gameOverScreen.show();
    }

    @Test
    public void testResize() {
        createGameOverScreenTest();
        gameOverScreen.show();
        gameOverScreen.resize(200, 200);

        gameOverScreen.dispose();
        gameOverScreen.dispose();
        gameOverScreen.resize(200, 200);
    }

    @Test
    public void testHide() {
        createGameOverScreenTest();
        gameOverScreen.hide();

        gameOverScreen.show();
        gameOverScreen.hide();
    }

    @Test
    public void testRender() {
        createGameOverScreenTest();

        gameOverScreen.render(1/60f);
    }
}
