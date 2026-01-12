package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * collectable RoomObject that inverts the players controls when collected
 */
public class ControlInverter extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;

    /**
     * Initialise the inverter
     * @param texture
     * @param x
     * @param y
     */
    public ControlInverter(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

    /**
     * Update logic function to be called each active frame
     * toggle inverting player controls when collected
     * @param delta
     */
    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                if (NewGameScreen.player.isInverted) {
                    NewGameScreen.infoMsg("Controls un-inverted.");
                    NewGameScreen.player.isInverted = false;
                    collected = true;
                }
                else {
                    NewGameScreen.infoMsg("Controls inverted.");
                    NewGameScreen.player.isInverted = true;
                    collected = true;

                    if (!Main.playerInvertedOnce) {
                        Main.playerInvertedOnce = true;
                        Main.foundNegativeEvents++;
                    }
                }
            }
        }
    }

    /**
     * Draw function to be called each active frame
     * Draw sprite if uncollected
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x, y);
        }
    }
}
