package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import static io.github.team6ENG.EscapeUni.NewGameScreen.audioManager;

public class LeaderboardScreen implements Screen {

    private final Main game;

    private Preferences leaderboardPrefs = Gdx.app.getPreferences("leaderboardPrefs");
    private int[] topScores = new int[5];
    private String[] topNames = new String[5];

    // stage and resources created in show() and disposed in dispose()
    private Stage stage;
    private Skin skin;
    private final GlyphLayout layout = new GlyphLayout();

    private TextButton exitButton;
    private TextButton mainMenuButton;

    private com.badlogic.gdx.InputProcessor previousInputProcessor; // used to restore on hide()

    /**
     * initialise win screen
     * @param game current Instance of Main
     */
    public LeaderboardScreen(final Main game) {
        this.game = game;
        // DO NOT initialize stage/input here — do it in show()
    }

    @Override
    public void show() {
        //Retrieve scores from save file
        topScores[0] = leaderboardPrefs.getInteger("score0", 0);
        topScores[1] = leaderboardPrefs.getInteger("score1", 0);
        topScores[2] = leaderboardPrefs.getInteger("score2", 0);
        topScores[3] = leaderboardPrefs.getInteger("score3", 0);
        topScores[4] = leaderboardPrefs.getInteger("score4", 0);

        topNames[0] = leaderboardPrefs.getString("name0", "None");
        topNames[1] = leaderboardPrefs.getString("name1", "None");
        topNames[2] = leaderboardPrefs.getString("name2", "None");
        topNames[3] = leaderboardPrefs.getString("name3", "None");
        topNames[4] = leaderboardPrefs.getString("name4", "None");

        // create stage with a fixed virtual size (you used 800x450)
        stage = new Stage(game.viewport, game.batch);

        // remember previous input processor so we can restore it later
        previousInputProcessor = Gdx.input.getInputProcessor();
        Gdx.input.setInputProcessor(stage);

        // Prefer shared skin from game (do NOT dispose it later)
        skin = game.buttonSkin;

        // build UI
        setupUI();
    }

    private void setupUI() {
        exitButton = createButton("Exit");
        mainMenuButton = createButton("Main Menu");

        stage.addActor(exitButton);
        stage.addActor(mainMenuButton);

        positionButtons();
        addListeners();
    }

    private TextButton createButton(String text) {
        // If skin is null, fallback to a simple TextButton may fail; ensure game.buttonSkin exists in assets
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(1.6f);
        button.pad(12f);
        button.setSize(160, 50);
        button.setColor(new Color(0.0f, 0.95f, 0.95f, 1f));
        return button;
    }

    private void positionButtons() {
        float w = stage.getViewport().getWorldWidth();
        float h = stage.getViewport().getWorldHeight();

        mainMenuButton.setPosition(10f, 65f);
        exitButton.setPosition(10f, 10f);
    }

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
                audioManager.stopMusic();
                dispose();
                game.resetGame();
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
        ScreenUtils.clear(Color.BLACK);

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

        game.batch.begin();
        float w = (stage != null) ? stage.getViewport().getWorldWidth() : game.viewport.getWorldWidth();
        float h = (stage != null) ? stage.getViewport().getWorldHeight() : game.viewport.getWorldHeight();


        float brightness = 0.85f + 0.15f * (float) Math.sin(TimeUtils.millis() / 500f);
        if (game.menuFont != null) {
            game.menuFont.setColor(brightness, brightness, brightness, 1f);
            game.menuFont.setColor(Color.WHITE);

            game.menuFont.draw(game.batch, "Achievements & Leaderboard", (w / 2f) - 200f, h - 15f);

            //Achievements
            if (Main.foundHiddenEvents >= Main.totalHiddenEvents && Main.foundNegativeEvents >= Main.totalNegativeEvents && Main.foundPositiveEvents >= Main.totalPositiveEvents) {
                game.menuFont.draw(game.batch, "Found all events!", 100f, h - 60f);
            }
            else {
                game.menuFont.draw(game.batch, "???", 100f, h - 60f);
            }

            if (Main.gameTimer >= 260) {
                game.menuFont.draw(game.batch, "Speedrun!", 400f, h - 60f);
            }
            else {
                game.menuFont.draw(game.batch, "???", 400f, h - 60f);
            }

            if (!Main.playerShotOnce) {
                game.menuFont.draw(game.batch, "No deaths!", 100f, h - 100f);
            }
            else {
                game.menuFont.draw(game.batch, "???", 100f, h - 100f);
            }

            if (Main.gameTimer <= 5) {
                game.menuFont.draw(game.batch, "Close call!", 400f, h - 100f);
            }
            else {
                game.menuFont.draw(game.batch, "???", 400f, h - 100f);
            }

            //Leaderboard
            game.menuFont.draw(game.batch, topNames[0], 200f, 300f);
            game.menuFont.draw(game.batch, ""+topScores[0], 400f, 300f);

            game.menuFont.draw(game.batch, topNames[1], 200f, 262f);
            game.menuFont.draw(game.batch, ""+topScores[1], 400f, 262f);

            game.menuFont.draw(game.batch, topNames[2], 200f, 225f);
            game.menuFont.draw(game.batch, ""+topScores[2], 400f, 225f);

            game.menuFont.draw(game.batch, topNames[3], 200f, 187f);
            game.menuFont.draw(game.batch, ""+topScores[3], 400f, 187f);

            game.menuFont.draw(game.batch, topNames[4], 200f, 150f);
            game.menuFont.draw(game.batch, ""+topScores[4], 400f, 150f);
        }

        game.batch.end();

        if (stage != null) stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (stage == null) return;
        stage.getViewport().update(width, height, true);
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
    }
}
