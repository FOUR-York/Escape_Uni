package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LightSwitch extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;

    public LightSwitch(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                NewGameScreen.infoMsg("Lights toggled.");
                collected = true;

                if (!Main.playerGotLightSwitchOnce) {
                    Main.playerGotLightSwitchOnce = true;
                    Main.foundPositiveEvents++;
                }

                LightSource.lightsOff = !LightSource.lightsOff;
                Main.score += 250;
                NewGameScreen.player.scoreEarnedThisRoom += 250;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x, y);
        }
    }
}
