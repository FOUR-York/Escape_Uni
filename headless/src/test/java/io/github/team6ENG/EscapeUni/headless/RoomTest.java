package io.github.team6ENG.EscapeUni.headless;

import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class RoomTest extends AbstractHeadlessGdxTest {
    Room room;

    /**
     * Testing that creating a room works.
     */
    @Test
    public void testCreateRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        NewGameScreen.nextRoom = "classRoom.json";
        Main.activeSpritePath = "sprites/femaleSprite.png";
        NewGameScreen.start();

        room = new Room("classRoom.json");
    }

    /**
     * Testing the dispose() method.
     */
    @Test
    public void testDispose() {
        testCreateRoom();

        room.dispose();
    }

    /**
     * Testing the roomCell function.
     */
    @Test
    public void testRoomCell() {
        testCreateRoom();

        // Case that x < 0.
        assertEquals(GridObject.TYPE.NONE, room.roomCell(-10, 0));

        // Case that x > width - 1.
        assertEquals(GridObject.TYPE.NONE, room.roomCell(room.width, 0));

        // Case that y < 0.
        assertEquals(GridObject.TYPE.NONE, room.roomCell(0, -10));

        // Case that y > height - 1.
        assertEquals(GridObject.TYPE.NONE, room.roomCell(0, room.height));

        // Case that all conditions are met.
        int x = room.width - 2,  y = room.height - 2;
        assertEquals(room.grid[x+(room.height - 1 - y) * room.width].type, room.roomCell(x, y));
    }

    /**
     * Testing the addObject() function.
     */
    @Test
    public void testAddObject() {
        testCreateRoom();
        RoomObject testObject = mock(RoomObject.class);

        ArrayList<RoomObject> expectedObjects = new ArrayList<>();
        room.objects.clear();
        room.addObject(testObject);

        expectedObjects.add(testObject);

        assertEquals(expectedObjects, room.objects);
    }

    /**
     * Testing the updateObjects() function.
     */
    @Test
    public void testUpdateObject() {
        testCreateRoom();

        room.updateObjects(1/60f);

        room.addObject(null);
        room.updateObjects(1/60f);
    }

    /**
     * Testing the loadRoom() function.
     * Loading the storageRoom.
     */
    @Test
    public void testLoadStorageRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        new Room("storageRoom.json");
    }

    /**
     * Loading the TurretBoxRoom.
     */
    @Test
    public void testLoadTurretBoxRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        new Room("turretBoxRoom.json");
    }

    /**
     * Loading the Outside.
     */
    @Test
    public void testLoadOutside() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        new Room("outside.json");
    }

    /**
     * Loading the CorridorRoom.
     */
    @Test
    public void testLoadCorridorRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        new Room("corridorRoom.json");
    }

    /**
     * Loading the boxRoom.
     */
    @Test
    public void testLoadBoxRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        new Room("boxRoom.json");
    }

    /**
     * Testing the openDoors() function.
     */
    @Test
    public void testOpenDoors() {
        testCreateRoom();
        assertFalse(room.isKeycardCollected());

        room.openDoors();
        assertTrue(room.isKeycardCollected());
    }

    /**
     * Testing the spawnProjectiles and removeProjectiles function.
     */
    @Test
    public void testRemoveProjectiles() {
        testCreateRoom();
        room.spawnProjectile(0, 0, 4f, 2, 8f);

        room.removeProjectile(0);

        room.updateObjects(1f);
    }
}
