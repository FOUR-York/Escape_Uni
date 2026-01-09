package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Control and play game sounds
 */
public class AudioManager {

    private final Main game;
    private final Sound torchClick;
    private final Sound honk;
    private final Sound footSteps;
    private final Music music;
    private final Sound noAccess;
    private final Sound collect;

    public static final String honkAsset = "soundEffects/honk.mp3";
    public static final String torchClickAsset = "soundEffects/click.mp3";
    public static final String footStepsAsset = "soundEffects/footSteps.mp3";
    public static final String noAccessAsset = "soundEffects/wrong.mp3";
    public static final String collectAsset = "soundEffects/tap.mp3";
    public static final String musicAsset = "soundEffects/music.mp3";

    /**
     * Initialised audio manager
     * @param game current instance of Main
     */
    public AudioManager(final Main game) {
        this.game = game;

        honk = Gdx.audio.newSound(Gdx.files.internal(honkAsset));
        torchClick = Gdx.audio.newSound(Gdx.files.internal(torchClickAsset));
        footSteps = Gdx.audio.newSound(Gdx.files.internal(footStepsAsset));
        noAccess = Gdx.audio.newSound(Gdx.files.internal(noAccessAsset));
        collect = Gdx.audio.newSound(Gdx.files.internal(collectAsset));
        music = Gdx.audio.newMusic(Gdx.files.internal(musicAsset));
        playMusic();
    }

    public void playHonk(){
        honk.play(game.gameVolume);
    }

    public void playTorch(){
        torchClick.play(game.gameVolume);
    }

    public void playNoAccess(){
        noAccess.play(game.gameVolume);
    }

    public void playCollect() {
        collect.play(game.gameVolume);
    }

    public void loopFootsteps(){
        footSteps.loop(.2f *game.gameVolume);
    }

    public void stopFootsteps(){
        footSteps.stop();
    }

    public void playMusic() {
        setMusicVolume();
        music.play();
        music.setLooping(true);
    }

    public void setMusicVolume(){
        music.setVolume(0.1f * game.musicVolume);
    }

    public void stopMusic(){
        music.stop();
    }

    public void pauseMusic(){
        music.pause();
    }

    public void dispose() {
        // torchClick and honk can never be null.
        torchClick.dispose();
        honk.dispose();
    }
}
