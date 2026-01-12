package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * RoomObject that displays a collectable hidden event
 */
public class HiddenBob extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;
    private Sprite sprite;

    /**
     * Create and initialise HiddenBob
     * @param texture
     * @param x
     * @param y
     */
    public HiddenBob(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
        sprite = new Sprite(texture);
    }


    /**
     * Update logic function, called each frame
     * @param delta
     */
    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                collected = true;

                if (!Main.bob) {
                    Main.bob = true;
                    Main.foundHiddenEvents++;
                }

                // count score if room is uncompleted
                if (!NewGameScreen.room.isVisited()){
                    Main.score += 500;
                    NewGameScreen.player.scoreEarnedThisRoom += 500;
                }
            }
        }
    }

    /**
     * Draw function called every frame
     * Draw bob if the player is in range
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        if (!collected) {
            float dist = (float) Math.sqrt(Math.pow(x+16 - NewGameScreen.player.rX, 2) + Math.pow(y+16 - NewGameScreen.player.rY, 2));
            float transparency;
            if (dist >= 128f) {
                transparency = 0f;
            }
            else if (dist < 0) {
                transparency = 1f;
            }
            else {
                transparency = 1f - ((dist) / 128f);
            }
            sprite.setPosition(x, y);
            sprite.setColor(1,1,1,transparency);
            sprite.draw(batch);
        }
    }
}
