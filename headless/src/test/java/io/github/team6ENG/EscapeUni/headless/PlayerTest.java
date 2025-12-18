package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.team6ENG.EscapeUni.AudioManager;
import io.github.team6ENG.EscapeUni.Main;
import io.github.team6ENG.EscapeUni.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// Could make the input tests easier with an array such as
// [Input.Keys.S, Input.Keys.Down....

public class PlayerTest extends AbstractHeadlessGdxTest {
    private Input mockInput;
    private Player playerTest;
    private float[] coords;
    TiledMapTileLayer wallsLayer;

    public void SetPlayerUp() {
        Main main = mock(Main.class);
        main.activeSpritePath = "sprites/maleSprite.png";
        main.viewport = new FitViewport(800, 450);

        AudioManager audioManager = mock(AudioManager.class);

        mockInput = mock(Input.class);
        Gdx.input = mockInput;

        playerTest = new Player(main, audioManager, 3, 2);

        playerTest.setTileDimensions(8);

        wallsLayer = mock(TiledMapTileLayer.class);
        when(wallsLayer.getWidth()).thenReturn(200);
        when(wallsLayer.getHeight()).thenReturn(200);

        playerTest.setWallLayer(wallsLayer);
    }

    public void getCoords() {
        coords = new float[]{playerTest.sprite.getX(), playerTest.sprite.getY()};
    }

    public void resetControls(boolean KeyValue) {
        when(mockInput.isKeyPressed(Input.Keys.D)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.RIGHT)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.A)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.LEFT)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.W)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.UP)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.S)).thenReturn(KeyValue);
        when(mockInput.isKeyPressed(Input.Keys.DOWN)).thenReturn(KeyValue);
    }


    /**
     * Testing the result when the player receives input,
     * using the mockito framework.
     */
    @Test
    public void testNoPlayerInput() {
        SetPlayerUp();

        // TESTING ALL KEYS ARE NEGATIVE
        getCoords();

        playerTest.handleInput(1/60f, 1.0f);

        assertEquals(coords[1], playerTest.sprite.getY(),
            "No input changed the y coordinate");
        assertEquals(coords[0], playerTest.sprite.getX(),
            "No input changed the x coordinate");
    }

    @Test
    public void testPlayerInput() {
        SetPlayerUp();

        int[] keyboardInputs = {Input.Keys.W, Input.Keys.UP, Input.Keys.S, Input.Keys.DOWN,
            Input.Keys.D, Input.Keys.RIGHT, Input.Keys.A, Input.Keys.LEFT};

        for (int index = 0; index < keyboardInputs.length; index += 2) {
            float changeX = 0;
            float changeY = 0;

            // The player moves up 1 place along y-axis.
            if (index == 0) { changeY = (1.25f * 1.0f * 60f * (1 / 60f)); }
            // The player moves up 1 place down the y-axis.
            if (index == 2) { changeY = -(1.25f * 1.0f * 60f * (1 / 60f)); }
            // The player moves up 1 place along x-axis.
            if (index == 4) { changeX = (1.25f * 1.0f * 60f * (1 / 60f)); }
            // The player moves up 1 place down along x-axis.
            if (index == 6) { changeX = -(1.25f * 1.0f * 60f * (1 / 60f)); }

            when(mockInput.isKeyPressed(keyboardInputs[index])).thenReturn(true);
            when(mockInput.isKeyPressed(keyboardInputs[index + 1])).thenReturn(true);

            getCoords();

            playerTest.handleInput(1 / 60f, 1.0f);

            assertEquals((coords[0] + changeX), playerTest.sprite.getX(),
                "The x-coordinate did not change as expected when " +  Input.Keys.toString(keyboardInputs[index]) + " and " + Input.Keys.toString(keyboardInputs[index]) + " was pressed.");
            assertEquals((coords[1] + changeY), playerTest.sprite.getY(),
                "The y-coordinate did not change as expected when " +  Input.Keys.toString(keyboardInputs[index]) + " and " +  Input.Keys.toString(keyboardInputs[index + 1]) + " was pressed.");

            getCoords();
            when(mockInput.isKeyPressed(keyboardInputs[index + 1])).thenReturn(false);

            playerTest.handleInput(1 / 60f, 1.0f);

            assertEquals((coords[0] + changeX), playerTest.sprite.getX(),
                "The x-coordinate did not change as expected when only " +  Input.Keys.toString(keyboardInputs[index]) + " was pressed.");
            assertEquals((coords[1] + changeY), playerTest.sprite.getY(),
                "The y-coordinate did not change as expected when only " +  Input.Keys.toString(keyboardInputs[index]) + " was pressed.");

            getCoords();
            when(mockInput.isKeyPressed(keyboardInputs[index])).thenReturn(false);
            when(mockInput.isKeyPressed(keyboardInputs[index + 1])).thenReturn(true);

            playerTest.handleInput(1 / 60f, 1.0f);

            assertEquals((coords[0] + changeX), playerTest.sprite.getX(),
                "The x-coordinate did not change as expected when only " +  Input.Keys.toString(keyboardInputs[index + 1]) + " was pressed.");
            assertEquals((coords[1] + changeY), playerTest.sprite.getY(),
                "The y-coordinate did not change as expected when only " +  Input.Keys.toString(keyboardInputs[index + 1]) + " was pressed.");

            resetControls(false);
        }
    }

    @Test
    public void testPlayerMovementOutOfBounds() {
        SetPlayerUp();

        Sprite sprite = mock(Sprite.class);
        sprite.setBounds(sprite.getX(), sprite.getY(), 48, 64);
        playerTest.sprite = sprite;

        float[] Coordinates = {3168.0F, -32.0F, 3160.0F, -24.0F};
        int[] Inputs = {Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT, Input.Keys.LEFT,};

        for (int i = 0; i < 4; i++) {
            if (i < 2) {when(playerTest.sprite.getY()).thenReturn(Coordinates[i]); }
            else {when(playerTest.sprite.getX()).thenReturn(Coordinates[i]); }
            getCoords();

            when(mockInput.isKeyPressed(Inputs[i])).thenReturn(true);
            playerTest.handleInput(1/60f, 1.0f);

            assertEquals(coords[0], playerTest.sprite.getX(),
                "Pressing " + Inputs[i] + " when out of boundary changed the x coordinate");
            assertEquals(coords[1], playerTest.sprite.getY(),
                "Pressing " + Inputs[i] + " when out of boundary changed the y coordinate");

            when(mockInput.isKeyPressed(Inputs[i])).thenReturn(false);
        }
    }

    @Test
    public void testPlayerInputSpeed() {
        SetPlayerUp();
        getCoords();

        playerTest.inWater = true;
        playerTest.handleInput(1/60f, 1.0f);
    }

    @Test
    public void testPlayerInputCells () {
        SetPlayerUp();

        // case that the cell is null
        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(null);

        playerTest.setWallLayer(wallsLayer);
        // All movement controls will be true -
        // meaning that every if statement should be called.
        resetControls(true);
        playerTest.handleInput(1/60f, 1.0f);

        // case that the cell ID is not a map wallsId (which is 1)

        TiledMapTileLayer.Cell mockCell = mock(TiledMapTileLayer.Cell.class);
        TiledMapTile mockTile = mock(TiledMapTile.class);
        when(mockCell.getTile()).thenReturn(mockTile);
        when(mockTile.getId()).thenReturn(0);

        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);
        playerTest.handleInput(1/60f, 1.0f);

        // case that the cellID is a map wallsID (1).
        when(mockTile.getId()).thenReturn(1);

        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);
        playerTest.handleInput(1/60f, 1.0f);

        // case that the cellID is map langwithBarriersId (3).
        when(mockTile.getId()).thenReturn(3);

        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);
        playerTest.handleInput(1/60f, 1.0f);

        // case that hasEnteredLangwith is true.
        when(mockTile.getId()).thenReturn(3);

        playerTest.hasEnteredLangwith = true;
        playerTest.handleInput(1/60f, 1.0f);

        // case that hasEnteredLangwith is false.
        playerTest.hasEnteredLangwith = false;
        playerTest.handleInput(1/60f, 1.0f);

    }

    // Maybe simplify this code.
    @Test
    public void testUpdatePlayer() {
        SetPlayerUp();

        // testing the instance when the player is not   moving.
        playerTest.isMoving = false;
        playerTest.updatePlayer(1 / 60f);

        // testing the instance when the player is moving.
        playerTest.isMoving = true;

        playerTest.isFacingUp = false;
        playerTest.isMovingHorizontally = false;

        playerTest.updatePlayer(1 / 60f);

        playerTest.isMovingHorizontally = true;
        playerTest.isFacingLeft = false;
        playerTest.updatePlayer(1 / 60f);


        playerTest.isFacingLeft = true;
        playerTest.updatePlayer(1 / 60f);

        // testing the instance where the player is facing up.
        playerTest.isFacingUp = true;

        playerTest.isMovingHorizontally = false;
        playerTest.updatePlayer(1 / 60f);

        // testing the instance where the player is moving horizontally up.
        playerTest.isMovingHorizontally = true;

        playerTest.isFacingLeft = false;
        playerTest.updatePlayer(1 / 60f);

        playerTest.isFacingLeft = true;
        playerTest.updatePlayer(1 / 60f);
    }

    @Test
    public void testPlayerDispose() {
        SetPlayerUp();

        playerTest.dispose();
    }

    @Test
    public void testPlayerInBounds() {
        SetPlayerUp();

        // keepPlayerInBounds is called in handleInput()
        Sprite sprite = new Sprite();
        // Force both the final conditionals to be false.
        sprite.setBounds(6352.0F, 3536.0F, 48F, 64F);

        playerTest.sprite = sprite;
        playerTest.handleInput(1/60f, 1.0f);

        // Force both the final conditionals to be true.
        sprite.setBounds(6400.0F, 3600, 48F, 64F);

        playerTest.sprite = sprite;
        playerTest.handleInput(1/60f, 1.0f);
    }

    @Test
    public void testPlayerCheckIfInWater() {
        SetPlayerUp();

        when(mockInput.isKeyPressed(Input.Keys.W)).thenReturn(true);

        // in the case that cell = null, then cell.getTile == null
        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(null);
        playerTest.handleInput(1/60f, 1.0f);

        // in the case that cell != null.
        TiledMapTileLayer.Cell mockCell = mock(TiledMapTileLayer.Cell.class);
        TiledMapTile mockTile = mock(TiledMapTile.class);
        when(wallsLayer.getCell(anyInt(), anyInt())).thenReturn(mockCell);
        when(mockCell.getTile()).thenReturn(mockTile);

        // the case that the cell.getTile().getID() != mapWaterId ( = 2 )
        when(mockTile.getId()).thenReturn(0);
        playerTest.handleInput(1/60f, 1.0f);

        // the case that the cell.getTile().getID() = mapWaterId ( = 2 )
        when(mockTile.getId()).thenReturn(2);
        playerTest.handleInput(1/60f, 1.0f);
    }
}
