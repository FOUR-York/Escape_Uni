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
 * Represents a single light source
 */
public class LightSource {
    private static LightSource[] lightSources;
    private static int lights;
    public static ShaderProgram shaderProgram;
    public static boolean lightsOff;

    float circleX;
    float circleY;
    float radius;
    boolean isVisible;

    /**
     * Initialises a new light source
     * @param circleX
     * @param circleY
     * @param radius
     */
    protected LightSource(float circleX, float circleY, float radius) {
        this.circleX = circleX;
        this.circleY = circleY;
        this.radius = radius;
        isVisible = true;
    }

    /**
     * reset static lighting data, load shaders
     */
    public static void initialiseLighting() {
        lightSources = new LightSource[10];
        lights = 0;
        lightsOff = false;
        // screen shader
        String vertexShader = Gdx.files.internal("shaders/sc_lighting_vert.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/sc_lighting_frag.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        if (!shaderProgram.isCompiled()) {
            System.out.print(shaderProgram.getLog());
        }
        ShaderProgram.pedantic = false;
    }

    /**
     * Update logic function to be called each frame
     * Creates an array of floats to pack the data into the shader uniform
     * @param delta
     */
    public static void update(float delta) {
        float[] v = new float[3*lightSources.length];
        for (int i = 0; i < lights; i++) {
            v[i*3] =  lightSources[i].circleX;
            v[i*3 + 1] = lightSources[i].circleY;
            v[i*3 + 2] = lightSources[i].radius;
        }
        shaderProgram.bind();
        shaderProgram.setUniformi("u_active", lightsOff? 1:0);
        shaderProgram.setUniformi("u_length", lights);
        shaderProgram.setUniform3fv("u_vecs", v, 0, 10*3);
    }

    /**
     * Creates a new light source
     * Adds the light source to the static active array
     * Returns the light source for dynamic modification
     * @param circleX
     * @param circleY
     * @param radius
     * @return
     */
    public static LightSource createLightSource(float circleX, float circleY, float radius){
       LightSource lightSource  = new LightSource(circleX, circleY, radius);
       lightSources[lights] = lightSource;
       lights++;
       return lightSource;
    }
}
