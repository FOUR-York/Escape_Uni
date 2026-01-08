package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ControlInverter extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;

    public ControlInverter(Texture texture, float x, float y) {
        super(texture, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

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
                }
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
