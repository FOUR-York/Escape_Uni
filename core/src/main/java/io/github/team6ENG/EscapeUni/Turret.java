package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * RoomObjects that periodically fires projectiles in a direction
 */
public class Turret extends RoomObject {
    float t;
    int amp;
    int dir;
    float speed;
    TextureRegion region;

    /**
     * Creates and initialises a new Turret
     * @param texture
     * @param x
     * @param y
     * @param dir cardinal direction that the turret fires
     * @param speed speed of projectiles and turret firing rate
     */
    public Turret(Texture texture, float x, float y, int dir, float speed) {
        super(texture, x, y);
        region = new TextureRegion(texture);
        this.dir = dir;
        this.speed = speed;
        t = 0;
        // starting amplitude
        amp = (int)Math.min(func(speed*t)+1, 1) ; // 0 or 1
    }

    /**
     * Update logic called every frame
     * @param delta
     */
    @Override
    public void update(float delta) {
        t += delta;
        // fire if the value of the oscillating stepwise function has changed
        if ((int)Math.min(func(speed*t)+1, 1) != amp) {
            NewGameScreen.room.spawnProjectile(x+NewGameScreen.tileWidth/2f, y+NewGameScreen.tileHeight/2f, speed, dir, NewGameScreen.tileWidth/4f);
            amp = (int)Math.min(func(speed*t)+1, 1) ; // 0 or 1
        }
    }

    /**
     * Draw function called every active frame
     * draws the turret rotated in its direction
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
            region,
            x, y,
            region.getRegionWidth() / 2f,
            region.getRegionHeight() / 2f,
            region.getRegionWidth(),
            region.getRegionHeight(),
            1f, 1f,
            90f*dir
        );
    }

    /**
     * Helper function to describe a wave with evenly spaced roots that are used to calculate when the turret should fire
     * @param x
     * @return
     */
    private float func(float x) {
        return (float) ((Math.abs(Math.abs(Math.sin(x)*2f)-0.5f)*2f-0.5f)*2);
    }

}
