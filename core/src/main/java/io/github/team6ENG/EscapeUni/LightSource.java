package io.github.team6ENG.EscapeUni;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

import static java.lang.Math.sin;

/**
 * Stores light sources and renders a dark overlay with
 * light sources (circles) to simulate 2D lighting effects.
 *
 */
/**
 * Represents a single light source
 */
public class LightSource {
    private static LightSource[] lights;
    private static ShaderProgram shaderProgram;

    float circleX;
    float circleY;
    Color colour;
    float radius;
    boolean isVisible;

    /**
     * Initialises single light source
     * @param circleX
     * @param circleY
     */
    protected LightSource(float circleX, float circleY){
        this.circleX = circleX;
        this.circleY = circleY;
        isVisible = true;
    }

    public static void initialiseLighting(SpriteBatch batch) {
        lights = new LightSource[1];
//        // screen shader
//        String vertexShader = Gdx.files.internal("shaders/sc_lighting_vert.glsl").readString();
//        String fragmentShader = Gdx.files.internal("shaders/sc_lighting_frag.glsl").readString();
//        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
//        if (!shaderProgram.isCompiled()) {
//            System.out.print(shaderProgram.getLog());
//        }
//        ShaderProgram.pedantic = false;
//
//        batch.setShader(shaderProgram);
    }

    public static void update(float delta) {
//        float[] v = new float[2*lights.length];
//        for (int i = 0; i < lights.length; i++) {
//            v[i*2] =  lights[i].circleX;
//            v[i*2 + 1] = lights[i].circleY;
//        }
        shaderProgram.bind();
        shaderProgram.setUniformf("pos", new Vector2(lights[0].circleX, lights[0].circleY));
    }

    public static LightSource createLightSource(float circleX, float circleY){
       LightSource lightSource  = new LightSource(circleX, circleY);
       lights[0] = lightSource;
       return lightSource;
    }
}

//    /**
//     * create new light source and add to list of lights
//     * @param lightName
//     * @param circleX
//     * @param circleY
//     * @param colour
//     * @param radius
//     */
//    public void addLightSource(String lightName, float circleX, float circleY, Color colour, int radius){
//        lights.put(lightName, new LightSource(circleX, circleY, colour, radius));
//    }
//
//    /**
//     * Remove a light source from lights
//     * @param lightName
//     */
//    public void removeLightSource(String lightName){
//        lights.remove(lightName);
//    }
//    /**
//     * Reset list of lights
//     */
//    public void clearLightSources(){
//        lights.clear();
//    }
//
//    /**
//     * Reposition light source
//     */
//    public void update(String lightName, float circleX, float circleY){
//        // send light source data to uniform
//    }

//    /**
//     * Adjust radius of a light source
//     * @param lightName
//     * @param radius
//     */
//    public void adjustRadius(String lightName, int radius){
//        lights.get(lightName).radius = radius;
//    }
//
//    /**
//     * Set visibility of light source
//     * @param lightName
//     * @param isVisible
//     */
//    public void isVisible(String lightName, boolean isVisible){
//        lights.get(lightName).isVisible = isVisible;
//    }
//
//    /**
//     * Renders the lighting system:
//     * 1. Draws a dark overlay
//     * 2. Adds transparent circles
//     *
//     * @param camera The camera
//     * @param mapWidth Dimensions of world map for drawing darkness
//     * @param mapHeight Dimensions of world map for drawing darkness
//     *
//     * @return Texture representing the darkness and lights
//     */
//
//    public Texture render(int mapWidth, int mapHeight) {
//        frameBuffer.begin();
//
//        Pixmap pixmap = new Pixmap(mapWidth,mapHeight, Pixmap.Format.RGBA8888);
//
//        // Fill entire pixmap with semi-transparent black
//        pixmap.setColor(0f, 0f, 0f, 0.9f);
//        pixmap.fill();
//
//        pixmap.setBlending(Pixmap.Blending.None);
//
//        for(String l : lights.keySet()) {
//            if(lights.get(l).isVisible) {
//                pixmap.setColor(lights.get(l).colour);
//                pixmap.fillCircle((int) (lights.get(l).circleX), mapHeight - (int) (lights.get(l).circleY), lights.get(l).radius);
//            }
//        }
//        lightTexture.draw(pixmap, 0, 0);
//
//
//        pixmap.dispose();
//        frameBuffer.end();
//
//        return lightTexture;
//
//    }

//    public void dispose(){
//
//    }
//
//
//}
