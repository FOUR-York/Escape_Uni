package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public class Turret extends RoomObject {
    float t;
    int amp;
    int dir;
    float speed;
    public Turret(Texture texture, float x, float y, int dir, float speed) {
        super(texture, x, y);
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
        drawer.setColor(1.0f, 1.0f, 0.0f, 1.0f);
        drawer.rectangle(x, y, NewGameScreen.tileWidth, NewGameScreen.tileHeight);
    }

    private float func(float x) {
        return (float) ((Math.abs(Math.abs(Math.sin(x)*2f)-0.5f)*2f-0.5f)*2);
    }

}
