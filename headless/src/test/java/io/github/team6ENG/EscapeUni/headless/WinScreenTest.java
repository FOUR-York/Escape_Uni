package io.github.team6ENG.EscapeUni.headless;

import io.github.team6ENG.EscapeUni.WinScreen;
import io.github.team6ENG.EscapeUni.Main;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

public class WinScreenTest extends AbstractHeadlessGdxTest {
    @Test
    public void testWinScreen() {
        Main game = mock(Main.class);
        WinScreen winScreen = new WinScreen(game);
    }

    @Test
    public void testWinScreenWithInput() {
        Main game = mock(Main.class);
    }

}
