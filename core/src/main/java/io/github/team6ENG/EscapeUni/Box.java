package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;
/*
    * helper class written by dlb
 */
public class Box extends RoomObject {
    GridObject gridInstance;
    public Box(GridObject gridInstance) {
        super(NewGameScreen.boxTex, gridInstance.getGridX()*NewGameScreen.tileWidth,
            gridInstance.getGridY()*NewGameScreen.tileHeight);
        gridInstance.type = GridObject.TYPE.PUSH;
        this.gridInstance = gridInstance;
    }

    @Override
    public void step(float delta) {
        x = MathUtils.lerp(x, gridInstance.getGridX()*NewGameScreen.tileWidth, 0.3f);
        y = MathUtils.lerp(y, gridInstance.getGridY()*NewGameScreen.tileHeight, 0.3f);
    }

    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        float delta = NewGameScreen.tileWidth/6f;
        drawer.setColor(0.9f, 0.5f, 0.2f, 1.0f);
        drawer.rectangle(x+delta, y+delta, NewGameScreen.tileWidth-delta*2, NewGameScreen.tileHeight-delta*2);
        drawer.line(x+delta, y+delta, x+NewGameScreen.tileWidth-delta, y+NewGameScreen.tileHeight-delta);
    }
}
