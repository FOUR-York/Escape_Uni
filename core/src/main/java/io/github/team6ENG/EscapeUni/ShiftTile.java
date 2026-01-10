package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public class ShiftTile extends RoomObject {
    int dir;
    int posX, posY;
    TextureRegion region;
    final float delta = NewGameScreen.tileWidth/6f;
    public ShiftTile(Texture texture, int x, int y, int dir) {
        super(texture, x*NewGameScreen.tileWidth, y*NewGameScreen.tileHeight);
        region = new TextureRegion(texture);
        this.dir = dir;
        this.posX = x;
        this.posY = y;
        x = x*NewGameScreen.tileWidth;
        y = y*NewGameScreen.tileHeight;
    }

    @Override
    public void update(float delta) {
        GridObject.push(NewGameScreen.room.grid, NewGameScreen.room.width, NewGameScreen.room.height, posX, posY, dir, 99);
    }

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
}
