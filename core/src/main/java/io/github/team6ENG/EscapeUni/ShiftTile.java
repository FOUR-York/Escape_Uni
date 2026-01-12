package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * RoomObject that pushes GridObjects at its position in the grid with unlimited force
 */
public class ShiftTile extends RoomObject {
    int dir;
    int posX, posY;
    TextureRegion region;
    final float delta = NewGameScreen.tileWidth/6f;

    /**
     * Create and initialise the ShiftTile
     * @param texture
     * @param gridX
     * @param gridY
     * @param dir
     */
    public ShiftTile(Texture texture, int gridX, int gridY, int dir) {
        super(texture, gridX*NewGameScreen.tileWidth, gridY*NewGameScreen.tileHeight);
        region = new TextureRegion(texture);
        this.dir = dir;
        this.posX = gridX;
        this.posY = gridY;
        this.x = gridX*NewGameScreen.tileWidth;
        this.y = gridY*NewGameScreen.tileHeight;
    }

    /**
     * Push GridObjects at posX, posY every frame
     * @param delta
     */
    @Override
    public void update(float delta) {
        GridObject.push(NewGameScreen.room.grid, NewGameScreen.room.width, NewGameScreen.room.height, posX, posY, dir, 99);
    }

    /**
     * Draw the ShiftTile every frame with its texture rotated in its direction
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
}
