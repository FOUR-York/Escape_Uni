package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
        main.viewport = new FitViewport(800, 600);
        main.batch = mock(SpriteBatch.class);

        Gdx.input = mock(Input.class);

        gameOverScreen = new GameOverScreen(main, "Sorry you missed the bus, better luck next time");
    }

    @Test
    public void testArbitraryFunctions() {
        createGameOverScreenTest();
    }

    /*@Test
    public void testShow() {
        createGameOverScreenTest();

        Stage stage = mock(Stage.class);

        when(Gdx.input.getInputProcessor()).thenReturn(stage);

        gameOverScreen.show();
    }*/
}
