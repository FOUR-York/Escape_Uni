package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.team6ENG.EscapeUni.Main;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MainTest extends AbstractHeadlessGdxTest {
    @Test
    public void testMainAssets() {
        assertTrue(Gdx.files.internal(Main.menuFontAsset).exists(),
            "The asset for the menu font should be available");

        assertTrue(Gdx.files.internal(Main.buttonSkinAsset).exists(),
            "The asset for the button skin should be available");
    }

    @Test
    public void testAllNullDispose () {
        Main main = new Main();

        main.buttonSkin = null;
        main.batch = null;
        main.menuFont = null;

        main.dispose();
    }

    @Test
    public void testAllNotNullDispose () {
        Main main = new Main();

        main.buttonSkin = Mockito.mock(Skin.class);
        main.menuFont = Mockito.mock(BitmapFont.class);
        main.batch = Mockito.mock(SpriteBatch.class);

        assertNotNull(main.buttonSkin,
            "The button skin should not be null for this test.");
        assertNotNull(main.menuFont,
            "The menu font should not be null for this test.");
        assertNotNull(main.batch,
            "The batch should not be null for this test.");

        main.dispose();
    }

    @Test
    public void testRender() {
        Main main = new Main();

        main.viewport = mock(FitViewport.class);
        main.batch = mock(SpriteBatch.class);
        Camera camera = new OrthographicCamera();

        when(main.viewport.getCamera()).thenReturn(camera);
        main.render();
    }

    @Test
    public void testDispose() {
        Main main = new Main();
        main.dispose();
    }

    @Test
    public void testResize() {
        Main main = new Main();

        main.viewport = mock(FitViewport.class);

        main.resize(800, 450);
    }
}
