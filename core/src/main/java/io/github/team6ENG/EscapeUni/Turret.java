package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public class Turret extends RoomObject {
    float t;
    int amp;
    int dir;
    float speed;
    TextureRegion region;
    public Turret(Texture texture, float x, float y, int dir, float speed) {
        super(texture, x, y);
        region = new TextureRegion(texture);
        this.dir = dir;
        this.speed = speed;
        t = 0;
        amp = (int)Math.min(func(speed*t)+1, 1) ; // 0 or 1
    }

    @Override
    public void update(float delta) {
        t += delta;
        if ((int)Math.min(func(speed*t)+1, 1) != amp) {
            NewGameScreen.spawnProjectile(x+NewGameScreen.tileWidth/2f, y+NewGameScreen.tileHeight/2f, 4f, dir, NewGameScreen.tileWidth/4f);
            amp = (int)Math.min(func(speed*t)+1, 1) ; // 0 or 1
        }
    }

    @Override
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
//        drawer.setColor(1.0f, 1.0f, 0.0f, 1.0f);
//        drawer.rectangle(x, y, NewGameScreen.tileWidth, NewGameScreen.tileHeight);
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

    private float func(float x) {
        return (float) ((Math.abs(Math.abs(Math.sin(x)*2f)-0.5f)*2f-0.5f)*2);
    }

}
