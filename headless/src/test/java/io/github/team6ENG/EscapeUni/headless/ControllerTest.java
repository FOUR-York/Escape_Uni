package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;
import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTest extends AbstractHeadlessGdxTest {
    Controller testController;
    Room room;

    /**
     * Creating a Controller object to test.
     */
    @Test
    public void createControllerTest() {
        room = mock(Room.class);
        room.grid = GridObject.createGrid(20, 15);
        room.width = 20;
        room.height = 15;

        Main.activeSpritePath = "sprites/femaleSprite.png";
        NewGameScreen.nextRoom = "classRoom.json";
        testController = new Controller(GridObject.getAt(room.grid, room.width, room.height, 1, 1), 32/4f);
    }

    /**
     * Testing the step function.
     */
    @Test
    public void testStep() {
        createControllerTest();

        // Case that powerupTimer > 0f.
        testController.powerupTimer = 100f;
        testController.step();

        // Case that powerUpTimer = 0f.
        testController.powerupTimer = 0f;
        testController.step();

        /*
         Checking that rX and rY are appropriately changed.

         lerp(x, y, z) works by smoothly transitioning between point x and y
         by the distance z.

         posX: 1 posY: 1
         rX: 48.0 rY: 48.0

         So rX = lerp(48.0, 48.0, 0.3f)
         So rY = lerp(48.0, 48.0, 0.3f);
        */
        assertEquals(MathUtils.lerp(48.0f, 48.0f, 0.3f), testController.rX);
        assertEquals(MathUtils.lerp(48.0f, 48.0f, 0.3f), testController.rY);

        // Case that powerupTimer < 0f.
        testController.powerupTimer = -10f;

        // Case that invisible = false.
        testController.invincible = false;
        testController.step();

        // Checking that powerupTimer = 0f.
        assertEquals(0f, testController.powerupTimer);

        // Case that invisible = true.
        testController.invinciblePowerup(1/60f);
        testController.powerupTimer = -10f;
        testController.step();

        // We want to check that invisible turns false.
        assertFalse(testController.invincible,
            "In Controller.step() when the powerUpTimer < 0f and " +
                "it is invisible, then invisible needs to be set ot false.");

        // Checking that powerupTimer = 0f.
        assertEquals(0f, testController.powerupTimer);
    }

    /**
     * Testing the hop(int dir) function.
     * dir means the direction of movement, with
     * North = 0, East = 3, South = 2, West = 1.
     */
    @Test
    public void testHop() {
        createControllerTest();
        // We need to call this to call NewGameScreen.start().
        testController.hit();

        // Testing Moving North - only thing that should be changed is FacingUp.
        testController.hop(0);
        assertTrue(testController.isMoving,
            "When hop is called, isMoving should be turned to true.");
        assertTrue(testController.isFacingUp,
            "When moving up (dir = 0), isFacingUp should be true.");
        assertFalse(testController.isMovingHorizontally,
            "When moving up (dir = 0), isMovingHorizontally should be unchanged (false).");
        assertFalse(testController.isFacingLeft,
            "When moving up (dir = 0), isFacingLeft should be unchanged (false).");
        // Now reversing this move.
        testController.hop(2);

        // Testing Moving South, then all the boolean variables should be false.
        testController.hop(2);
        assertFalse(testController.isFacingUp,
            "When moving down (dir = 2), isFacingUp should be unchanged (false).");
        assertFalse(testController.isMovingHorizontally,
            "When moving down (dir = 2), isMovingHorizontally should be unchanged (false).");
        assertFalse(testController.isFacingLeft,
            "When moving down (dir = 2), isFacingLeft should be unchanged (false).");

        // Testing Moving East - only thing changed is moving horizontally.
        testController.hop(3);
        assertFalse(testController.isFacingUp,
            "When moving right (dir = 3), isFacingUp should be unchanged (false).");
        assertTrue(testController.isMovingHorizontally,
            "When moving right (dir = 3), isMovingHorizontally should be true.");
        assertFalse(testController.isFacingLeft,
            "When moving right (dir = 3), isFacingLeft should be unchanged (false).");

        // Testing Moving West - then facingLeft is the only thing changed.
        testController.hop(1);
        assertFalse(testController.isFacingUp,
            "When moving left (dir = 1), isFacingUp should be unchanged (false).");
        assertTrue(testController.isMovingHorizontally,
            "When moving left (dir = 1), isMovingHorizontally should be true.");
        assertTrue(testController.isFacingLeft,
            "When moving left (dir = 1), isFacingLeft should be true.");

        // This is the default case in the switch. Nothing should change.
        testController.hop(5);
        assertFalse(testController.isFacingUp);
        assertTrue(testController.isMovingHorizontally);
        assertTrue(testController.isFacingLeft);
    }

    /**
     * Testing the hit function.
     * The sole purpose of this function is that if invisible = false,
     * then a newGameScreen is started. Otherwise, nothing.
     */
    @Test
    public void testHit() {
        createControllerTest();

        // case that invisible is false
        testController.hit();

        // case that Main.score = 0
        Main.score = 25;
        testController.hit();

        // case that Main.score > 0
        Main.score = 100;
        testController.hit();

        // case that invisible is true
        testController.invinciblePowerup(1/60f);
        testController.hit();
    }

    /**
     * Testing the updateSprite() function.
     */
    @Test
    public void testUpdateSprite() {
        createControllerTest();

        // Case that moveTime < 0f.
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(100f);
        testController.updateSprite();
        assertFalse(testController.isMoving,
            "When moveTime < 0f, then the isMoving should be false.");

        when(Gdx.graphics.getDeltaTime()).thenReturn(0f);
        testController.hit();

        // isFacingUp + -isMovingHorizontally
        testController.hop(0);
        testController.updateSprite();

        // isFacingUp + isMovingHorizontally + isFacingLeft
        testController.hop(1);
        testController.updateSprite();

        // isFacingUp + isMovingHorizontally + -isFacingLeft
        testController.hop(3);
        testController.updateSprite();

        // -isFacingUp + isMovingHorizontally + isFacingLeft
        testController.hop(2);
        testController.hop(1);
        testController.updateSprite();

        // -isFacingUp + isMovingHorizontally + -isFacingLeft
        testController.hop(3);
        testController.updateSprite();

        // -isFacingUp + -isMovingHorizontally
        testController.hop(2);
        testController.updateSprite();
    }
}
