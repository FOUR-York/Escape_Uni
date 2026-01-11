package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ScorePickup extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;

    public ScorePickup(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                collected = true;

                if (!Main.playerGotScorePickupOnce) {
                    Main.playerGotScorePickupOnce = true;
                    Main.foundPositiveEvents++;
                }

                Main.score += 100;
                NewGameScreen.player.scoreEarnedThisRoom += 100;
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
