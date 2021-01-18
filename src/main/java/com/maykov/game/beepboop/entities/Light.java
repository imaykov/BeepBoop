package com.maykov.game.beepboop.entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

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


    
}
