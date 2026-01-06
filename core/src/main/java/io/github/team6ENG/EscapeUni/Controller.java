package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.math.MathUtils;

// implement smooth transitions between tiles
/*
    * helper class written by dlb
    * player grid object
 */
public class Controller {
    GridObject gridInstance;
    float rX, rY;
    float radius;
    public Controller(GridObject gridInstance, float radius) {
        int x = gridInstance.getGridX(), y = gridInstance.getGridY();
        gridInstance.type = GridObject.TYPE.PLAYER;
        this.gridInstance = gridInstance;
        rX = x*NewGameScreen.tileWidth + NewGameScreen.tileWidth/2f;
        rY = y*NewGameScreen.tileHeight + NewGameScreen.tileHeight/2f;
        this.radius = radius;
    }
    public void step() {
        int posX = gridInstance.getGridX(), posY = gridInstance.getGridY();
        rX = MathUtils.lerp(rX, posX*NewGameScreen.tileWidth+NewGameScreen.tileWidth/2f, 0.3f);
        rY = MathUtils.lerp(rY, posY*NewGameScreen.tileHeight+NewGameScreen.tileHeight/2f, 0.3f);
    }
    public void hop(int dir) {
        int nX = gridInstance.getGridX(), nY = gridInstance.getGridY();
        GridObject.push(NewGameScreen.room.grid, NewGameScreen.room.width, NewGameScreen.room.height, nX, nY, dir);
    }
    public void hit() {
        // restart
        // TODO: implement restarting procedure
//        NewGameScreen.start();
    }
}
