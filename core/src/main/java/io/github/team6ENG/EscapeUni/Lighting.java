package io.github.team6ENG.EscapeUni;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;

/**
 * Stores light sources and renders a dark overlay with
 * light sources (circles) to simulate 2D lighting effects.
 *
 */
public class Lighting {
    private final HashMap<String, LightSource> lights = new HashMap<>();
    private final FrameBuffer frameBuffer;
    private final Texture lightTexture;

    public Lighting(int mapWidth, int mapHeight) {
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, mapWidth, mapHeight, false);
        lightTexture = new Texture(mapWidth, mapHeight, Pixmap.Format.RGBA8888);
    }

    /**
     * Represents a single light source
     */
    static class LightSource{
        float circleX;
        float circleY;
        Color colour;
        int radius;
        boolean isVisible;

        /**
         * Initialises single light source
         * @param circleX: [need to add a description]
         * @param circleY: [need to add a description]
         * @param colour: [need to add a description]
         * @param radius: [need to add a description]
         */
        protected LightSource(float circleX, float circleY, Color colour, int radius){
            this.circleX = circleX;
            this.circleY = circleY;
            this.colour = colour;
            this.radius = radius;
            isVisible = true;
        }
    }

    /**
     * create new light source and add to list of lights
     * @param lightName: [need to add a description]
     * @param circleX: [need to add a description]
     * @param circleY: [need to add a description]
     * @param colour: [need to add a description]
     * @param radius: [need to add a description]
     */
    public void addLightSource(String lightName, float circleX, float circleY, Color colour, int radius){
        lights.put(lightName, new LightSource(circleX, circleY, colour, radius));
    }

    /**
     * Reposition light source
     */
    public void updateLightSource(String lightName, float circleX, float circleY){
        lights.get(lightName).circleX = circleX;
        lights.get(lightName).circleY = circleY;

    }

    /**
     * Set visibility of light source
     * @param lightName: [need to add a description]
     * @param isVisible: [need to add a description]
     */
    public void isVisible(String lightName, boolean isVisible){
        lights.get(lightName).isVisible = isVisible;
    }

    /**
     * Renders the lighting system:
     * 1. Draws a dark overlay
     * 2. Adds transparent circles
     *
     * @param mapWidth Dimensions of world map for drawing darkness
     * @param mapHeight Dimensions of world map for drawing darkness
     *
     * @return Texture representing the darkness and lights
     */

    public Texture render(int mapWidth, int mapHeight) {
        frameBuffer.begin();

        Pixmap pixmap = new Pixmap(mapWidth,mapHeight, Pixmap.Format.RGBA8888);

        // Fill entire pixmap with semi-transparent black
        pixmap.setColor(0f, 0f, 0f, 0.9f);
        pixmap.fill();

        pixmap.setBlending(Pixmap.Blending.None);

        for(String l : lights.keySet()) {
            if(lights.get(l).isVisible) {
                pixmap.setColor(lights.get(l).colour);
                pixmap.fillCircle((int) (lights.get(l).circleX), mapHeight - (int) (lights.get(l).circleY), lights.get(l).radius);
            }
        }
        lightTexture.draw(pixmap, 0, 0);


        pixmap.dispose();
        frameBuffer.end();

        return lightTexture;

    }

    public void dispose(){

    }


}
