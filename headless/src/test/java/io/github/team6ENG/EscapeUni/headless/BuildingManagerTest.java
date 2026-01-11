package io.github.team6ENG.EscapeUni.headless;

import java.io.*;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.team6ENG.EscapeUni.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BuildingManagerTest extends AbstractHeadlessGdxTest {
    BuildingManager buildingManager;
    Main main;
    GameScreen gameScreen;
    Sprite sprite;
    Player player;
    AudioManager audioManager;

    public void createBuildingManagerTest(float... Bounds) {
        main = mock(Main.class);
        main.viewport = mock(FitViewport.class);
        when(main.viewport.getWorldWidth()).thenReturn(1600F);
        when(main.viewport.getWorldHeight()).thenReturn(1600F);
        main.activeSpritePath = "sprites/femaleSprite.png";

        gameScreen = mock(GameScreen.class);
        gameScreen.lighting = mock(Lighting.class);
        gameScreen.audioManager = mock(AudioManager.class);
        gameScreen.items = new HashMap<>();
        gameScreen.items.put("keyCard", new Collectable(main, "items/idFemale.png",   300, 200, 0.05f, false, "RonCookeScreen", gameScreen.audioManager));

        sprite = new  Sprite();
        player = mock(Player.class);
        audioManager = mock(AudioManager.class);

        if (Bounds.length != 0) {
            sprite.setBounds(Bounds[0], Bounds[1], Bounds[2], Bounds[3]);
        }

        player.sprite = sprite;
        buildingManager = new BuildingManager(main, gameScreen, player, audioManager);
    }

    @Test
    public void testIsInLangwith() {
        createBuildingManagerTest();
        assertFalse(buildingManager.isInLangwith());
    }

    @Test
    public void testisInRonCooke() {
        createBuildingManagerTest();
        assertFalse(buildingManager.isInRonCooke());
    }

    @Test
    public void updateTestRonCooke() {
        createBuildingManagerTest(350, 455, 50, 50);
        assertFalse(buildingManager.isInRonCooke());

        buildingManager.update(1/60f);

        Gdx.input =  mock(Input.class);
        when(Gdx.input.isKeyJustPressed(Input.Keys.G)).thenReturn(true);
        buildingManager.update(1/60f);

        assertTrue(buildingManager.isInRonCooke());


        when(Gdx.input.isKeyJustPressed(Input.Keys.G)).thenReturn(false);
        buildingManager.update(1/60f);
        assertTrue(buildingManager.isInRonCooke());

        when(Gdx.input.isKeyJustPressed(Input.Keys.G)).thenReturn(true);
        buildingManager.update(1/60f);
        assertFalse(buildingManager.isInRonCooke());
    }

    @Test
    public void updateTestLangwith() {
        createBuildingManagerTest(1078, 1215, 50, 50);
        assertFalse(buildingManager.isInLangwith());

        buildingManager.update(1/60f);

        Gdx.input =  mock(Input.class);
        when(Gdx.input.isKeyJustPressed(Input.Keys.G)).thenReturn(true);
        buildingManager.update(1/60f);

        assertFalse(buildingManager.isInLangwith());

        gameScreen.items.get("keyCard").Collect();
        buildingManager.update(1/60f);

        assertTrue(buildingManager.isInLangwith());

        // Unable to leave Langwith as key is not pressed.
        when(Gdx.input.isKeyJustPressed(Input.Keys.G)).thenReturn(false);
        buildingManager.update(1/60f);
        assertTrue(buildingManager.isInLangwith());

        when(Gdx.input.isKeyJustPressed(Input.Keys.G)).thenReturn(true);
        buildingManager.update(1/60f);
        assertFalse(buildingManager.isInLangwith());

    }

    @Test
    public void testFalseBuildingTrigger() {
        createBuildingManagerTest();
        buildingManager.update(1/60f);
    }

    @Test
    public void testRender() {
        createBuildingManagerTest();

        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(1/60f);

        SpriteBatch mockSpriteBatch = mock(SpriteBatch.class);
        BitmapFont mockBitmapFont = new BitmapFont();
        BitmapFont mockSmallBitmapFont = new BitmapFont();

        buildingManager.render(mockSpriteBatch, mockBitmapFont, 1600, 1600);
        // this makes showEnterPrompt true.
        // it also makes the lockedOutTime = 5.
        updateTestLangwith();


        // this is out way of ensuring renderWorldPrompts works.
        buildingManager.render(mockSpriteBatch, mockBitmapFont, 1600, 1600);

        buildingManager.renderUI(mockSpriteBatch, mockSmallBitmapFont, mockBitmapFont, 1600, 1600);
    }

    @Test
    public void testArbitraryFunctions() {
        createBuildingManagerTest();

        OrthographicCamera mockCamera = new OrthographicCamera(400,225);
        buildingManager.renderBuildingMap(mockCamera);
        buildingManager.dispose();
    }
}
