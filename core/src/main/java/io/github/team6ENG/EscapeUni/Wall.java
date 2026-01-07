package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public class Wall extends RoomObject {
    GridObject gridInstance;
    public Wall(GridObject gridInstance) {
        super(NewGameScreen.wallTex, gridInstance.getGridX()*NewGameScreen.tileWidth,
            gridInstance.getGridY()*NewGameScreen.tileHeight);
        gridInstance.type = GridObject.TYPE.SOLID;
        this.gridInstance = gridInstance;
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
//        drawer.setColor(1.0f, 0.5f, 0.0f, 1.0f);
//        drawer.filledRectangle(gridInstance.getGridX()*NewGameScreen.tileWidth,
//            gridInstance.getGridY()*NewGameScreen.tileHeight,
//            NewGameScreen.tileWidth,
//            NewGameScreen.tileHeight);
    }
}
