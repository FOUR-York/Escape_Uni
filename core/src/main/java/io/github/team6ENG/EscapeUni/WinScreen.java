package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * screen displayed when player wins
 */
public class WinScreen extends GameScreenBase {
    private final Main game;

    // stage and resources created in show() and disposed in dispose()
    private Stage stage;
    private Skin skin;

    private TextButton exitButton;
    private TextButton mainMenuButton;

    private com.badlogic.gdx.InputProcessor previousInputProcessor; // used to restore on hide()

    private static final String TITLE_TEXT = "Congratulations, you escaped university:)";

    /**
     * initialise win screen
     * @param game current Instance of Main
     */
    public WinScreen(final Main game) {
        super(game,TITLE_TEXT);

        this.game = game;
        // DO NOT initialize stage/input here — do it in show()
    }

    @Override
    public void show() {
        // create stage with a fixed virtual size (you used 800x450)
        stage = new Stage(game.viewport, game.batch);

        // remember previous input processor so we can restore it later
        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(stage);

        // Prefer shared skin from game (do NOT dispose it later)
        skin = game.buttonSkin;
        super.setStage(stage);

        // build UI
        setupUI();
    }

    private void setupUI() {
        exitButton = createButton("Exit", skin);
        mainMenuButton = createButton("Main Menu", skin);

        stage.addActor(exitButton);
        stage.addActor(mainMenuButton);
        super.setStage(stage);

        positionButtons();
        addListeners();
    }

    private void positionButtons() {
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();
        super.setStage(stage);

        mainMenuButton.setPosition((w - mainMenuButton.getWidth()) / 2f, h / 2f -60);
        exitButton.setPosition((w - exitButton.getWidth()) / 2f, h / 2f -170);
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
            super.setStage(stage);
        }

        // DO NOT dispose game.menuFont or game.buttonSkin or game.batch here
    }
}
