package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
/*
    * helper class written by dlb
 */
public class Room {
    public GridObject[] grid;
    public ArrayList<RoomObject> objects;
    public int width, height;
    public Texture texture;
    public Room(int width, int height) {
        this.texture = NewGameScreen.roomTex;
        this.width = width;
        this.height = height;
        objects = new ArrayList<>();
        grid = GridObject.createGrid(width, height);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width-1 ||  y == 0 || y == height-1) {
                    grid[y*width+x].type = GridObject.TYPE.SOLID;
                } else {
                    grid[y*width+x].type = GridObject.TYPE.NONE;
                }
            }
        }
    }

    public void addObject(RoomObject object) {
        objects.add(object);
    }
    public void draw(ShapeDrawer drawer, SpriteBatch batch) {
        batch.draw(texture, 0, 0);
    }
    public void dispose() {

    }
}
