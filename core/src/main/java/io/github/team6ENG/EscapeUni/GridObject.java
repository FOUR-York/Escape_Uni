package io.github.team6ENG.EscapeUni;

/**
 * GridObject returns information about the grid and valid object actions based upon their types
 */
public class GridObject {
    public enum TYPE {
        NONE,
        SOLID,
        PUSH,
        CONTROLLER,
    }
    public TYPE type;
    private int gridX, gridY;

    /**
     * Create new GridObject
     * @param x
     * @param y
     * @param type
     */
    private GridObject(int x, int y, TYPE type) {
        this.type = type;
        gridX = x;
        gridY = y;
    }

    /**
     * Recursively pushes up to "force" consecutive objects in the grid and in the direction specified
     * GridObject.TYPE.SOLID blocks any pushes, as does a null grid entry
     * @param grid
     * @param gridW
     * @param gridH
     * @param x
     * @param y
     * @param dir
     * @param force
     * @return
     */
    public static int push(GridObject[] grid, int gridW, int gridH, int x, int y, int dir, int force) {
        int idx = x + (gridH - y - 1) * gridW;
        if (idx < 0 || idx >= grid.length) {
            return 0;
        }
        if (force < 0) {
            NewGameScreen.infoMsg("Force too low to push GridObject");
            return 0;
        }
        switch (grid[idx].type) {
            case CONTROLLER:
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
            case NONE:
                return 1;
            case SOLID:
            default:
                return 0;
        }
    }

    /**
     * Moves an instance of gridObject within a grid.
     * The instance can only be moved to an empty grid position.
     * @param grid
     * @param gridW
     * @param gridH
     * @param sX
     * @param sY
     * @param dX
     * @param dY
     */
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

    /**
     * Returns a GridObject within a grid according to its grid coords
     * @param grid
     * @param gridW
     * @param gridH
     * @param sX
     * @param sY
     * @return
     */
    public static GridObject getAt(GridObject[] grid, int gridW, int gridH, int sX, int sY) {
        int idx = sX + (gridH - sY - 1) * gridW;
        if (idx < grid.length) {
            return grid[idx];
        } else {
            NewGameScreen.errorMsg("Cannot get Grid at index out of Grid size");
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Returns a new grid (array of GridObjects) with the specified width and height
     * @param gridW
     * @param gridH
     * @return
     */
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
