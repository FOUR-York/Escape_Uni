package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Keycard object
 * Opens doors when collected
 */
public class Keycard extends RoomObject {
    private LightSource lightSource;
    private int gridX, gridY;
    private boolean collected = false;

    /**
     * Initialise new keycard
     * @param texture
     * @param x
     * @param y
     */
    public Keycard(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);

        lightSource = LightSource.createLightSource(x, y, 80f);
    }

    /**
     * update logic function
     * if player in range, keycard is collected, doors are opened
     * player gains 100 score on the room's completion and only if the room has not already been completed
     * @param delta
     */
    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                NewGameScreen.infoMsg("Keycard collected.");
                NewGameScreen.room.openDoors();
                collected = true;
                if (!NewGameScreen.room.isVisited()) {
                    Main.score += 100;
                    NewGameScreen.player.scoreEarnedThisRoom += 100;
                }
                if (!Main.playerFoundKeycardOnce) {
                    Main.playerFoundKeycardOnce = true;
                    Main.foundPositiveEvents++;
                }
            }
        }
    }

    /**
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
