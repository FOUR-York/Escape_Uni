package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameScreenBaseTest extends AbstractHeadlessGdxTest {
    GameScreenBase gameScreenBase;

    @Test
    public void testGameScreenBase() {
        Main game = mock(Main.class);
        gameScreenBase = new GameScreenBase(game);

        gameScreenBase.dispose();
        gameScreenBase.hide();
        gameScreenBase.resume();
        gameScreenBase.pause();
        gameScreenBase.resize(200, 200);
        gameScreenBase.show();
    }
}
