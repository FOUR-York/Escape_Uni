package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Powerup extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;

    public Powerup(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                NewGameScreen.infoMsg("Powerup collected.");
                collected = true;

                if (!Main.playerGotPowerupOnce) {
                    Main.playerGotPowerupOnce = true;
                    Main.foundPositiveEvents++;
                }

                NewGameScreen.player.invinciblePowerup(10f);
                Main.score += 250;
            }
        }
    }

    @Override
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, x, y);
        }
    }
}
