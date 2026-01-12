package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Room object that toggles lighting effects when collected
 */
public class LightSwitch extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;
    private final LightSource lightSource;

    /**
     * Initialise a new light switch
     * @param texture
     * @param x
     * @param y
     */
    public LightSwitch(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);

        // set a light source to switch location
        lightSource = LightSource.createLightSource(
            x+NewGameScreen.tileWidth/2f,
            y+NewGameScreen.tileHeight/2f,
            50f);
    }

    @Override
    /**
     * update function for each active frame
     * toggle lights if collected
     */
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                NewGameScreen.infoMsg("Lights toggled.");
                collected = true;

                if (!Main.playerGotLightSwitchOnce) {
                    Main.playerGotLightSwitchOnce = true;
                    Main.foundNegativeEvents++;
                }

                LightSource.lightsOff = !LightSource.lightsOff;
            }
        }
    }

    /**
     * Draw function for each active frame
     * draw only while uncollected
     * @param batch
     */
    @Override
    public void draw(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x, y);
        }
    }
}
