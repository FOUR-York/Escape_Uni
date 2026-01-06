package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

/*
    * helper class written by dlb
 */
public class ShiftTile extends RoomObject {
    int dir;
    int posX, posY;
    final float delta = NewGameScreen.tileWidth/6f;
    public ShiftTile(int x, int y, int dir) {
        super(NewGameScreen.shiftTex, x*NewGameScreen.tileWidth, y*NewGameScreen.tileHeight);
        this.dir = dir;
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void step(float delta) {
        GridObject.push(NewGameScreen.room.grid, NewGameScreen.room.width, NewGameScreen.room.height, posX, posY, dir, 99);
    }

    @Override
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        float drawX = x, drawY = y;
        drawer.setColor(0.3f, 0.0f, 0.8f, 1.0f);
        drawer.rectangle(drawX+delta,
            drawY+delta,
            NewGameScreen.tileWidth-delta*2,
            NewGameScreen.tileHeight-delta*2);
        float m = NewGameScreen.tileHeight/2f;
        float w = NewGameScreen.tileHeight/4f;
        float sX = delta+delta/2f;
        float eX = NewGameScreen.tileWidth-sX;
        Vector2 topL = new Vector2(NewGameScreen.tileWidth/2f-sX, NewGameScreen.tileHeight/2f-(m+w/2f)).rotateDeg(dir*90f-90f);
        Vector2 bottomL = new Vector2(NewGameScreen.tileWidth/2f-sX, NewGameScreen.tileHeight/2f-(m-w/2f)).rotateDeg(dir*90f-90f);
        Vector2 r = new Vector2(NewGameScreen.tileWidth/2f-eX, NewGameScreen.tileHeight/2f-m).rotateDeg(dir*90f-90f);
        Vector2 draw = new Vector2(drawX+NewGameScreen.tileWidth/2f, drawY+NewGameScreen.tileHeight/2f);
        drawer.filledTriangle(draw.x+topL.x, draw.y+topL.y,
            draw.x+bottomL.x, draw.y+bottomL.y,
            draw.x+r.x, draw.y+r.y);
    }
}
