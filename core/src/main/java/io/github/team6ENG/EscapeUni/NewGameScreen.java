package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.TimeUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;

import static java.lang.Math.max;
import static java.lang.Math.sin;
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

    private TextureRegion busTex;

    // level transition
    public static String nextRoom;
    public static boolean transition = false;
    public static boolean restart = false;

    private final FrameBuffer frameBuffer;
    private final Texture screenTexture;

    private final Preferences leaderboardPrefs = Gdx.app.getPreferences("leaderboardPrefs");

    NewGameScreen(final Main game) {
        this.game = game;

        room = null;
        nextRoom = "classRoom.json";

        // initialise screen space framebuffer
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, 640, 480, false);

        screenTexture = frameBuffer.getColorBufferTexture();
        screenTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        //


        initialiseShapeDrawer();
        initialiseAudio();
        Room.initialiseRooms();

        busTex = new TextureRegion(new Texture(Gdx.files.internal("images/bus.png")));

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
        // initialise components
        LightSource.initialiseLighting();
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

        boolean winFlag = false;

        Gdx.gl.glFlush();

        frameBuffer.begin();

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

        shapeDrawer.setColor(1.0f, 1.0f, 1.0f, 0.2f);
        if (player.invincible){
            shapeDrawer.setColor(new Color(Color.GOLD.r, Color.GOLD.g, Color.GOLD.b, 0.2f));
        }
        shapeDrawer.circle(player.rX,player.rY,player.radius, player.invincible? 1.7f:1f);

        room.drawObjects(game.batch);

        room.drawProjectiles(game.batch);

        game.batch.flush();
        game.batch.setShader(player.shaderProgram);

        player.updateSprite();

        player.shaderProgram.setUniformf("u_time", (float) player.powerupTimer);
        float inv = 1f;
        if (player.powerupTimer < 1f) {
            inv = (float) Math.min((int) (sin(player.powerupTimer*30f)+1f), 1);
        }
        player.shaderProgram.setUniformf("u_invulnerable", player.invincible?inv:0f);
        if (player.sprite.getTexture() != null) {
            player.sprite.draw(game.batch);
        }
        game.batch.flush();
        game.batch.setShader(null);

        if (room.end) {
            float busCoordsX = 14*tileWidth, busCoordsY = 8*tileHeight;
           // draw bus
            game.batch.draw(
                busTex,
                busCoordsX, busCoordsY,
                4*tileWidth / 2f,
                2.5f*tileHeight / 2f,
                4*tileWidth,
                2.5f*tileHeight,
                -1f,
                1f,
                0f
            );
            if (player.rX > busCoordsX &&
                player.rY > busCoordsY &&
                player.rX < busCoordsX + tileWidth*4 &&
                player.rY < busCoordsY + tileHeight*4) {
                    winFlag = true;
            }
        }

        game.batch.end();

        frameBuffer.end();


        // draw framebuffer with screen shader
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.setShader(LightSource.shaderProgram);

        game.batch.begin();

        LightSource.update(delta);
        game.batch.draw(screenTexture, 0, 0, screenTexture.getWidth(), screenTexture.getHeight(),
            0, 0, screenTexture.getWidth(), screenTexture.getHeight(), false, true);
        game.batch.end();

        game.batch.setShader(null);
        //

        //Pausing
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            audioManager.pauseMusic();
            audioManager.stopFootsteps();
            game.setScreen(new PauseScreen(game, NewGameScreen.this, audioManager));
        }

        // room transition flag
        if (transition) {
            transition = false;
            room.roomComplete();
            start();
        } else if (restart) {
            Main.score -= player.scoreEarnedThisRoom;
            player.scoreEarnedThisRoom = 0;

            if (Main.score < 0) {Main.score = 0;}

            restart = false;
            start();
        }

        //Winning the game
        if (winFlag) {
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
            restart = true;
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
        game.viewport.update(width, height);
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
        frameBuffer.dispose();
        screenTexture.dispose();
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
        drawText(bigFont, ("score: " +(int)game.score), Color.GREEN, 5f, y);
        y -= lineSpacing+10f;
        drawText(smallFont, ("Negative Events: " + game.foundNegativeEvents +"/" + game.totalNegativeEvents), Color.WHITE, 5, y);
        y -= lineSpacing;
        drawText(smallFont, ("Positive Events: "+ game.foundPositiveEvents +"/"+ game.totalPositiveEvents), Color.WHITE, 5, y);
        y -= lineSpacing;
        drawText(smallFont, ("Hidden Events:   "+ game.foundHiddenEvents+"/"+ game.totalHiddenEvents), Color.WHITE, 5, y);
        y -= lineSpacing;
        //Display time with 2 digits for seconds
        drawText(bigFont, ((int)game.gameTimer/60 + ":" +((int)game.gameTimer % 60 <10?"0" :"" ) +(int)game.gameTimer % 60), Color.RED, worldWidth - 60f, worldHeight-5f);
        // onscreen instructions
        if (nextRoom.equals("classRoom.json")) {
            GlyphLayout layout = new GlyphLayout();
            String controls = new String("Arrow keys to move\nR to restart\nP to pause");
            layout.setText(smallFont, controls);
            drawText(smallFont, controls, Color.WHITE, (worldWidth- layout.width)/2f, (worldHeight- layout.width)/2f);
            drawText(smallFont, "Coins increase score!", Color.WHITE, (worldWidth/640f)*13*tileWidth, (worldHeight/480f)*2f*tileHeight);
            drawText(smallFont, "grab the keycard and run to\nthe door to escape each room!", Color.WHITE, (worldWidth/640f)*7*tileWidth, (worldHeight/480f)*10f*tileHeight);
        }

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

        String userName = System.getProperty("user.name");
        if (userName.length() > 6) {
            userName = userName.substring(0, 6);
        }

        String[] topNames = new String[5];
        topNames[0] = leaderboardPrefs.getString("name0", "None");
        topNames[1] = leaderboardPrefs.getString("name1", "None");
        topNames[2] = leaderboardPrefs.getString("name2", "None");
        topNames[3] = leaderboardPrefs.getString("name3", "None");
        topNames[4] = leaderboardPrefs.getString("name4", "None");

        String prevName1 = "None";
        String prevName2 = "None";

        int[] topScores = new int[5];
        topScores[0] = leaderboardPrefs.getInteger("score0", 0);
        topScores[1] = leaderboardPrefs.getInteger("score1", 0);
        topScores[2] = leaderboardPrefs.getInteger("score2", 0);
        topScores[3] = leaderboardPrefs.getInteger("score3", 0);
        topScores[4] = leaderboardPrefs.getInteger("score4", 0);

        int prevScore1 = 0;
        int prevScore2 = 0;

        for (int i = 0; i < topScores.length; i++) {
            if (Main.score >= topScores[i]) {
                prevScore1 = topScores[i];
                topScores[i] = (int) Main.score;

                prevName1 = topNames[i];
                topNames[i] = userName;

                for (int j = i+1; j < topScores.length; j++) {
                    prevScore2 = topScores[j];
                    topScores[j] = prevScore1;

                    prevName2 = topNames[j];
                    topNames[j] = prevName1;
                    if (j+1 < topScores.length) {
                        prevScore1 = topScores[j+1];
                        topScores[j+1] = prevScore2;

                        prevName1 = topNames[j+1];
                        topNames[j+1] = prevName2;
                    }
                }
                break;
            }
        }

        leaderboardPrefs.putInteger("score0", topScores[0]);
        leaderboardPrefs.putInteger("score1", topScores[1]);
        leaderboardPrefs.putInteger("score2", topScores[2]);
        leaderboardPrefs.putInteger("score3", topScores[3]);
        leaderboardPrefs.putInteger("score4", topScores[4]);

        leaderboardPrefs.putString("name0", topNames[0]);
        leaderboardPrefs.putString("name1", topNames[1]);
        leaderboardPrefs.putString("name2", topNames[2]);
        leaderboardPrefs.putString("name3", topNames[3]);
        leaderboardPrefs.putString("name4", topNames[4]);

        leaderboardPrefs.flush();

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

