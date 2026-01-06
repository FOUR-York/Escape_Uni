package io.github.team6ENG.EscapeUni;

/*
    * helper class written by dlb
 */
public class Projectile {
    float x, y;
    float speed;
    int dir;
    float radius;
    int id;
    public Projectile(float x, float y, float speed, int dir, float radius, int id) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.dir = dir;
        this.radius = radius;
        this.id = id;
    }
    public void step() {
        int cellX = (int)x/NewGameScreen.tileWidth, cellY = (int)y/NewGameScreen.tileHeight;
        switch (dir) {
            case 0:
                y += speed;
                cellY += 1;
                break;
            case 1:
                x -= speed;
                cellX -= 1;
                break;
            case 2:
                y -= speed;
                cellY -= 1;
                break;
            case 3:
                x += speed;
                cellX += 1;
                break;
        }
        if (NewGameScreen.dist(x, y, NewGameScreen.player.rX, NewGameScreen.player.rY) < NewGameScreen.player.radius + radius) {
            NewGameScreen.player.hit();
        }
        if (NewGameScreen.roomCell(cellX, cellY) == GridObject.TYPE.SOLID) {
            float dist = 1;
            switch(dir) {
                case 0:
                    dist = cellY*NewGameScreen.tileHeight-y;
                    break;
                case 1:
                    dist = x-(cellX+1)*NewGameScreen.tileWidth;
                    break;
                case 2:
                    dist = y-(cellY+1)*NewGameScreen.tileHeight;
                    break;
                case 3:
                    dist = cellX*NewGameScreen.tileWidth-x;
                    break;
            }
            if (Math.abs(dist) < radius) {
                NewGameScreen.removeProjectile(id);
            }
        }
    }
}
