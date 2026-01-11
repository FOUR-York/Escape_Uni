package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LangwithScreenTest extends AbstractHeadlessGdxTest {
    LangwithScreen testLangwithScreen;
    Main main;
    GameScreen gameScreen;
    Sprite sprite;
    Player player;
    AudioManager audioManager;
    BuildingManager buildingManager;

    public void createLangwithScreen() {
        main = mock(Main.class);
        main.viewport = mock(FitViewport.class);
        when(main.viewport.getCamera()).thenReturn(new OrthographicCamera(400, 225));
        when(main.viewport.getWorldWidth()).thenReturn(1600F);
        when(main.viewport.getWorldHeight()).thenReturn(1600F);
        main.activeSpritePath = "sprites/femaleSprite.png";

        main.menuFont = new BitmapFont();
        main.menuFont.setColor(Color.BLACK);
        main.gameFont = new BitmapFont();
        main.gameFont.setColor(Color.WHITE);

        main.batch = mock(SpriteBatch.class);
        main.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        gameScreen = mock(GameScreen.class);
        gameScreen.lighting = mock(Lighting.class);
        gameScreen.audioManager = mock(AudioManager.class);

        audioManager = mock(AudioManager.class);

        gameScreen.items = new HashMap<>();
        gameScreen.items.put("gooseFood", new Collectable(main, "items/gooseFood.png",   500, 1500, 0.03f, true, "GameScreen", audioManager));
        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   300, 200, 0.05f, false, "LangwithScreen", audioManager));
        gameScreen.items.put("torch", new Collectable(main, "items/torch.png",   300, 220, 0.1f, false, "LangwithScreen", audioManager));
        gameScreen.items.put("pizza", new Collectable(main, "items/pizza.png", 600, 100, 0.4f, true, "LangwithScreen", audioManager));
        gameScreen.items.put("phone", new Collectable(main, "items/phone.png", 100, 100, 0.05f, true, "LangwithScreen", audioManager));

        sprite = new Sprite();
        player = mock(Player.class);

        player.sprite = sprite;
        player.sprite.setBounds(0, 0, 48,64);
        buildingManager = new BuildingManager(main, gameScreen, player, audioManager);

        Gdx.input = mock(Input.class);
        testLangwithScreen = new LangwithScreen(main, buildingManager, gameScreen);
    }

    @Test
    public void testRender() {
        createLangwithScreen();

        // dx = x - 8 - 60 + 32 -> x = 36
        // dy = y - 16 - 800 + 32 -> y = 784
        main.gameTimer = 300;

        gameScreen.items.remove("pizza");
        gameScreen.items.put("pizza", new Collectable(main, "items/pizza.png", 36, 784, 0.4f, true, "LangwithScreen", audioManager));
        when(Gdx.input.isKeyJustPressed(Input.Keys.P)).thenReturn(false);
        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(false);

        testLangwithScreen.render(1/60f);

        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(true);
        testLangwithScreen.render(1/60f);
        testLangwithScreen.render(1/60f);

        gameScreen.items.remove("phone");
        gameScreen.items.put("phone", new Collectable(main, "items/pizza.png", 36, 784, 0.4f, true, "LangwithScreen", audioManager));
        when(Gdx.input.isKeyJustPressed(Input.Keys.P)).thenReturn(false);
        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(false);

        testLangwithScreen.render(1/60f);

        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(true);
        testLangwithScreen.render(1/60f);
        testLangwithScreen.render(1/60f);
    }

    @Test
    public void testRenderPauseKey() {
        createLangwithScreen();
        main.gameTimer = 300;

        when(Gdx.input.isKeyJustPressed(Input.Keys.P)).thenReturn(true);
        testLangwithScreen.render(1/60f);
    }

    @Test
    public void testRenderWithPause() {
        createLangwithScreen();

        testLangwithScreen.pause();
        testLangwithScreen.render(1/60f);
    }

    @Test
    public void testInstructionIsEmpty() {
        createLangwithScreen();
        gameScreen.items.remove("phone");
        gameScreen.items.put("phone", new Collectable(main, "items/pizza.png", 36, 784, 0.4f, true, "LangwithScreen", audioManager));
        gameScreen.items.remove("pizza");

        testLangwithScreen.render(1/60f);
        gameScreen.items.put("pizza", new Collectable(main, "items/pizza.png", 36, 784, 0.4f, true, "LangwithScreen", audioManager));
        testLangwithScreen.render(1/60f);
    }

    @Test
    public void testArbitraryFunctions() {
        createLangwithScreen();

        testLangwithScreen.show();
        testLangwithScreen.pause();
        testLangwithScreen.resume();
        testLangwithScreen.hide();
        testLangwithScreen.dispose();
    }

    @Test
    public void testResize() {
        createLangwithScreen();

        testLangwithScreen.resize(200, 200);
    }

}

