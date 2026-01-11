package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LeaderboardScreenTest extends AbstractHeadlessGdxTest {
    LeaderboardScreen testLeaderboardScreen;
    Main game;

    /**
     * Creating a test LeaderboardScreen object.
     */
    @Test
    public void createLeaderBoardScreen() {
        game = new Main();
        game.menuFont = new BitmapFont();

        game.viewport = new FitViewport(800, 600);
        Camera mockCamera = new OrthographicCamera(400,225);
        game.viewport.setCamera(mockCamera);

        game.batch = mock(SpriteBatch.class);
        when(game.batch.getProjectionMatrix()).thenReturn(new Matrix4());
        when(game.batch.getTransformMatrix()).thenReturn(new Matrix4());
        when(game.batch.getColor()).thenReturn(Color.BLACK);

        game.buttonSkin = new Skin(Gdx.files.internal(Main.buttonSkinAsset));

        testLeaderboardScreen = new LeaderboardScreen(game);
        testLeaderboardScreen.show();
    }

    /**
     * Test the render function.
     */
    @Test
    public void testRender() {
        createLeaderBoardScreen();
        Main.playerShotOnce = true;
        testLeaderboardScreen.render(1/60f);

        testLeaderboardScreen.dispose();
        Main.foundHiddenEvents = Main.totalHiddenEvents;
        Main.foundNegativeEvents = Main.totalNegativeEvents;
        Main.foundPositiveEvents = Main.totalPositiveEvents;
        Main.gameTimer = 0;
        Main.playerShotOnce = false;
        testLeaderboardScreen.render(1/60f);

        game.menuFont = null;
        testLeaderboardScreen.render(1/60f);
    }

    /**
     * Test obsolete functions.
     */
    @Test
    public void testArbitraryFunction() {
        createLeaderBoardScreen();
        testLeaderboardScreen.pause();
        testLeaderboardScreen.resume();
    }

    /**
     * Test resize function.
     */
    @Test
    public void testResize() {
        createLeaderBoardScreen();
        testLeaderboardScreen.resize(10, 10);

        testLeaderboardScreen.dispose();
        testLeaderboardScreen.resize(10, 10);
    }

    /**
     * Test hide function.
     */
    @Test
    public void testHide() {
        createLeaderBoardScreen();
        testLeaderboardScreen.hide();

        testLeaderboardScreen.show();
        testLeaderboardScreen.hide();
    }

    /**
     * Test dispose function.
     */
    @Test
    public void testDispose() {
        createLeaderBoardScreen();
        testLeaderboardScreen.dispose();
        testLeaderboardScreen.dispose();
    }
}
