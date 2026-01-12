package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ScorePickup extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;
    private boolean countScore;
    private int id;

    public ScorePickup(Texture texture, float x, float y, boolean countScore, int id) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
        this.countScore = countScore;
        this.id = id;
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

                NewGameScreen.room.collectCoin(id);

                if (countScore) {
                    Main.score += 25;
                    NewGameScreen.player.scoreEarnedThisRoom += 50;
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
