package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Keycard extends RoomObject {
    private LightSource lightSource;
    private int gridX, gridY;
    private boolean collected = false;

    public Keycard(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);

        lightSource = LightSource.createLightSource(x, y, 80f);
    }

    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                NewGameScreen.infoMsg("Keycard collected.");
                NewGameScreen.room.openDoors();
                collected = true;
                Main.score += 100;
                NewGameScreen.player.scoreEarnedThisRoom += 100;
                if (!Main.playerFoundKeycardOnce) {
                    Main.playerFoundKeycardOnce = true;
                    Main.foundPositiveEvents++;
                }
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
