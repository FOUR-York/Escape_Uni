package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Door class
 * attaches to a gridObject and makes it solid
 * when keycard collected, change attached grid object to GridObject.TYPE.NONE
 * when player in range, initiate a room transition
 */
public class Door extends RoomObject {
    LightSource lightSource;
    GridObject gridInstance;
    String nextRoom;
    private int posX, posY;

    /**
     * Initialise Door
     * @param nextRoom
     * @param texture
     * @param gridInstance
     */
    public Door(String nextRoom, Texture texture, GridObject gridInstance) {
        super(texture, gridInstance.getGridX()*NewGameScreen.tileWidth+NewGameScreen.tileWidth/2f,
            gridInstance.getGridY()*NewGameScreen.tileHeight+NewGameScreen.tileHeight/2f);
        this.nextRoom = nextRoom;
        this.posX = gridInstance.getGridX();
        this.posY = gridInstance.getGridY();
        gridInstance.type = GridObject.TYPE.SOLID;
        this.gridInstance = gridInstance;

        // add position as light source
        lightSource = LightSource.createLightSource(x, y, 100f);
    }

    /**
     * logic update function
     * @param delta
     */
    @Override
    public void update(float delta) {
        if (gridInstance != null && NewGameScreen.room.isKeycardCollected()) {
            gridInstance.type = GridObject.TYPE.NONE;
            // detach from gridObject
            gridInstance = null;
        }
        float pX = NewGameScreen.player.rX, pY = NewGameScreen.player.rY;
        // transition if player in range
        if (NewGameScreen.dist(pX, pY, x, y) < 5) {
            // next room
            NewGameScreen.transition = true;
            NewGameScreen.nextRoom = nextRoom;
        };
    }

    /**
     * Draw function
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        if (NewGameScreen.room.isKeycardCollected()) {
            batch.draw(texture, posX*NewGameScreen.tileHeight, posY*NewGameScreen.tileHeight);
        }
    }
}
