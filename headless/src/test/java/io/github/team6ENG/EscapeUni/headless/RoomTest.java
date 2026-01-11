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
     */
    /*@Test
    public void testLoadRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
        new Room("outside.json");
        new Room("boxRoom.json");
        new Room("classRoom.json");
        new Room("corridorRoom.json");
        new Room("outside.json");
        new Room("shiftPuzzle.json");
        new Room("storageRoom.json");
    }*/

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
     * Testing the spawnProjectiles function.
     */
    @Test
    public void testSpawnProjectiles() {
        testCreateRoom();
    }

    /**
     * Testing the removeProjectile function.
     */
    @Test
    public void testRemoveProjectiles() {
        testCreateRoom();
    }
}
