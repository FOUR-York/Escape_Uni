package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;

public class Keycard extends RoomObject {
    private int gridX, gridY;

    public Keycard(float x, float y) {
        super(NewGameScreen.keycardTex, x, y);
        gridX = (int) (x/NewGameScreen.tileWidth);
        gridY = (int) (y/NewGameScreen.tileHeight);
    }

    @Override
    public void step(float delta) {
        if (NewGameScreen.player.gridInstance.getGridX() == gridX && NewGameScreen.player.gridInstance.getGridY() == gridY) {
            System.out.println("Keycard collision");
        }
    }
}
