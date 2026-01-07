package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public class Door extends RoomObject {
    GridObject gridInstance;
    public Door(GridObject gridInstance) {
        super(NewGameScreen.wallTex, gridInstance.getGridX()*NewGameScreen.tileWidth,
            gridInstance.getGridY()*NewGameScreen.tileHeight);
        gridInstance.type = GridObject.TYPE.SOLID;
        this.gridInstance = gridInstance;
    }

    @Override
    public void update(float delta) {
        if (NewGameScreen.keycard) {
            gridInstance.type = GridObject.TYPE.NONE;
        }
    }

    @Override
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
    }
}
