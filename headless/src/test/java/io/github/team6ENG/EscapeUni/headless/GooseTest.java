package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.team6ENG.EscapeUni.Goose;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GooseTest extends AbstractHeadlessGdxTest {
    Goose gooseTest;
    TiledMapTileLayer wallsLayer;

    /**
     * Creating a test Goose object.
     */
    @Test
    public void createGoose() {
        gooseTest = new Goose();
        gooseTest.setTileDimensions(8);

        wallsLayer = mock(TiledMapTileLayer.class);
        when(wallsLayer.getWidth()).thenReturn(200);
        when(wallsLayer.getHeight()).thenReturn(200);

        gooseTest.setWallLayer(wallsLayer);
    }

    /**
     * Checking that the getter function for width and height work.
     */
    @Test
    public void testGetWidthAndHeight() {
        gooseTest = new Goose();
        gooseTest.currentGooseFrame = null;
        assertEquals(16f, gooseTest.getWidth(),
            "When the gooseTest's current frame is null, the width should return 16f.");
        assertEquals(16f, gooseTest.getHeight(),
            "When the gooseTest's current frame is null, the height should return 16f.");

        Texture texture = mock(Texture.class);
        gooseTest.currentGooseFrame = new TextureRegion(texture, 0, 0, 48, 64);
        assertEquals(48, gooseTest.getWidth(),
            "Goose.getWidth() should return 48.");
        assertEquals(64, gooseTest.getHeight(),
            "Goose.getHeight() should return 64.");
    }

    /**
     * Checking the attackMode() function.
     */
    @Test
    public void testAttackMode() {
        gooseTest = new Goose();
        gooseTest.attackMode();
    }


    /**
     * Checking that the Goose moves when the target (Player)
     * is in range.
     */
    @Test
    public void testMoveGooseWhenTargetInRange() {
        createGoose();

        // if goose isSleeping.
        gooseTest.isSleeping = true;
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

        gooseTest.isSleeping = false;

        // distance > idleDistance (=20)
        gooseTest.x = 25;
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

        // isPlayerMoving = true
        gooseTest.x = 0;
        gooseTest.moveGoose(1 / 60f, 0, 0, true, true);

        // isMovedAllowed(tileX, tileY) = false, when tileX < 0.
        gooseTest.x = -100;
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

        // isMovedAllowed(tileX, tileY) = true.
        gooseTest.x = 0;
        gooseTest.y = 0;
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

        gooseTest.moveGoose(1 / 60f, 0, 0, false, false);
    }

    /**
     * Checking that the Goose moves when the target is out of range.
     */
    @Test
    public void testMoveGooseWhenTargetOutOfRange() {
        createGoose();

        // x > followX, when x = 0.

        // isMoveAllowed(tileX, tileY) = false when tileX < 0..
        gooseTest.x = -100;
        gooseTest.moveGoose(1 / 60f, -150, 0, true, true);

        // iaMoveAllowed(tileX, tileY) = true.
        gooseTest.x = 0;
        gooseTest.moveGoose(1 / 60f, -150, 0, true, true);

    }

    /**
     * Checking the moveAllowed() function.
     */
    @Test
    public void testMoveAllowed() {
        createGoose();

        /*
        The for-loop completed the following tests in order:
        1) tileX < 0,
        2) tileY < 0,
        3) tileX >= wallsLayer.getWidth(),
        4) tileY >= wallsLayer.getHeight()
        5) cell == null.
         */

        int[] xCoords = {-100, 0, 1600, 0, 0};
        int[] yCoords = {0, -100, 0, 1600, 0};
        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(null);
        gooseTest.setWallLayer(wallsLayer);

        for (int index = 0; index < xCoords.length; index++) {
            gooseTest.x = xCoords[index];
            gooseTest.y = yCoords[index];

            gooseTest.moveGoose(1 / 60f, 0, 0, false, true);
        }

        // cell.getTitle == null.
        TiledMapTileLayer.Cell mockCell = mock(TiledMapTileLayer.Cell.class);

        when(mockCell.getTile()).thenReturn(null);
        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);

        gooseTest.setWallLayer(wallsLayer);
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

        // thest that both of these are false. and cell.getID = mapWallsId,
        TiledMapTile tile = mock(TiledMapTile.class);
        when(mockCell.getTile()).thenReturn(tile);
        when(mockCell.getTile().getId()).thenReturn(0);
        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

        // thest that both of these are false. and cell.getID != mapWallsId,
        when(mockCell.getTile().getId()).thenReturn(1);
        gooseTest.moveGoose(1 / 60f, 0, 0, false, true);

    }

    /**
     * Checking the loadBabyGoose function.
     */
    @Test
    public void testLoadBabyGoose() {
        createGoose();

        gooseTest.loadBabyGoose(5);
        gooseTest.loadBabyGoose(4);
    }

    /**
     * Checking the nextRunLocation function.
     */
    @Test
    public void testNextRunLocation() {
        createGoose();

        int[] xCoords = {0, 697, 697, 345, 605, 547};
        int[] yCoords = {0, 0, 396, 295, 154, 49};

        for (int index = 0; index < xCoords.length; index++) {
            gooseTest.x = xCoords[index];
            gooseTest.y = yCoords[index];

            gooseTest.nextRunLocation();
        }
    }
}
