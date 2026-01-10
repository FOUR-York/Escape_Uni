package io.github.team6ENG.EscapeUni;

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
    public void update(float delta) {
        int cellX = (int) x / NewGameScreen.tileWidth, cellY = (int) y / NewGameScreen.tileHeight;
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
            NewGameScreen.room.removeProjectile(id);
        }
        if (cellX < 0 || cellX > NewGameScreen.room.width - 1 ||
            cellY < 0 || cellY > NewGameScreen.room.height - 1 ||
            NewGameScreen.room.roomCell(cellX, cellY) == GridObject.TYPE.SOLID ||
            NewGameScreen.room.roomCell(cellX, cellY) == GridObject.TYPE.PUSH) {
            float dist = 1;
            switch (dir) {
                case 0:
                    dist = cellY * NewGameScreen.tileHeight - y;
                    break;
                case 1:
                    dist = x - (cellX + 1) * NewGameScreen.tileWidth;
                    break;
                case 2:
                    dist = y - (cellY + 1) * NewGameScreen.tileHeight;
                    break;
                case 3:
                    dist = cellX * NewGameScreen.tileWidth - x;
                    break;
            }
            if (Math.abs(dist) < radius) {
                NewGameScreen.room.removeProjectile(id);
            }
        }
    }
}
