package com.maykov.game.beepboop.entities;

import org.lwjgl.util.vector.Vector3f;


/**
 * This class is used to control the position and rotation of the view.
 * 
 * 
 * 
 */
public class Camera {
    protected Vector3f position = new Vector3f(0, 0, 0);
    protected float pitch;
    protected float yaw;
    protected float roll;


    /**
     * Increases the x, y, and z coordinates relative to the object's current coordinates.
     * 
     * 
     * @param x The value to increase the x-coordinate by.
     * @param y The value to increase the y-coordinate by.
     * @param z The value to increase the z-coordinate by.
     */
    public void increasePosition(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    /**
     * Increases the x, y, and z rotation relative to the object's current rotation.
     * 
     * 
     * @param x The value to increase the x-rotation by.
     * @param y The value to increase the y-rotation by.
     * @param z The value to increase the z-rotation by.
     */
    public void increaseRotation(float x, float y, float z) {
        pitch += x;
        yaw += y;
        roll += z;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return this.roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }

    public void setRotation(float x, float y, float z) {
        pitch = x;
        yaw = y;
        roll = z;

    }

    
    
}
