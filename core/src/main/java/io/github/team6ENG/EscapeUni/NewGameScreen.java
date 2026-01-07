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

    // textures
    public static Texture roomTex;
    public static Texture wallTex;
    public static Texture boxTex;
    public static Texture shiftTex;
    public static Texture turretTex;
    public static Texture keycardTex;
    public static Texture powerupTex;

    NewGameScreen(final Main game) {
        this.game = game;

        // init shapeDrawer
        Pixmap drawerPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        drawerPixmap.setColor(Color.WHITE);
        drawerPixmap.drawPixel(0, 0);
        drawerTexture = new Texture(drawerPixmap); //remember to dispose of later
        drawerPixmap.dispose();
        TextureRegion drawerRegion = new TextureRegion(drawerTexture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(game.batch, drawerRegion);

        initialiseAudio();

        start();
    }


    public static void start() {
        player = null;
        // reset variables
        projectiles = new Projectile[100];
        projectileCount = 0;
        projectileReception();
        if (player == null) {
            errorMsg("Controller is null");
            player = new Controller(GridObject.getAt(room.grid, room.width, room.height, 1, 1), tileWidth/4f, game);
        }
    }

    public static void ClassRoomOne() {
        // load textures
        roomTex = new Texture(Gdx.files.internal("test/ClassRoom1.png"));

        wallTex = new Texture(Gdx.files.internal("test/wall.png"));
        boxTex = new Texture(Gdx.files.internal("test/box.png"));
        shiftTex = new Texture(Gdx.files.internal("test/shift.png"));
        turretTex = new Texture(Gdx.files.internal("test/turret.png"));
        keycardTex = new Texture(Gdx.files.internal("items/keycard1.png"));
        powerupTex = new Texture(Gdx.files.internal("items/star.png"));
        //ClassRoom1
        room = new Room(width, height);
        //ClassRoom1
        loadLevelMatrix(new int[]{
            // Row 0
            4, 4, 4 /*door*/, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 /*door*/, 4, 4,
            // Row 1
            4, 0, 0, 0, 0, 4 /*chair*/, 4 /*table*/, 4 /*table*/, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 2
            4, 0, 0, 0, 0, 4 /*chair*/, 4 /*table*/, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 3
            4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 4
            4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 5
            4, 0, 0, 0, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/,
            4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 0, 0, 0, 4,
            // Row 6
            4, 0, 0, 0, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/,
            4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 0, 0, 0, 4,
            // Row 7
            4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 8
            4, 0, 0, 0, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/,
            4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 0, 0, 0, 4,
            // Row 9
            4, 0, 0, 0, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/,
            4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 0, 0, 0, 4,
            // Row 10
            4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 11
            4, 0, 0, 0, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/,
            4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 4 /*door*/, 0, 0, 0, 4,
            // Row 12
            4, 0, 0, 0, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/,
            4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 0, 0, 0, 4,
            // Row 13
            4, 0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 14
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
        });
    }

    public static void BoxRoomOne() {
        // load textures
        roomTex = new Texture(Gdx.files.internal("test/room.png"));

        wallTex = new Texture(Gdx.files.internal("test/wall.png"));
        boxTex = new Texture(Gdx.files.internal("test/box.png"));
        shiftTex = new Texture(Gdx.files.internal("test/shift.png"));
        turretTex = new Texture(Gdx.files.internal("test/turret.png"));
        keycardTex = new Texture(Gdx.files.internal("items/keycard1.png"));
        powerupTex = new Texture(Gdx.files.internal("items/star.png"));
        // BoxRoom1
        room = new Room(width, height);
        //ClassRoom1
        loadLevelMatrix(new int[]{
            // Row 0
            4, 4, 4 /*door*/, 4, 4, 4, 4, 4, 4, 4, 4 /*door*/, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            // Row 1
            4, 0, 0, 11, 0, 0, 4 /*bgbox*/, 4 /*bgbox*/, 4,  4 /*bgbox*/, 0, 0, 4 /*bgbox*/, 0, 4, 0,
            4 /*bgbox*/, 4 /*bgbox*/, 4 /*bgbox*/, 4,
            // Row 2
            4, 4, 4, 0, 0, 11, 0, 4 /*bgbox*/, 4, 4 /*bgbox*/, 0, 11, 4 /*bgbox*/, 4, 0, 0, 11, 0, 0, 4,
            // Row 3
            4, 0, 4, 4, 4, 4, 0, 4 /*bgbox*/, 4, 4 /*bgbox*/, 11, 0, 0, 4, 11, 4 /*bgbox*/, 4, 0, 4 /*bgbox*/, 4,
            // Row 4
            4, 11, 4, 4 /*bgbox*/, 4 /*bgbox*/, 4, 0, 0, 4, 4 /*bgbox*/, 0, 0, 0, 4, 0, 0, 4, 0, 4 /*bgbox*/, 4,
            // Row 5
            4, 0, 0, 0, 4 /*bgbox*/, 4, 0, 0, 4, 4 /*bgbox*/, 0, 0, 0, 4, 0, 11, 4, 0, 4 /*bgbox*/, 4,
            // Row 6
            4, 0, 4, 0, 0, 4, 0, 0, 4, 4, 4, 4, 0, 4, 4 /*bgbox*/, 0, 4, 11, 0, 4,
            // Row 7
            4, 0, 4, 0, 0, 4, 0, 0, 4 /*bgbox*/, 4 /*bgbox*/, 0, 4, 11, 4, 4 /*bgbox*/, 0, 4, 0, 0, 4,
            // Row 8
            4, 0, 4, 11, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4 /*bgbox*/, 0, 4,
            // Row 9
            4, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4,  4 /*bgbox*/, 0, 4,
            // Row 10
            4, 4 /*bgbox*/, 4 /*bgbox*/, 0, 0, 0, 4 /*bgbox*/, 4 /*bgbox*/, 4, 0, 0, 11, 0, 4, 0, 0, 4, 4, 4, 4,
            // Row 11
            4, 0, 4, 4 /*bgbox*/, 0, 0, 0, 0, 4, 0, 0, 4 /*bgbox*/, 4, 4, 0, 0, 11, 0, 4 /*bgbox*/, 4,
            // Row 12
            4, 0, 4, 4, 4, 4, 4, 0, 0, 11, 4 /*bgbox*/, 4 /*bgbox*/, 4, 0, 0, 0, 11, 0, 0, 4,
            // Row 13
            4, 0, 0, 0, 0, 11, 0, 0, 11, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0, 4,
            // Row 14
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 /*door*/, 4, 4,
        });
    }

    public static void directionPuzzle() {
        // load textures
        roomTex = new Texture(Gdx.files.internal("test/pushPuzzle.png"));

        wallTex = new Texture(Gdx.files.internal("test/wall.png"));
        boxTex = new Texture(Gdx.files.internal("test/box.png"));
        shiftTex = new Texture(Gdx.files.internal("test/shift.png"));
        turretTex = new Texture(Gdx.files.internal("test/turret.png"));
        keycardTex = new Texture(Gdx.files.internal("items/keycard1.png"));
        powerupTex = new Texture(Gdx.files.internal("items/star.png"));
        // PushPuzzle
        room = new Room(width, height);
        //Push (up) = 12
        //Push (left) = 13
        //Push (down) = 14
        //Push (right) = 15
        loadLevelMatrix(new int[]{
            // Row 0
            4, 4, 4, 4 /*door*/, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4 /*door*/, 4, 4, 4,
            // Row 1
            4, 14, 13, 0, 0, 0, 13, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 15, 14, 4,
            // Row 2
            4, 14, 12, 0, 0, 0, 15, 0, 4, 4, 4, 4, 0, 0, 0, 0, 0, 12, 14, 4,
            // Row 3
            4, 14, 12, 0, 0, 15, 15, 0, 4, 4, 4, 4, 0, 0, 12, 12, 13, 0, 14, 4,
            // Row 4
            4, 15, 0, 15, 15, 12, 0, 14, 4, 4, 4, 4, 0, 0, 0, 0, 0, 12, 13, 4,
            // Row 5
            4, 12, 0, 0, 14, 12, 15, 14, 4, 4, 4, 4, 14, 13, 13, 13, 0, 12, 13, 4,
            // Row 6
            4, 15, 12, 12, 15, 12, 12, 14, 4, 4, 4, 4, 14, 15, 0, 15, 15, 14, 12, 4,
            // Row 7
            4, 12, 13, 13, 13, 13, 0, 13, 4, 4, 4, 4, 14, 12, 0, 12, 0, 0, 12, 4,
            // Row 8
            4, 4, 4, 4, 4, 4, 4, 12, 13, 13, 4, 4, 14, 12, 0, 12, 0, 0, 12, 4,
            // Row 9
            4, 4, 4, 4, 4, 4, 4, 14, 15, 12, 4, 4, 15, 0, 15, 12, 0, 0, 12, 4,
            // Row 10
            4, 4, 4, 4, 4, 4, 4, 15, 0, 15, 15, 14, 12, 14, 0, 0, 14, 15, 12, 4,
            // Row 11
            4, 4, 4, 4, 4, 4, 4, 12, 14, 12, 14, 13, 13, 14, 0, 0, 15, 15, 12, 4,
            // Row 12
            4, 4, 4, 4, 4, 4, 4, 12, 13, 0, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            // Row 13
            4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 13, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            // Row 14
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4 /*door*/, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
        });
    }

    public static void projectileReception() {
        // load textures
        roomTex = new Texture(Gdx.files.internal("test/projectileReception.png"));

        wallTex = new Texture(Gdx.files.internal("test/wall.png"));
        boxTex = new Texture(Gdx.files.internal("test/box.png"));
        shiftTex = new Texture(Gdx.files.internal("test/shift.png"));
        turretTex = new Texture(Gdx.files.internal("test/turret.png"));
        keycardTex = new Texture(Gdx.files.internal("items/keycard1.png"));
        powerupTex = new Texture(Gdx.files.internal("items/star.png"));
        room = new Room(width, height);
        /*Proj (up) = 16, Proj (Left) = 17, Proj (down) = 18, Proj (right) = 19*/
        /*Keycard = 28, Powerup = 24*/
        loadLevelMatrix(new int[]{
            // Row 0
            4, 4, 4, 4 /*door*/, 4 /*door*/, 18, 4, 4, 4, 4, 18, 4, 4, 4, 4, 4, 4, 4, 4, 4,
            // Row 1
            4, 4 /*chair*/, 0, 0, 0, 0, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 0, 0, 0, 4 /*chair*/, 4, 4, 4, 4, 4, 4,
            // Row 2
            19, 0, 0, 0, 0, 0, 4 /*chair*/, 0, 0, 4 /*chair*/, 0, 0, 0, 0, 4 /*chair*/, 0, 4, 4, 4, 4,
            // Row 3
            19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 /*chair*/, 4 /*chair*/, 0, 0, 0, 0,
            // Row 4
            4, 0, 0, 0, 0, 0, 0, 4 /*chair*/, 0, 0, 0, 0, 0, 0, 4 /*chair*/, 0, 4, 4, 4, 4,
            // Row 5
            4, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4 /*chair*/, 4, 4, 4, 4, 0, 0, 4, 4, 4, 4, 4, 4,
            // Row 6
            4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 4,
            // Row 7
            4, 4, 4, 4, 4, 4, 4, 19, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4,
            // Row 8
            4, 0 /*ITEM?*/, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4,
            // Row 9
            4, 0, 16, 4 /*chair*/, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 4 /*chair*/, 4 /*chair*/, 0, 4,
            // Row 10
            4, 4 /*chair*/, 4 /*table*/, 4 /*table*/, 0, 0, 4, 4, 0, 0, 0, 4, 4, 0, 0, 17, 4 /*table*/, 4 /*table*/, 0, 4,
            // Row 11
            4, 4 /*chair*/, 4 /*table*/, 4 /*table*/, 0, 0, 4, 4, 0, 0, 0, 4, 4, 0, 4 /*chair*/, 4 /*table*/,
            4 /*table*/, 4 /*table*/ , 0, 4,
            // Row 12
            4, 0, 4 /*chair*/, 4 /*chair*/, 0, 0, 4, 19, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 4,
            // Row 13
            4, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 4, 4, 0, 0, 0, 0, 4 /*door*/, 16, 4,
            // Row 14
            4, 4, 4, 4, 4, 16, 4, 4, 4, 0 /*ENTER*/, 4, 4, 4, 4, 16, 4, 4, 4, 4, 4,
        });
    }

    public static void loadLevelMatrix(int[] levelMatrix) {
        if (levelMatrix.length != room.width*room.height) {
            errorMsg("Invalid level matrix");
            return;
        }
        for (int i = 0; i < room.width; i++) {
            for (int j = 0; j < room.height; j++) {
                int matData = levelMatrix[i+(room.height-j-1)*room.width];
                int objId = matData>>2;
                infoMsg(String.valueOf(objId));
                int r = matData%4;
                switch (objId) {
                    case 0:
                        break;
                    case 1:
                        room.addObject(new Wall(GridObject.getAt(room.grid, room.width, room.height, i, j)));
                        break;
                    case 2:
                        room.addObject(new Box(GridObject.getAt(room.grid, room.width, room.height, i, j)));
                        break;
                    case 3:
                        room.addObject(new ShiftTile(i, j, r));
                        break;
                    case 4:
                        room.addObject(new Turret(i*tileWidth, j*tileHeight, r, 4f));
                        break;
                    case 5:
                        player = new Controller(GridObject.getAt(room.grid, room.width, room.height, i, j), tileWidth/4f, game);
                        break;
                    case 6:
                        room.addObject(new Powerup(i*tileWidth, j*tileHeight));
                        break;
                    case 7:
                        room.addObject(new Keycard(i*tileWidth, j*tileHeight));
                        break;
                    default:
                        break;
                }
            }
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
//            audioManager.pauseMusic();
//            audioManager.stopFootsteps();
            game.setScreen(new PauseScreen(game, NewGameScreen.this, audioManager));
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
            player.hop(1);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.hop(3);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.hop(0);
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.hop(2);
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
        roomTex.dispose();
        wallTex.dispose();
        boxTex.dispose();
        shiftTex.dispose();
        turretTex.dispose();
        keycardTex.dispose();
        powerupTex.dispose();
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

