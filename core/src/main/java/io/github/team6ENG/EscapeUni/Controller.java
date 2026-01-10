package io.github.team6ENG.EscapeUni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;

// implement smooth transitions between tiles
public class Controller extends SpriteAnimations {
    GridObject gridInstance;
    LightSource lightSource;

    float rX, rY;
    float radius;

    public boolean isInverted = false;

    public boolean invincible = false;
    public float powerupTimer = 0f;

    private float stateTime;
    private float moveTime;

    public HashMap<String, Integer[]> animationInfo = new HashMap<String, Integer[]>();
    public TextureRegion currentPlayerFrame;

    public Sprite sprite;

    public boolean isFacingUp = false;
    public boolean isFacingLeft = false;
    public boolean isMoving;
    public boolean isMovingHorizontally;

    public float scoreEarnedThisRoom = 0;

    public ShaderProgram shaderProgram;

    public Controller(GridObject gridInstance, float radius) {
        super(Main.activeSpritePath, 8, 7);

        lightSource = LightSource.createLightSource(x, y);

        int x = gridInstance.getGridX(), y = gridInstance.getGridY();
        gridInstance.type = GridObject.TYPE.CONTROLLER;
        this.gridInstance = gridInstance;
        rX = x*NewGameScreen.tileWidth + NewGameScreen.tileWidth/2f;
        rY = y*NewGameScreen.tileHeight + NewGameScreen.tileHeight/2f;
        this.radius = radius;
        scoreEarnedThisRoom = 0;

        // HashMap<String, Integer[]> animationInfo:
        //      key - Name of animation
        //      Value - Array representing row of animation on sprite sheet and index of start and end frames
        animationInfo.put("idle", new Integer[]{0,0,8});
        animationInfo.put("walkForwards", new Integer[]{1,0,8});
        animationInfo.put("walkLeftForwards", new Integer[]{2,0,8});
        animationInfo.put("walkRightForwards", new Integer[]{6,0,8});
        animationInfo.put("walkRightBackwards", new Integer[]{5,0,8});
        animationInfo.put("walkLeftBackwards", new Integer[]{3,0,8});
        animationInfo.put("walkBackwards", new Integer[]{4,0,8});

        generateAnimation(animationInfo, 0.3f);

        sprite = new Sprite(animations.get("walkLeftForwards").getKeyFrame(0, true));
        sprite.setBounds(sprite.getX(), sprite.getY(), 48, 64);

        // effects shaders
        String vertexShader = Gdx.files.internal("shaders/sp_invulnerable_vert.glsl").readString();
        String fragmentShader = Gdx.files.internal("shaders/sp_invulnerable_frag.glsl").readString();
        shaderProgram = new ShaderProgram(vertexShader,fragmentShader);
        if (!shaderProgram.isCompiled()) {
            System.out.print(shaderProgram.getLog());
        }
        ShaderProgram.pedantic = false;
    }

    public void step() {
        int posX = gridInstance.getGridX(), posY = gridInstance.getGridY();
        rX = MathUtils.lerp(rX, posX*NewGameScreen.tileWidth+NewGameScreen.tileWidth/2f, 0.3f);
        rY = MathUtils.lerp(rY, posY*NewGameScreen.tileHeight+NewGameScreen.tileHeight/2f, 0.3f);

        float delta = Gdx.graphics.getDeltaTime();
        powerupTimer -= delta;

        if (powerupTimer < 0f) {
            if (invincible) {
                invincible = false;
                NewGameScreen.infoMsg("Powerup expired.");
            }
            powerupTimer = 0f;
        }
    }

    public void hop(int dir) {
        isMoving = true;
        moveTime = 0.25f;

        switch (dir) {
            case 0:
                isFacingUp = true;
                isMovingHorizontally = false;
                break;
            case 1:
                isFacingLeft = true;
                isMovingHorizontally = true;
                break;
            case 2:
                isFacingUp = false;
                isMovingHorizontally = false;
                break;
            case 3:
                isFacingLeft = false;
                isMovingHorizontally = true;
                break;
        }

        int nX = gridInstance.getGridX(), nY = gridInstance.getGridY();
        GridObject.push(NewGameScreen.room.grid, NewGameScreen.room.width, NewGameScreen.room.height, nX, nY, dir, 2);
    }

    public void hit() {
        // restart
        // TODO: implement restarting procedure
        if (!invincible) {
            if (!Main.playerShotOnce) {
                Main.playerShotOnce = true;
                Main.foundNegativeEvents++;
            }
            Main.score -= 25;

            Main.score -= scoreEarnedThisRoom;
            scoreEarnedThisRoom = 0;

            if (Main.score < 0) {Main.score = 0;}

            NewGameScreen.start();
        }
    }

    public void invinciblePowerup(float time) {
        invincible = true;
        powerupTimer += time;
    }

    public void updateSprite() {
        float delta = Gdx.graphics.getDeltaTime();
        stateTime += delta;
        moveTime -= delta;

        if (moveTime < 0f) {
            isMoving = false;
            isFacingUp = false;
            moveTime = 0f;
        }

        sprite.setPosition(rX-25f, rY-30f);

        if (isMoving){
            if(isFacingUp){
                if(isMovingHorizontally) {
                    if (isFacingLeft) {
                        currentPlayerFrame = animations.get("walkLeftBackwards").getKeyFrame(stateTime, true);
                    } else {
                        currentPlayerFrame = animations.get("walkRightBackwards").getKeyFrame(stateTime, true);
                    }
                }
                else{

                    currentPlayerFrame = animations.get("walkBackwards").getKeyFrame(stateTime, true);
                }
            }
            else{
                if(isMovingHorizontally) {
                    if (isFacingLeft) {
                        currentPlayerFrame = animations.get("walkLeftForwards").getKeyFrame(stateTime, true);
                    } else {
                        currentPlayerFrame = animations.get("walkRightForwards").getKeyFrame(stateTime, true);
                    }
                }
                else{
                    currentPlayerFrame = animations.get("walkForwards").getKeyFrame(stateTime, true);
                }
            }

        }
        else{

            currentPlayerFrame = animations.get("idle").getKeyFrame(stateTime, true);
        }
        sprite.setRegion(currentPlayerFrame);
        shaderProgram.setUniformf("u_uvMin", currentPlayerFrame.getU(), currentPlayerFrame.getV());
        shaderProgram.setUniformf("u_uvMax", currentPlayerFrame.getU2(), currentPlayerFrame.getV2());
    }
}
