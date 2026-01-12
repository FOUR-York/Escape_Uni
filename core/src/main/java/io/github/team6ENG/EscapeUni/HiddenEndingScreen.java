package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static io.github.team6ENG.EscapeUni.NewGameScreen.audioManager;

/**
 * screen displayed when player wins with a high score
 */
public class HiddenEndingScreen implements Screen {

    private final Main game;

    // stage and resources created in show() and disposed in dispose()
    private Stage stage;
    private Skin skin;
    private final GlyphLayout layout = new GlyphLayout();

    private TextButton exitButton;
    private TextButton mainMenuButton;

    private com.badlogic.gdx.InputProcessor previousInputProcessor; // used to restore on hide()

    private static final String TITLE_TEXT = "Congratulations, you escaped university!\nAnd with an outstanding score!";

    private float frameTimer;
    Texture animSheet;
    Animation<TextureRegion> anim;

    private ShapeRenderer shapeRenderer;

    /**
     * initialise hidden win screen
     * @param game current Instance of Main
     */
    public HiddenEndingScreen(final Main game) {
        this.game = game;
        // DO NOT initialize stage/input here — do it in show()

        animSheet = new Texture(Gdx.files.internal("sprites/bobhallsheet.png"));

        TextureRegion[][] tmp = TextureRegion.split(animSheet,
            animSheet.getWidth() / 39,
            animSheet.getHeight());

        TextureRegion[] animFrames = new TextureRegion[39];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 39; j++) {
                animFrames[index++] = tmp[i][j];
            }
        }

        anim = new Animation<TextureRegion>(0.025f, animFrames);

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        // create stage with a fixed virtual size (you used 800x450)
        stage = new Stage(game.viewport);

        // remember previous input processor so we can restore it later
        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(stage);

        // Prefer shared skin from game (do NOT dispose it later)
        skin = game.buttonSkin;

        // build UI
        setupUI();
    }

    /**
     * Create buttons and positions
     */
    private void setupUI() {
        exitButton = createButton("Exit");
        mainMenuButton = createButton("Continue");

        stage.addActor(exitButton);
        stage.addActor(mainMenuButton);

        positionButtons();
        addListeners();
    }

    /**
     * Helper function that creates a new button
     * @param text button text
     * @return
     */
    private TextButton createButton(String text) {
        // If skin is null, fallback to a simple TextButton may fail; ensure game.buttonSkin exists in assets
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(1.6f);
        button.pad(25f);
        button.setSize(320, 100);
        button.setColor(new Color(0.0f, 0.95f, 0.95f, 1f));
        return button;
    }

    /**
     * Helper function to set the buttons positions
     */
    private void positionButtons() {
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();

        mainMenuButton.setPosition((w - mainMenuButton.getWidth()) / 2f, h / 2f -60);
        exitButton.setPosition((w - exitButton.getWidth()) / 2f, h / 2f -170);
    }

    /**
     * Link buttons to their respective screens
     */
    private void addListeners() {
        Color normalColor = new Color(0.0f, 0.95f, 0.95f, 1f);
        Color clickColor = new Color(0.4f, 1f, 1f, 1f);


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitButton.setColor(clickColor);
                Gdx.app.postRunnable(Gdx.app::exit);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.setColor(clickColor);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitButton.setColor(normalColor);
            }
        });
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainMenuButton.setColor(clickColor);
                Gdx.app.postRunnable(() -> game.setScreen(new LeaderboardScreen(game)));
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                mainMenuButton.setColor(clickColor);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                mainMenuButton.setColor(normalColor);
            }
        });
    }

    @Override
    public void render(float delta) {
        frameTimer += delta;

        ScreenUtils.clear(Color.BLACK);

        TextureRegion currentFrame = anim.getKeyFrame(frameTimer, true);

        // update stage
        if (stage != null) {
            stage.act(delta);
        }

        // Draw background + title using game's batch (aligned to stage camera)
        if (stage != null) {
            game.batch.setProjectionMatrix(stage.getCamera().combined);
        } else {
            game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        }
        float w = (stage != null) ? stage.getViewport().getWorldWidth() : game.viewport.getWorldWidth();
        float h = (stage != null) ? stage.getViewport().getWorldHeight() : game.viewport.getWorldHeight();

        game.batch.begin();
        game.batch.draw(currentFrame, 0, 0, w, h);
        game.batch.end();

        // reset shapeRenderer
        shapeRenderer.setProjectionMatrix(game.batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.rect(0, 305, w, 100);
        shapeRenderer.end();

        game.batch.begin();
        float brightness = 0.85f + 0.15f * (float) Math.sin(TimeUtils.millis() / 500f);
        if (game.menuFont != null) {
            game.menuFont.setColor(brightness, brightness, brightness, 1f);
            layout.setText(game.menuFont, TITLE_TEXT);
            game.menuFont.draw(game.batch, TITLE_TEXT, (w - layout.width) / 2f, h * 0.82f);
            game.menuFont.setColor(Color.WHITE);

            layout.setText(game.menuFont, "Score: "+ (int)game.score);
            game.menuFont.draw(game.batch, ("Score: "+ (int)game.score), (w - layout.width) / 2f, h * 0.7f);

        }

        game.batch.end();

        if (stage != null) stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (stage != null) stage.getViewport().update(width, height, true);
        positionButtons();
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

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
        }


        // DO NOT dispose game.menuFont or game.buttonSkin or game.batch here

        animSheet.dispose();
    }
}
