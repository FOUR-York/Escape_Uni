package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public abstract class RoomObject {
    float x, y;
    Texture texture;
    public RoomObject(Texture texture, float x, float y) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public abstract void step(float delta);
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        batch.draw(texture, x, y);
    }


    public void delete() {
    }
}
