package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
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

    @Test
    public void testWinScreen() {
        Main game = mock(Main.class);
        game.menuFont = new BitmapFont();

        game.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        game.viewport.setCamera(mockCamera);

        game.batch = mock(SpriteBatch.class);
        when(game.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(game.batch.getTransformMatrix()).thenReturn(new Matrix4());

        game.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        winScreen = new WinScreen(game);
    }

    @Test
    public void testShow() {
        testWinScreen();

        winScreen.show();
    }

    @Test
    public void testResize() {
        testWinScreen();

        winScreen.resize(200, 200);

        winScreen.show();
        winScreen.resize(200, 200);
    }

    @Test
    public void testDispose() {
        testWinScreen();

        winScreen.show();
        winScreen.dispose();
        winScreen.dispose();
    }

    @Test
    public void testHide() {
        testWinScreen();

        winScreen.hide();

        winScreen.show();
        winScreen.hide();
    }

    @Test
    public void restRender() {
        testWinScreen();
        winScreen.render(1/60f);
    }
}
