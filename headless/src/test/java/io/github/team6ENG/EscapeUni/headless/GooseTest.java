package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.team6ENG.EscapeUni.Goose;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GooseTest extends AbstractHeadlessGdxTest {
    Goose gooseTest;
    TiledMapTileLayer wallsLayer;

    @Test
    public void testGoose() {
        gooseTest = new Goose();
    }

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

    @Test
    public void testAttackMode() {
        gooseTest = new Goose();
        gooseTest.attackMode();
    }

    public void createGoose() {
        gooseTest = new Goose();
        gooseTest.setTileDimensions(8);


        wallsLayer = mock(TiledMapTileLayer.class);
        when(wallsLayer.getWidth()).thenReturn(200);
        when(wallsLayer.getHeight()).thenReturn(200);

        gooseTest.setWallLayer(wallsLayer);
    }

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

    @Test
    public void testMoveGooseWhenTargetOutOfRange() {
        createGoose();

        // x > followX, when x = 0.

        // isMoveAllowed(tileX, tileY) = false when tileX < 0..
        gooseTest.x = -100;
        gooseTest.moveGoose(1 / 60f, -150, 0, true, true);

        System.out.println();

        // iaMoveAllowed(tileX, tileY) = true.
        gooseTest.x = 0;
        gooseTest.moveGoose(1 / 60f, -150, 0, true, true);

    }

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

    @Test
    public void testLoadBabyGoose() {
        createGoose();

        gooseTest.loadBabyGoose(5);
        gooseTest.loadBabyGoose(4);
    }

    @Test
    public void testNextRunLocation() {
        createGoose();
        // runPath =  Arrays.asList(new int[]{700, 400}, new int[]{340, 300}, new int[]{600, 150}, new int[]{550, 50});

        int[] xCoords = {0, 697, 697, 345, 605, 547};
        int[] yCoords = {0, 0, 396, 295, 154, 49};

        for (int index = 0; index < xCoords.length; index++) {
            gooseTest.x = xCoords[index];
            gooseTest.y = yCoords[index];

            gooseTest.nextRunLocation();
        }
    }
}
