package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Instructions Screen, displays before game starts
 */
public class InstructionsScreen extends GameScreenBase {

    private final Main game;

    // stage and resources created in show() and disposed in dispose()
    private Stage stage;
    private Skin skin;

    private TextButton playButton;

    private com.badlogic.gdx.InputProcessor previousInputProcessor; // used to restore on hide()

    private static final String TITLE_TEXT = "It's time to meet your friends in town\nand the bus leaves in 5 minutes, better\ngrab your phone from your room in\nLangwith college before you board.";

    /**
     * Initialise the instructions screen
     * @param game current instance of game
     */
    public InstructionsScreen(final Main game) {
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

        stage.addActor(playButton);
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

        playButton.setPosition((w - playButton.getWidth()) / 2f, h / 2f -100);
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
