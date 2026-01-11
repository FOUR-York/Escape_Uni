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

public class RonCookeScreenTest extends AbstractHeadlessGdxTest {
    RonCookeScreen testRonCookeScreen;
    Main main;
    GameScreen gameScreen;
    Sprite sprite;
    Player player;
    AudioManager audioManager;
    BuildingManager buildingManager;

    /**
     * Creating a test RonCooke object.
     */
    public void createRonCookeScreen() {
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

        gameScreen = mock(GameScreen.class);
        gameScreen.lighting = mock(Lighting.class);
        gameScreen.audioManager = mock(AudioManager.class);

        audioManager = mock(AudioManager.class);

        gameScreen.items = new HashMap<>();
        gameScreen.items.put("gooseFood", new Collectable(main, "items/gooseFood.png",   500, 1500, 0.03f, true, "GameScreen", audioManager));
        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   300, 200, 0.05f, false, "RonCookeScreen", audioManager));
        gameScreen.items.put("torch", new Collectable(main, "items/torch.png",   300, 220, 0.1f, false, "RonCookeScreen", audioManager));
        gameScreen.items.put("pizza", new Collectable(main, "items/pizza.png", 600, 100, 0.4f, true, "LangwithScreen", audioManager));
        gameScreen.items.put("phone", new Collectable(main, "items/phone.png", 100, 100, 0.05f, true, "LangwithScreen", audioManager));

        sprite = new Sprite();
        player = mock(Player.class);

        player.sprite = sprite;
        player.sprite.setBounds(0, 0, 48,64);
        buildingManager = new BuildingManager(main, gameScreen, player, audioManager);

        Gdx.input = mock(Input.class);
        testRonCookeScreen = new RonCookeScreen(main, buildingManager, gameScreen);
    }

    /**
     * Testing the render function when the player
     * has/hasn't got a keycard.
     */
    @Test
    public void testRenderForKeyCard() {
        createRonCookeScreen();
        Main.gameTimer = 300;

        // the player has no items.
        testRonCookeScreen.render(1/60f);

        // putting the keyCard out of range.
        gameScreen.items.remove("keyCard");
        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   300, 200, 0.05f, true, "RonCookeScreen", mock(AudioManager.class)));

        testRonCookeScreen.render(1/60f);

        gameScreen.items.remove("keyCard");
        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   776, 784, 0.05f, true, "RonCookeScreen", mock(AudioManager.class)));
        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(false);

        testRonCookeScreen.render(1/60f);

        when(Gdx.input.isKeyJustPressed(Input.Keys.E)).thenReturn(true);
        testRonCookeScreen.render(1/60f);
        testRonCookeScreen.render(1/60f);

        gameScreen.items.get("keyCard").Collect();
        testRonCookeScreen.render(1/60f);
    }

    /**
     * Testing render in different gameTimer situations.
     */
    @Test
    public void testRenderForTimer() {
        createRonCookeScreen();

        Main.gameTimer = -10;
        testRonCookeScreen.render(1/60f);

        Main.gameTimer = 300;
        testRonCookeScreen.render(1/60f);

        gameScreen.items.get("keyCard").Collect();
        testRonCookeScreen.render(1/60f);

        gameScreen.items.get("torch").Collect();
        testRonCookeScreen.render(1/60f);

        when(Gdx.input.isKeyJustPressed(Input.Keys.P)).thenReturn(true);
        main.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));
        testRonCookeScreen.render(1/60f);
    }

    /**
     * Testing the renderUi function.
     */
    @Test
    public void testRenderUI() {
        // so playerUI should be called when item.playerHas = false.
        createRonCookeScreen();

        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   776, 784, 0.05f, true, "RonCookeScreen", mock(AudioManager.class)));
        gameScreen.items.put("pizza", new Collectable(main, "items/idFemale.png",   776, 784, 0.05f, true, "RonCookeScreen", mock(AudioManager.class)));

        testRonCookeScreen.render(1/60f);
    }

    /**
     * Testing renderUi for SpeechTimer.
     */
    @Test
    public void testSpeechTimerRenderUI() {
        createRonCookeScreen();

        main.gameTimer = 300;
        testRonCookeScreen.render(4f);
        testRonCookeScreen.render(1/60f);
    }

    /**
     * Testing RenderUI to get full coverage.
     */
    @Test
    public void testRenderUIifStatement() {
        createRonCookeScreen();

        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   776, 784, 0.05f, true, "RonCookeScreen", mock(AudioManager.class)));
        gameScreen.items.get("keyCard").Collect();
        testRonCookeScreen.render(1/60f);

        gameScreen.items.remove("keyCard");
        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   0, 0, 0.05f, true, "RonCookeScreen", mock(AudioManager.class)));
        testRonCookeScreen.render(1/60f);
    }

    /**
     * Testing Render when the game is paused.
     */
    @Test
    public void testRenderWithPause() {
        createRonCookeScreen();

        testRonCookeScreen.pause();
        testRonCookeScreen.render(1/60f);
    }

    /**
     * Testing obsolete functions with no implemenetation.
     */
    @Test
    public void testArbitraryFunctions() {
        createRonCookeScreen();

        testRonCookeScreen.show();
        testRonCookeScreen.pause();
        testRonCookeScreen.resume();
        testRonCookeScreen.hide();
        testRonCookeScreen.dispose();
    }

    /**
     * Testing the Resize function.
     */
    @Test
    public void testResize() {
        createRonCookeScreen();

        testRonCookeScreen.resize(200, 200);
    }
}
