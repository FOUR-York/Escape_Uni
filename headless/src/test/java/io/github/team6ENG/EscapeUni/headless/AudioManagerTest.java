package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import io.github.team6ENG.EscapeUni.AudioManager;
import io.github.team6ENG.EscapeUni.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class AudioManagerTest extends AbstractHeadlessGdxTest {
    AudioManager audioManager;

    @Test
    public void testAudioManagerAssets() {
        assertTrue(Gdx.files.internal(AudioManager.honkAsset).exists(),
            "The asset for honking should be available");

        assertTrue(Gdx.files.internal(AudioManager.torchClickAsset).exists(),
            "The asset for torch clicking should be available");

        assertTrue(Gdx.files.internal(AudioManager.footStepsAsset).exists(),
            "The asset for foot steps should be available");

        assertTrue(Gdx.files.internal(AudioManager.noAccessAsset).exists(),
            "The asset for no access should be available");

        assertTrue(Gdx.files.internal(AudioManager.collectAsset).exists(),
            "The asset for collecting should be available");

        assertTrue(Gdx.files.internal(AudioManager.musicAsset).exists(),
            "The asset for music should be available");
    }

    @Test
    public void setUpAudioManager() {
        Main main = mock(Main.class);

        audioManager = new AudioManager(main);
    }

    @Test
    public void playAllAudios() {
        setUpAudioManager();

        audioManager.playHonk();
        audioManager.playTorch();
        audioManager.playNoAccess();
        audioManager.playCollect();

        audioManager.loopFootsteps();
        audioManager.stopFootsteps();

        audioManager.pauseMusic();
        audioManager.stopMusic();

        audioManager.dispose();
    }
}
