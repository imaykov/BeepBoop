package com.maykov.game.beepboop.entities;

import com.maykov.game.beepboop.gui.DisplayManager;
import com.maykov.game.beepboop.model.TexturedModel;
import com.maykov.game.beepboop.terrains.Terrain;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;


/**
 * This class is a subclass of entity, but can be moved by a keyboard. In addition, a third person camera
 * can track the Player.
 * 
 * 
 */
public class Player extends Entity {
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -30f;
    

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    
    // Jump variables
    private float upwardsSpeed = 0;
    private int maxJumps = 1;
    private int jumpsLeft = maxJumps;
    private float jumpHeight = 50;
    


    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain) {
        float timePerFrame = DisplayManager.getFrameTimeSeconds();
        checkInputs();
        super.increaseRotation(0f, currentTurnSpeed * timePerFrame, 0f);
        float distance = currentSpeed * timePerFrame;
        float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0f, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0f, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0f);
        float height = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
        if (height < 0.001 && height > -0.001) {
            //System.out.println("PlayerHeight is = " + getPosition().y + ", height is = " + height);
        }
        if (getPosition().y < height) {
            upwardsSpeed = 0;
            jumpsLeft = maxJumps;
            getPosition().y = height;

        } else {

        }

    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            currentSpeed = -RUN_SPEED;
        } else {
            currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            currentTurnSpeed = TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            currentTurnSpeed = -TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }

        if (jumpsLeft > 0 && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jumpsLeft--;
            upwardsSpeed += jumpHeight;

        }
    }




    
}
