package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Door extends RoomObject {
    GridObject gridInstance;
    String nextRoom;
    public Door(String nextRoom, Texture texture, GridObject gridInstance) {
        super(texture, gridInstance.getGridX()*NewGameScreen.tileWidth+NewGameScreen.tileWidth/2f,
            gridInstance.getGridY()*NewGameScreen.tileHeight+NewGameScreen.tileHeight/2f);
        this.nextRoom = nextRoom;
        gridInstance.type = GridObject.TYPE.SOLID;
        this.gridInstance = gridInstance;
    }

    @Override
    public void update(float delta) {
        if (NewGameScreen.room.isKeycardCollected()) {
            gridInstance.type = GridObject.TYPE.NONE;
        }
        float pX = NewGameScreen.player.rX, pY = NewGameScreen.player.rY;
        if (NewGameScreen.dist(pX, pY, x, y) < 5) {
            // next room
            NewGameScreen.transition = true;
            NewGameScreen.nextRoom = nextRoom;
        };
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (NewGameScreen.room.isKeycardCollected()) {
        }
    }
}
