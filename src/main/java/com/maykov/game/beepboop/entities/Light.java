package com.maykov.game.beepboop.entities;

import org.lwjgl.util.vector.Vector3f;


/**
 * Light objects are used to illuminate the surrounding area.
 * 
 * 
 * 
 */
public class Light {
    private Vector3f position;
    private Vector3f color;
    private Vector3f attenuation = new Vector3f(1, 0, 0);

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
        this.position = position;
        this.color = color;
        this.attenuation = attenuation;
    }

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

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getAttenuation() {
        return this.attenuation;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }


    
}
