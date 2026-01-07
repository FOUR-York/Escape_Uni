package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Door extends RoomObject {
    GridObject gridInstance;
    public Door(Texture texture, GridObject gridInstance) {
        super(texture, gridInstance.getGridX()*NewGameScreen.tileWidth+NewGameScreen.tileWidth/2f,
            gridInstance.getGridY()*NewGameScreen.tileHeight+NewGameScreen.tileHeight/2f);
        gridInstance.type = GridObject.TYPE.SOLID;
        this.gridInstance = gridInstance;
    }

    @Override
    public void update(float delta) {
        if (NewGameScreen.keycard) {
            gridInstance.type = GridObject.TYPE.NONE;
        }
        float pX = NewGameScreen.player.rX, pY = NewGameScreen.player.rY;
        if (NewGameScreen.dist(pX, pY, x, y) < 5) {
            // next room
            NewGameScreen.infoMsg("Next room");
        };
    }

    @Override
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        if (NewGameScreen.keycard) {
            drawer.filledCircle(x, y, 3);
        }
    }
}
