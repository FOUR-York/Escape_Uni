package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * first screen to display when game loads
 */
public class MainMenuScreen extends GameScreenBase {

    private final Main game;

    // stage and resources created in show() and disposed in dispose()
    private Stage stage;
    private Skin skin;

    private TextButton playButton;
    private TextButton exitButton;

    private com.badlogic.gdx.InputProcessor previousInputProcessor; // used to restore on hide()

    private static final String TITLE_TEXT = "Escape University Of York";

    /**
     * Initialise menu screen
     * @param game current instance of Main
     */
    public MainMenuScreen(final Main game) {
        super(game, TITLE_TEXT);

        this.game = game;
        // DO NOT initialize stage/input here — do it in show()
    }

    @Override
    public void show() {
        // create stage with a fixed virtual size (you used 800x450)
        stage = new Stage(game.viewport, game.batch);
        super.setStage(stage);

        // remember previous input processor so we can restore it later
        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(stage);

        // Prefer shared skin from game (do NOT dispose it later)
        skin = game.buttonSkin;

        // build UI
        setupUI();
    }
    /**
     *Add required UI elements to stage
     */
    private void setupUI() {
        playButton = createButton("Play", skin);
        exitButton = createButton("Exit", skin);

        stage.addActor(playButton);
        stage.addActor(exitButton);
        super.setStage(stage);

        positionButtons();
        addListeners();
    }

    /**
     * Place buttons on screen
     */
    private void positionButtons() {
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();
        super.setStage(stage);

        playButton.setPosition((w - playButton.getWidth()) / 2f, h / 2f );
        exitButton.setPosition((w - exitButton.getWidth()) / 2f, h / 2f - 120);
    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) return;
        stage.getViewport().update(width, height, true);
        super.setStage(stage);

        positionButtons();
    }

    @Override
    public void hide() {
        if (Gdx.input.getInputProcessor() == stage) {
            Gdx.input.setInputProcessor(null);
        }

        if (previousInputProcessor != null) {
            Gdx.input.setInputProcessor(previousInputProcessor);
            previousInputProcessor = null;
        }
    }

    @Override
    public void dispose() {
        // Dispose only things we created here
        if (stage != null) {
            stage.dispose();
            stage = null;
            setStage(null);
        }


        // DO NOT dispose game.menuFont or game.buttonSkin or game.batch here
    }
}
