package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.team6ENG.EscapeUni.Main;
import io.github.team6ENG.EscapeUni.NewGameScreen;
import io.github.team6ENG.EscapeUni.ShiftTile;
import org.junit.jupiter.api.Test;

public class ShiftTileTest extends AbstractHeadlessGdxTest {
    ShiftTile testShiftTile;

    /**
     * Testing that creating a Shift Tile works.
     */
    @Test
    public void createShiftTileTest() {
        JsonValue mapJson = new JsonReader().parse(Gdx.files.internal( "classRoom.json"));
        Texture shiftTex = new Texture(Gdx.files.internal(mapJson.get("shiftTex").asString()));

        testShiftTile = new ShiftTile(shiftTex, 0, 0, 1);

        NewGameScreen.nextRoom = "classRoom.json";
        Main.activeSpritePath = "sprites/femaleSprite.png";
        NewGameScreen.start();
    }

    /**
     * Testing the update(delta) function.
     */
    @Test
    public void testUpdate() {
        createShiftTileTest();

        testShiftTile.update(1/60f);
    }
}
