package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;
/*
    * helper class written by dlb, modified to fit existing team6 codebase.
    * TODO: fix projectiles, improve wall loading, create room loading helper class and format, collisions
 */
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class NewGameScreen implements Screen {

    private static Main game;

    private Texture drawerTexture;
    private ShapeDrawer shapeDrawer;
    public static Controller player;
    static int width = 20, height = 15;
    static int tileWidth = 640/width;
    static int tileHeight = 480/height;
    static Room room;
    private static Projectile[] projectiles;
    private static int projectileCount = 0;

    public static boolean keycard = false;

    public AudioManager audioManager;

    public static String nextRoom = "classRoom.json";
    public static boolean transition = false;


    NewGameScreen(final Main game) {
        NewGameScreen.game = game;

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
        keycard = false;
        player = null;
        // reset variables
        projectiles = new Projectile[100];
        projectileCount = 0;
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
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                projectile.step();
            }
        }

        for (RoomObject roomObject : room.objects) {
            if (roomObject != null) {
                roomObject.update(delta);
            }
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

        game.batch.begin();

        room.draw(shapeDrawer, game.batch);

        // render grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                float theta = tileWidth / 8f;
                if (roomCell(i, j) == GridObject.TYPE.NONE) {
                    shapeDrawer.setColor(1.0f, 1.0f, 1.0f, 0.2f);
                    shapeDrawer.rectangle(i * tileWidth + theta, j * tileHeight + theta,
                        tileWidth - theta * 2, tileHeight - theta * 2);
                } else if (roomCell(i, j) == GridObject.TYPE.PLAYER) {
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

        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                shapeDrawer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
                shapeDrawer.circle(projectile.x,projectile.y,projectile.radius);
            }
        }


        for (RoomObject roomObject : room.objects) {
            if (roomObject != null) {
                roomObject.draw(shapeDrawer, game.batch);
            }
        }

        game.batch.end();


        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            audioManager.pauseMusic();
            audioManager.stopFootsteps();
            game.setScreen(new PauseScreen(game, NewGameScreen.this, audioManager));
        }

        if (transition) {
            transition = false;
            start();
        }
    }

    public static GridObject.TYPE roomCell(int x, int y) {
        //bounds check
        if (x >= 0 && x <= room.width - 1 && y >= 0 && y <= room.height - 1) {
            // get the cell inverted, so
            return room.grid[x+(room.height-1-y)*room.width].type;
        }
        System.out.print("[ERROR]: Invalid call to roomCell: coords out of bounds\n");
        return GridObject.TYPE.NONE;
    }

    public static void spawnProjectile(float x, float y, float speed, int dir, float radius) {
        if (projectileCount < projectiles.length) {
            for (int i = 0; i < projectiles.length; i++) {
                if (projectiles[i] == null) {
                    projectiles[i] = new Projectile(x, y, speed, dir, radius, i);
                    break;
                }
            }
            projectileCount++;
        }
    }

    public static void removeProjectile(int id) {
        projectiles[id] = null;
        projectileCount--;
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
}

