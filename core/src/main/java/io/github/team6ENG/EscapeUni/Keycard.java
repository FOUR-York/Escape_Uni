package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Keycard extends RoomObject {
    private int gridX, gridY;
    private boolean collected = false;

    public Keycard(float x, float y) {
        super(NewGameScreen.keycardTex, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

    @Override
    public void update(float delta) {
        if (!collected) {
            if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
                NewGameScreen.infoMsg("Keycard collected.");
                collected = true;
                NewGameScreen.keycard = true;
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
