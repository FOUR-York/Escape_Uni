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
    private static LightSource[] lightSources;
    private static int lights;
    public static ShaderProgram shaderProgram;
    public static boolean lightsOff;

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
    protected LightSource(float circleX, float circleY, float radius) {
        this.circleX = circleX;
        this.circleY = circleY;
        this.radius = radius;
        isVisible = true;
    }

    public static void initialiseLighting() {
        lightSources = new LightSource[20];
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

    public static void update(float delta) {
        float[] v = new float[3*lightSources.length];
        for (int i = 0; i < lights; i++) {
            v[i*3] =  lightSources[i].circleX;
            v[i*3 + 1] = lightSources[i].circleY;
            v[i*3 + 2] = lightSources[i].radius;
        }
        shaderProgram.bind();
//        shaderProgram.setUniformf("pos", new Vector2(lightSources[0].circleX, lightSources[0].circleY));
        shaderProgram.setUniformi("u_active", lightsOff? 1:0);
        shaderProgram.setUniformi("u_length", lights);
        shaderProgram.setUniform3fv("u_vecs", v, 0, 10*3);
    }

    public static LightSource createLightSource(float circleX, float circleY, float radius){
       LightSource lightSource  = new LightSource(circleX, circleY, radius);
       lightSources[lights] = lightSource;
       lights++;
       return lightSource;
    }
}
