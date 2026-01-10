package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Door extends RoomObject {
    LightSource lightSource;
    GridObject gridInstance;
    String nextRoom;
    private int posX, posY;

    public Door(String nextRoom, Texture texture, GridObject gridInstance) {
        super(texture, gridInstance.getGridX()*NewGameScreen.tileWidth+NewGameScreen.tileWidth/2f,
            gridInstance.getGridY()*NewGameScreen.tileHeight+NewGameScreen.tileHeight/2f);
        this.nextRoom = nextRoom;
        this.posX = gridInstance.getGridX();
        this.posY = gridInstance.getGridY();
        gridInstance.type = GridObject.TYPE.SOLID;
        this.gridInstance = gridInstance;

        lightSource = LightSource.createLightSource(x, y, 100f);
    }

    @Override
    public void update(float delta) {
        if (NewGameScreen.room.isKeycardCollected()) {
            gridInstance.type = GridObject.TYPE.NONE;
        }
        float pX = NewGameScreen.player.rX, pY = NewGameScreen.player.rY;
        if (NewGameScreen.dist(pX, pY, x, y) < 5) {
            // next room
            gridInstance = null;
            NewGameScreen.transition = true;
            NewGameScreen.nextRoom = nextRoom;
        };
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (NewGameScreen.room.isKeycardCollected()) {
            batch.draw(texture, posX*NewGameScreen.tileHeight, posY*NewGameScreen.tileHeight);
        }
    }
}
