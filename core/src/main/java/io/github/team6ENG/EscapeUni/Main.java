package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Main class initialises the game
 */
public class Main extends Game {

    public SpriteBatch batch;
    public SpriteBatch uiBatch;
    public BitmapFont menuFont;
    public BitmapFont gameFont;
    public FitViewport viewport;
    public Skin buttonSkin;
    public static String activeSpritePath;
    public static String activeUniIDPath;

    public static final String menuFontAsset = "fonts/menuScreenFont.fnt";
    public static final String buttonSkinAsset = "skins/uiskin.json";

    public static float gameTimer = 300;
    public static float score = 0;

    public float gameVolume = .5f;
    public float musicVolume = .5f;

    public static final int totalNegativeEvents = 3;
    public static final int totalPositiveEvents = 3;
    public static final int totalHiddenEvents = 2;

    public static int foundNegativeEvents = 0;
    public static int foundPositiveEvents = 0;
    public static int foundHiddenEvents = 0;

    public static boolean playerShotOnce = false;
    public static boolean playerFoundKeycardOnce = false;
    public static boolean playerGotPowerupOnce = false;
    public static boolean playerGotLightSwitchOnce = false;
    public static boolean playerGotScorePickupOnce = false;
    public static boolean playerInvertedOnce = false;
    public static boolean bob = false;
    public static boolean hiddenEnding = false;
    /**
     * Initialise global game variables
     */
    public void create() {
        batch = new SpriteBatch();
        uiBatch = new SpriteBatch();

        menuFont = new BitmapFont(Gdx.files.internal(menuFontAsset));
        viewport = new FitViewport(640, 480);

        //Scale font to our viewport by ratio of viewport height to screen height
        menuFont.setUseIntegerPositions(false);
        menuFont.getData().setScale(0.8f);
        menuFont.setColor(Color.valueOf("4287f5FF"));

        gameFont = new BitmapFont(Gdx.files.internal(menuFontAsset));
        gameFont.getData().setScale(0.4f);
        gameFont.setColor(Color.valueOf("4287f5FF"));

        buttonSkin = new Skin(Gdx.files.internal(buttonSkinAsset));

        this.setScreen(new MainMenuScreen(this));
    }

    /**
     * Reset game and return to the main menu
     */
    public void resetGame() {

        gameTimer = 300;
        score = 300;

        foundNegativeEvents = 0;
        foundPositiveEvents = 0;
        foundHiddenEvents = 0;

        playerShotOnce = false;
        playerFoundKeycardOnce = false;
        playerInvertedOnce = false;
        playerGotLightSwitchOnce = false;
        playerGotPowerupOnce = false;

        activeSpritePath = null;
        activeUniIDPath = null;

        // Return to main menu
        this.setScreen(new MainMenuScreen(this));

    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        super.resize(width, height);
    }

    public void setInputProcessor(com.badlogic.gdx.InputProcessor processor) {
        Gdx.input.setInputProcessor(processor);
    }


    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);    // black environment
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        uiBatch.setProjectionMatrix(viewport.getCamera().combined);
        super.render();
    }

    public void dispose() {
       // dispose resources in reverse creation order
        if (buttonSkin != null) {
            buttonSkin.dispose();
        }
        if (menuFont != null) {
            menuFont.dispose();
        }
        if (batch != null) {
            batch.dispose();
        }
    }
}
