package io.github.team6ENG.EscapeUni;

/**
 * Projectile class
 * Projectiles are limited to orthogonal movement
 * Check for walls and other objects in range
 */
public class Projectile {
    float x, y;
    float speed;
    int dir;
    float radius;
    int id;

    /**
     * Creates and initialises a new projectile with a handler id
     * @param x
     * @param y
     * @param speed
     * @param dir
     * @param radius
     * @param id
     */
    public Projectile(float x, float y, float speed, int dir, float radius, int id) {
        this.x = x;
        this.y = y;
        this.speed = speed * 60;
        this.dir = dir;
        this.radius = radius;
        this.id = id;
    }

    /**
     * Projectile update logic function to be called each active frame
     * Collisions with player and GridObjects
     * @param delta
     */
    public void update(float delta) {
        int cellX = (int) x / NewGameScreen.tileWidth, cellY = (int) y / NewGameScreen.tileHeight;
        int currentX = cellX;
        int currentY = cellY;
        // find position of next cell in the direction of the projectile
        // update position
        switch (dir) {
            case 0:
                y += speed * delta;
                cellY += 1;
                break;
            case 1:
                x -= speed * delta;
                cellX -= 1;
                break;
            case 2:
                y -= speed * delta;
                cellY -= 1;
                break;
            case 3:
                x += speed * delta;
                cellX += 1;
                break;
        }
        // ensure boxes block projectiles
        if (NewGameScreen.room.roomCell(currentX, currentY) == GridObject.TYPE.PUSH) {
            NewGameScreen.room.removeProjectile(id);
        } else if (NewGameScreen.dist(x, y, NewGameScreen.player.rX, NewGameScreen.player.rY) < NewGameScreen.player.radius + radius) {
            // player collisions
            NewGameScreen.player.hit();
            NewGameScreen.room.removeProjectile(id);
        } else if (cellX < 0 || cellX > NewGameScreen.room.width - 1 ||
            cellY < 0 || cellY > NewGameScreen.room.height - 1 ||
            NewGameScreen.room.roomCell(cellX, cellY) == GridObject.TYPE.SOLID ||
            NewGameScreen.room.roomCell(cellX, cellY) == GridObject.TYPE.PUSH) {
            // collisions with room boundaries and GridObjects
            float dist = 1;
            // find distance from the closest cell edge in direction
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
            // remove projectile if it is in contact
            if (Math.abs(dist) < radius) {
                NewGameScreen.room.removeProjectile(id);
            }
        }
    }
}
