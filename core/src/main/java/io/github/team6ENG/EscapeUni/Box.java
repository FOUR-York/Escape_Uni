package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * RoomObject box with a pushable GridObject to interact with the grid
 */
public class Box extends RoomObject {
    GridObject gridInstance;

    /**
     * Initialises the box
     * @param texture
     * @param gridInstance
     */
    public Box(Texture texture, GridObject gridInstance) {
        super(texture, gridInstance.getGridX()*NewGameScreen.tileWidth,
            gridInstance.getGridY()*NewGameScreen.tileHeight);
        gridInstance.type = GridObject.TYPE.PUSH;
        this.gridInstance = gridInstance;
    }

    /**
     * update logic function to be called each active frame
     * @param delta
     */
    @Override
    public void update(float delta) {
        // smoothly lerp the box to its grid position
        x = MathUtils.lerp(x, gridInstance.getGridX()*NewGameScreen.tileWidth, 0.3f);
        y = MathUtils.lerp(y, gridInstance.getGridY()*NewGameScreen.tileHeight, 0.3f);
    }

    /**
     * draw function, called each active frame
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
