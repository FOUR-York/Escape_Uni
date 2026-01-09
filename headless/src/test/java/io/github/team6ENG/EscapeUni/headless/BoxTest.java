package io.github.team6ENG.EscapeUni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import io.github.team6ENG.EscapeUni.*;

import org.junit.jupiter.api.Test;

public class BoxTest extends AbstractHeadlessGdxTest {
    Box box;
    @Test
    public void testBox() {
        String path = "classRoom.json";
        JsonValue mapJson = new JsonReader().parse(Gdx.files.internal(path));
        int[] dimensions = mapJson.get("dimensions").asIntArray();
        int width = dimensions[0];
        int height = dimensions[1];
        GridObject[] grid = GridObject.createGrid(width, height);

        Texture boxTex = new Texture(Gdx.files.internal(mapJson.get("boxTex").asString()));

        box = new Box(boxTex, GridObject.getAt(grid, width, height, 0, 0));

        box.update(1/60f);
    }
}
