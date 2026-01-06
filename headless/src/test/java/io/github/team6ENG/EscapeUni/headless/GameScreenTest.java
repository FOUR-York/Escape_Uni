package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameScreenTest extends AbstractHeadlessGdxTest {
    GameScreen gameScreen;

    @Test
    public void testGameScreenAssets() {
        assertTrue(Gdx.files.internal(GameScreen.mapTexAsset).exists(),
            "The asset for the map image should be available");

        assertTrue(Gdx.files.internal(GameScreen.mapTmxAsset).exists(),
            "The asset for the map tmx should be available");
    }
}
