package io.github.team6ENG.EscapeUni.headless;

import io.github.team6ENG.EscapeUni.AudioManager;
import io.github.team6ENG.EscapeUni.Collectable;
import io.github.team6ENG.EscapeUni.Main;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

public class CollectableTest extends AbstractHeadlessGdxTest {
    Collectable collectable;

    @Test
    public void createCollectableTest() {
        Main main = mock(Main.class);
        AudioManager audioManager = mock(AudioManager.class);

        collectable = new Collectable(main, "items/gooseFood.png",   0, 0, 0.03f, true, "GameScreen", audioManager);
    }

    @Test
    public void checkInRangeForCollectableTest() {
        createCollectableTest();

        collectable.checkInRange(0, 0);

        double playerY = -44.93;
        collectable.checkInRange(0, (float) playerY);

        collectable.Collect();
        collectable.checkInRange(0, 0);
    }

    @Test
    public void checkPlaySound() {
        createCollectableTest();

        collectable.playSound();
    }

}
