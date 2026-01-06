package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;

import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractHeadlessGdxTest {
    // This runs before each test.
    @BeforeEach
    public void setup() {
        new HeadlessApplication(new ApplicationAdapter() {});
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        Gdx.graphics = mock(Graphics.class);
    }
}
