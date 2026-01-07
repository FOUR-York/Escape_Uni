package io.github.team6ENG.EscapeUni;

/*
    * helper class written by dlb
 */
public class GridObject {
    public enum TYPE {
        NONE,
        SOLID,
        PUSH,
        PLAYER,
    }
    public TYPE type;
    private int gridX, gridY;
    private GridObject(int x, int y, TYPE type) {
        this.type = type;
        gridX = x;
        gridY = y;
    }

    public static int push(GridObject[] grid, int gridW, int gridH, int x, int y, int dir, int force) {
        int idx = x + (gridH - y - 1) * gridW;
        if (idx < 0 || idx >= grid.length) {
            NewGameScreen.errorMsg("Cannot push GridObject to null Grid position");
            return 0;
        }
        if (force < 0) {
            NewGameScreen.errorMsg("Force too low to push GridObject");
            return 0;
        }
        switch (grid[idx].type) {
            case PLAYER:
            case PUSH: {
                int nX = x, nY = y;
                switch (dir) {
                    case 0:
                        nY += 1;
                        break;
                    case 1:
                        nX -= 1;
                        break;
                    case 2:
                        nY -= 1;
                        break;
                    case 3:
                        nX += 1;
                        break;
                }
                if (push(grid, gridW, gridH, nX, nY, dir, force-1) > 0) {
                    move(grid, gridW, gridH, x, y, nX, nY);
                    return 1;
                } else {
                    return 0;
                }
            }
            case SOLID:
                NewGameScreen.infoMsg("Cannot push GridObject to non-empty Grid position");
                return 0;
            case NONE:
                return 1;
            default:
                NewGameScreen.errorMsg("Cannot push GridObject to null Grid position");
                return 0;
        }
    }

    public static void move(GridObject[] grid, int gridW, int gridH, int sX, int sY, int dX, int dY) {
        // swap gridobjects
        int source = sX + (gridH - sY - 1) * gridW;
        int dest = dX + (gridH - dY - 1) * gridW;
        if (grid[dest].type == TYPE.NONE) {
            GridObject tmp =  grid[dest];
            grid[dest] = grid[source];
            grid[source] = tmp;
            grid[source].gridX = sX;
            grid[source].gridY = sY;
            grid[dest].gridX = dX;
            grid[dest].gridY = dY;
        }
        else {
            NewGameScreen.errorMsg("Cannot move GridObject to non-empty Grid position");
        }
    }

    public static GridObject getAt(GridObject[] grid, int gridW, int gridH, int sX, int sY) {
        int idx = sX + (gridH - sY - 1) * gridW;
        if (idx < grid.length) {
            return grid[idx];
        } else {
            NewGameScreen.errorMsg("Cannot get Grid at index out of Grid size");
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public static GridObject[] createGrid(int gridW, int gridH) {
        GridObject[] grid = new GridObject[gridW*gridH];
        for (int i = 0; i < gridW; i++) {
            for (int j = 0; j < gridH; j++) {
                grid[i + (gridH-j-1)*gridW] = new GridObject(i, j, TYPE.NONE);
            }
        }
        return grid;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }
}
