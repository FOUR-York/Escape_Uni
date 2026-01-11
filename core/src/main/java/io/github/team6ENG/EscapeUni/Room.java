package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
public class Room {
    public GridObject[] grid;
    public ArrayList<RoomObject> objects;
    public int width, height;

    private final Projectile[] projectiles;
    private int projectileCount = 0;

    // textures
    public Texture roomTex;
    public Texture openRoomTex;
    public Texture doorTex;
    public Texture boxTex;
    public Texture shiftTex;
    public Texture turretTex;
    public Texture keycardTex;
    public Texture powerupTex;
    public Texture projectileTex;
    public Texture inverterTex;
    public Texture bobTex;
    public Texture switchTex;
    public Texture coinTex;

    public boolean end = false;
    private boolean keycard = false;

    private TextureRegion projectileRegion;


    public Room(String path) {
        this.width = 20;
        this.height = 15;
        objects = new ArrayList<>();
        grid = GridObject.createGrid(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width-1 ||  y == 0 || y == height-1) {
                    grid[y*width+x].type = GridObject.TYPE.SOLID;
                } else {
                    grid[y*width+x].type = GridObject.TYPE.NONE;
                }
            }
        }

        // reset variables
        projectiles = new Projectile[100];
        projectileCount = 0;
        loadRoom(path);
    }

    public GridObject.TYPE roomCell(int x, int y) {
        //bounds check
        if (x >= 0 && x <= width - 1 && y >= 0 && y <= height - 1) {
            // get the cell inverted, so
            return grid[x+(height-1-y)*width].type;
        }
        System.out.print("[ERROR]: Invalid call to roomCell: coords out of bounds\n");
        return GridObject.TYPE.NONE;
    }

    public void addObject(RoomObject object) {
        objects.add(object);
    }

    public void updateObjects(float delta) {
        for (RoomObject roomObject : objects) {
            if (roomObject != null) {
                roomObject.update(delta);
            }
        }
    }

    private void loadRoom(String path) {
        JsonValue mapJson = new JsonReader().parse(Gdx.files.internal(path));
        // start by loading map dimensions: should be 20x15
        int[] dimensions = mapJson.get("dimensions").asIntArray();
        int width = dimensions[0];
        int height = dimensions[1];
        // textures
        if (path.equals("outside.json")) {
            end = true;
        }
        roomTex = new Texture(Gdx.files.internal(mapJson.get("roomTex").asString()));
        if (mapJson.get("openRoomTex") != null) {
            openRoomTex = new Texture(Gdx.files.internal(mapJson.get("openRoomTex").asString()));
        }

        doorTex = new Texture(Gdx.files.internal(mapJson.get("doorTex").asString()));
        boxTex = new Texture(Gdx.files.internal(mapJson.get("boxTex").asString()));
        shiftTex = new Texture(Gdx.files.internal(mapJson.get("shiftTex").asString()));
        turretTex = new Texture(Gdx.files.internal(mapJson.get("turretTex").asString()));
        keycardTex = new Texture(Gdx.files.internal(mapJson.get("keycardTex").asString()));
        powerupTex = new Texture(Gdx.files.internal(mapJson.get("powerupTex").asString()));
        projectileTex = new Texture(Gdx.files.internal(mapJson.get("projectileTex").asString()));
        projectileRegion = new TextureRegion(projectileTex);
        inverterTex = new Texture(Gdx.files.internal(mapJson.get("inverterTex").asString()));
        bobTex = new Texture(Gdx.files.internal(mapJson.get("bobTex").asString()));
        switchTex = new Texture(Gdx.files.internal(mapJson.get("switchTex").asString()));
        coinTex = new Texture(Gdx.files.internal(mapJson.get("coinTex").asString()));
        // start loading in objects
        int[] gridData = mapJson.get("gridData").asIntArray();
        for (int i = 0; i < width; i ++) {
            for (int j = 0; j < height; j ++) {
                int objectId = i+width*j;
                if (gridData[objectId] == 0) {
                    grid[objectId].type = GridObject.TYPE.NONE;
                } else if (gridData[objectId] == 1) {
                    grid[objectId].type = GridObject.TYPE.SOLID;
                }
            }
        }
        int[] roomData = mapJson.get("roomObjectData").asIntArray();
        for (int i = 0; i < width; i ++) {
            for (int j = 0; j < height; j ++) {
                int objectId = roomData[i + (height - 1 - j) * width];
                JsonValue jsonObject = mapJson.get("objects").get(objectId).child;
                switch (jsonObject.name) {
                    case "door": {
                        addObject(new Door(jsonObject.get("nextRoom").asString(), doorTex, GridObject.getAt(grid, width, height, i, j)));
                        break;
                    }
                    case "box": {
                        addObject(new Box(boxTex, GridObject.getAt(grid, width, height, i, j)));
                        break;
                    }
                    case "shift": {
                        int r = jsonObject.get("direction").asInt();
                        addObject(new ShiftTile(shiftTex, i, j, r));
                        break;
                    }
                    case "turret": {
                        int r = jsonObject.get("direction").asInt();
                        addObject(new Turret(turretTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight, r, 4f));
                        break;
                    }
                    case "playerSpawn": {
                        NewGameScreen.player = new Controller(GridObject.getAt(grid, width, height, i, j), NewGameScreen.tileWidth / 4f);
                        break;
                    }
                    case "powerup": {
                        addObject(new Powerup(powerupTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight));
                        break;
                    }
                    case "keycard": {
                        addObject(new Keycard(keycardTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight));
                        break;
                    }
                    case "inverter": {
                        addObject(new ControlInverter(inverterTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight));
                        break;
                    }
                    case "bob": {
                        addObject(new HiddenBob(bobTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight));
                        break;
                    }
                    case "switch": {
                        addObject(new LightSwitch(switchTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight));
                        break;
                    }
                    case "coin": {
                        addObject(new ScorePickup(coinTex, i * NewGameScreen.tileWidth, j * NewGameScreen.tileHeight));
                    }
                    case "null":
                    default:
                        break;
                }
            }
        }
    }

    public void openDoors() {
        keycard = true;
    }

    public void spawnProjectile(float x, float y, float speed, int dir, float radius) {
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

    public void removeProjectile(int id) {
        projectiles[id] = null;
        projectileCount--;
    }

    public void updateProjectiles(float delta) {
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
                projectile.update(delta);
            }
        }
    }

    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        if (!keycard || openRoomTex == null) {
            batch.draw(roomTex, 0, 0);
        } else {
            batch.draw(openRoomTex, 0, 0);
        }
    }
    public void dispose() {
        roomTex.dispose();
        doorTex.dispose();
        boxTex.dispose();
        shiftTex.dispose();
        turretTex.dispose();
        keycardTex.dispose();
        powerupTex.dispose();
        projectileTex.dispose();
        inverterTex.dispose();
        bobTex.dispose();
    }

    public void drawProjectiles(SpriteBatch batch) {
        for (Projectile projectile : projectiles) {
            if (projectile != null) {
//                shapeDrawer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
//                shapeDrawer.circle(projectile.x,projectile.y,projectile.radius);
                batch.draw(
                    projectileRegion,
                    projectile.x-projectileRegion.getRegionWidth()/2f, projectile.y-projectileRegion.getRegionHeight()/2f,
                    projectileRegion.getRegionWidth() / 2f,
                    projectileRegion.getRegionHeight() / 2f,
                    projectileRegion.getRegionWidth(),
                    projectileRegion.getRegionHeight(),
                    1f, 1f,
                    90f*projectile.dir+90f
                );
            }
        }
    }

    public void drawObjects(SpriteBatch batch) {
        for (RoomObject roomObject : objects) {
            if (roomObject != null) {
                roomObject.draw(batch);
            }
        }
    }

    public boolean isKeycardCollected() {
        return keycard;
    }
}
