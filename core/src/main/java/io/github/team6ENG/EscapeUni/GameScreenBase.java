package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Objects;

public class GameScreenBase implements Screen {
    private final Main game;

    Stage stage;
    private final GlyphLayout layout = new GlyphLayout();

    private TextButton mainMenuButton;
    private TextButton exitButton;
    private TextButton playButton;


    private final String titleText ;

    public GameScreenBase(Main game, String... titleText) {
        this.game = game;

        if (titleText.length > 0) {
            this.titleText = titleText[0];
        } else {
            this.titleText = null;
        }
    }

    /**
     * Set up each button
     * @param text buttons display text
     * @return new button with required parameters
     */
    TextButton createButton(String text, Skin skin) {
        // If skin is null, fallback to a simple TextButton may fail; ensure game.buttonSkin exists in assets
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(1.6f);
        button.pad(25f);
        button.setSize(320, 100);
        button.setColor(new Color(0.0f, 0.95f, 0.95f, 1f));
        if (Objects.equals(text, "Exit")) {exitButton = button;}
        else if (Objects.equals(text, "Main Menu")) {mainMenuButton = button;}
        else if (Objects.equals(text, "Play")) {playButton = button;}

        return button;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Add listeners for button functionality
     */
    void addListeners() {
        Color normalColor = new Color(0.0f, 0.95f, 0.95f, 1f);
        Color clickColor = new Color(0.4f, 1f, 1f, 1f);

        TextButton[] ScreenButtons = getTextButtons(exitButton, mainMenuButton, playButton);
        for (TextButton screenButton : ScreenButtons) {
            System.out.println(screenButton);
        }

        for (TextButton button : ScreenButtons) {
            System.out.println("button: " + button);
            button.addListener(new ClickListener() {
                private boolean clicked = false;
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    button.setColor(clickColor);
                    if (button == mainMenuButton) {
                        dispose();
                        game.resetGame();
                    } else if (button == exitButton) {
                        Gdx.app.postRunnable(Gdx.app::exit);
                    } else if (button == playButton && Objects.equals(titleText, "Escape University Of York")) {
                        if (clicked) return;
                        clicked = true;
                        Gdx.app.postRunnable(() -> game.setScreen(new CharacterSelectScreen(game)));
                    }  else {
                        game.setScreen(new GameScreen(game));
                    }
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    button.setColor(clickColor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    button.setColor(normalColor);
                }
            });
        }
    }

    private TextButton[] getTextButtons(TextButton exitButton, TextButton mainMenuButton, TextButton playButton) {
        TextButton[] ScreenButtons = new TextButton[0];
        System.out.println("titleText: " + titleText);
        if (Objects.equals(titleText, "Sorry you missed the bus, better luck next time")) {
            ScreenButtons = new TextButton[]{exitButton, mainMenuButton};
        } else if (Objects.equals(titleText, "It's time to meet your friends in town\nand the bus leaves in 5 minutes, better\ngrab your phone from your room in\nLangwith college before you board.")) {
            ScreenButtons = new TextButton[]{playButton};
        } else if (Objects.equals(titleText, "Escape University Of York")) {
            ScreenButtons = new TextButton[]{playButton, exitButton};
        } else if (Objects.equals(titleText, "Congratulations, you escaped university:)")) {
            ScreenButtons = new TextButton[]{exitButton, mainMenuButton};
        } else {
            ScreenButtons = new TextButton[]{mainMenuButton};
        }
        return ScreenButtons;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        System.out.println("stage != null: " + (stage != null));
        // update stage
        if (stage != null) {
            stage.act(delta);
        }

        if (stage != null) stage.draw();

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
            layout.setText(game.menuFont, titleText);
            game.menuFont.draw(game.batch, titleText, (w - layout.width) / 2f, h * 0.82f);
            game.menuFont.setColor(Color.WHITE);

            if (Objects.equals(titleText, "Congratulations, you escaped university:)")) {
                layout.setText(game.menuFont, "Score: "+ (int)game.score);
                game.menuFont.draw(game.batch, ("Score: "+ (int)game.score), (w - layout.width) / 2f, h * 0.7f);
            }
        }

        game.batch.end();

        // allow quick keyboard start (space)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Gdx.app.postRunnable(() -> game.setScreen(new CharacterSelectScreen(game)));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
