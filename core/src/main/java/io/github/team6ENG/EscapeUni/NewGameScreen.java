package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;
/*
    * helper class written by dlb, modified to fit existing team6 codebase.
    * TODO: fix projectiles, improve wall loading, create room loading helper class and format, collisions
 */
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class NewGameScreen implements Screen {

    private final Main game;

    private Texture drawerTexture;
    private ShapeDrawer shapeDrawer;
    public static Controller player;
    static int width = 20, height = 15;
    static int tileWidth = 640/width;
    static int tileHeight = 480/height;
    static Room room;

    public static AudioManager audioManager;

    // level transition
    public static String nextRoom;
    public static boolean transition = false;


    NewGameScreen(final Main game) {
        this.game = game;

        room = null;
        nextRoom = "classRoom.json";


        // initialise components
        initialiseShapeDrawer();
        initialiseAudio();

        start();
    }

    private void initialiseShapeDrawer() {
        // init shapeDrawer
        Pixmap drawerPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        drawerPixmap.setColor(Color.WHITE);
        drawerPixmap.drawPixel(0, 0);
        drawerTexture = new Texture(drawerPixmap); //remember to dispose of later
        drawerPixmap.dispose();
        TextureRegion drawerRegion = new TextureRegion(drawerTexture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(game.batch, drawerRegion);
    }


    public static void start() {
        player = null;
        // create room
        room = new Room(nextRoom);
        if (player == null) {
            errorMsg("Controller is null");
            player = new Controller(GridObject.getAt(room.grid, room.width, room.height, 1, 1), tileWidth/4f);
        }
    }

    /**
     * Call every frame to update game state
     * @param delta - Time since last frame
     */
    private void update(float delta) {
        player.step();
        room.updateProjectiles(delta);
        room.updateObjects(delta);


        game.gameTimer -= delta;

        if (game.gameTimer <= 0) {
            gameOver();
        }
    }

    @Override
    public void render(float delta) {
        // input
        handleInput();
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.viewport.apply();
        Gdx.gl.glFlush();

        game.batch.enableBlending();
        game.batch.begin();

        room.draw(shapeDrawer, game.batch);

        // render grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float theta = tileWidth / 8f;
                if (room.roomCell(i, j) == GridObject.TYPE.NONE) {
                    shapeDrawer.setColor(1.0f, 1.0f, 1.0f, 0.2f);
                    shapeDrawer.rectangle(i * tileWidth + theta, j * tileHeight + theta,
                        tileWidth - theta * 2, tileHeight - theta * 2);
                } else if (room.roomCell(i, j) == GridObject.TYPE.CONTROLLER) {
                    shapeDrawer.setColor(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b, 0.2f);
                    shapeDrawer.rectangle(i * tileWidth + theta, j * tileHeight + theta,
                        tileWidth - theta * 2, tileHeight - theta * 2);
                }
            }
        }

        shapeDrawer.setColor(0.5f, 0.5f, 0.5f, 1.0f);
        shapeDrawer.circle(player.rX,player.rY,player.radius);

        player.updateSprite();
        if (player.sprite.getTexture() != null) {
            player.sprite.draw(game.batch);
        }

        TextureRegion region = new TextureRegion(room.projectileTex);

        room.drawProjectiles(game.batch);

        room.drawObjects(shapeDrawer, game.batch);


        game.batch.end();

        //Pausing
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            audioManager.pauseMusic();
            audioManager.stopFootsteps();
            game.setScreen(new PauseScreen(game, NewGameScreen.this, audioManager));
        }

        if (transition) {
            transition = false;
            start();
        }

        //Winning the game
        if (room.end && player.gridInstance.getGridX() == 19) {
            gameWin();
        }

        renderUI();
    }


    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if (player.isInverted) {
                player.hop(3);
            }
            else {
                player.hop(1);
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if (player.isInverted) {
                player.hop(1);
            }
            else {
                player.hop(3);
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (player.isInverted) {
                player.hop(2);
            }
            else {
                player.hop(0);
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            if (player.isInverted) {
                player.hop(0);
            }
            else {
                player.hop(2);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            start();
        }
    }

    private  void initialiseAudio() {
        audioManager = new AudioManager(game);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();
        drawerTexture.dispose();
    }

    public static float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public static void infoMsg(String msg) {
        System.out.println("[INFO]: "+msg);
    }
    public static void errorMsg(String msg) {
        System.out.println("[ERROR]: "+msg);
    }

    private void drawText(BitmapFont font, String text, Color colour, float x, float y) {
        font.setColor(colour);
        font.draw(game.uiBatch, text, x, y);
    }

    private void renderUI() {
        BitmapFont smallFont = game.gameFont;
        BitmapFont bigFont = game.menuFont;
        float worldHeight = game.viewport.getWorldHeight();
        float worldWidth = game.viewport.getWorldWidth();

        game.uiBatch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.uiBatch.begin();

        float y = worldHeight - 5f;
        float lineSpacing = 15f;

        // Requirements: Events tracker and game timer
        drawText(smallFont, ("score: " +(int)game.score), Color.BLUE, 5f, y);
        y -= lineSpacing;
        drawText(smallFont, ("Negative Events: " + game.foundNegativeEvents +"/" + game.totalNegativeEvents), Color.WHITE, 5, y);
        y -= lineSpacing;
        drawText(smallFont, ("Positive Events: "+ game.foundPositiveEvents +"/"+ game.totalPositiveEvents), Color.WHITE, 5, y);
        y -= lineSpacing;
        drawText(smallFont, ("Hidden Events:   "+ game.foundHiddenEvents+"/"+ game.totalHiddenEvents), Color.WHITE, 5, y);
        y -= lineSpacing;
        //Display time with 2 digits for seconds
        drawText(smallFont, ((int)game.gameTimer/60 + ":" +((int)game.gameTimer % 60 <10?"0" :"" ) +(int)game.gameTimer % 60), Color.BLUE, worldWidth - 40f, worldHeight-5f);

        game.uiBatch.end();

    }

    public void gameOver(){
        audioManager.stopMusic();
        Gdx.app.postRunnable(() -> game.setScreen(
            new GameOverScreen(game, "Sorry you missed the bus,\nbetter luck next time...")
        ));
    }

    public void gameWin() {
        Main.score += Main.gameTimer;

        if (Main.score >= 1400) {
            if (!Main.hiddenEnding) {
                Main.hiddenEnding = true;
                Main.foundHiddenEvents++;
            }
            Gdx.app.postRunnable(() -> game.setScreen(
                new HiddenEndingScreen(game)
            ));
        }
        else {
            Gdx.app.postRunnable(() -> game.setScreen(
                new WinScreen(game)
            ));
        }
    }
}

