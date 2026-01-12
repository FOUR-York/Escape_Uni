package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Abstract class for RoomObjects to implement
 */
public abstract class RoomObject {
    float x, y;
    Texture texture;

    /**
     * Create and initialise the RoomObject's texture and position
     * @param texture
     * @param x
     * @param y
     */
    public RoomObject(Texture texture, float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    /**
     * Abstract update function called every active frame
     * @param delta
     */
    public abstract void update(float delta);


    /**
     * Draw function to be called every active frame
     * Draws the RoomObject's texture at its position with the given SpriteBatch
     * @param batch
     */
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }
}
