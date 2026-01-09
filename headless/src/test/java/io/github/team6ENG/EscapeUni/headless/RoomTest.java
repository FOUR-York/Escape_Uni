package io.github.team6ENG.EscapeUni.headless;

import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

public class RoomTest extends AbstractHeadlessGdxTest {
    Room room;

    /**
     * Testing that creating a room works.
     */
    @Test
    public void testCreateRoom() {
        Main.activeSpritePath = "sprites/femaleSprite.png";
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
}
