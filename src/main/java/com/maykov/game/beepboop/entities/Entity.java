package com.maykov.game.beepboop.entities;

import com.maykov.game.beepboop.model.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

/**
 * Entities are the 3D Game Objects of this Game Engine. They store information on their position, rotation, and scale.<br>
 * They also store the RawModel, and textures used by the entity.
 * 
 * 
 */
public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private int textureIndex = 0;



    
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, 
        float rotZ, float scale) {
            this.model = model;
            this.position = position;
            this.rotX = rotX;
            this.rotY = rotY;
            this.rotZ = rotZ;
            this.scale = scale;
    }

    public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, 
        float rotZ, float scale) {
            this.model = model;
            this.position = position;
            this.rotX = rotX;
            this.rotY = rotY;
            this.rotZ = rotZ;
            this.scale = scale;
            this.textureIndex = textureIndex;
    }

    /**
     * Returns a float representing the x-offset of a texture. Used primarily when a texture atlas is used to make textures more diverse.
     * 
     * 
     * @return
     */
    public float getTextureXOffset() {
        int column = textureIndex % model.getTexture().getNumberOfRows();
        return (float) column / (float) model.getTexture().getNumberOfRows();
    }


    /**
     * Returns a float representing the y-offset of a texture. Used primarily when a texture atlas is used to make textures more diverse.
     * 
     * 
     * @return
     */
    public float getTextureYOffset() {
        int row = textureIndex / model.getTexture().getNumberOfRows();
        return (float) row / (float) model.getTexture().getNumberOfRows();
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

    /**
     * Increases the x, y, and z rotation relative to the object's current rotation.
     * 
     * 
     * @param x The value to increase the x-rotation by.
     * @param y The value to increase the y-rotation by.
     * @param z The value to increase the z-rotation by.
     */
    public void increaseRotation(float x, float y, float z) {
        rotX += x;
        rotY += y;
        rotZ += z;
    }

    public TexturedModel getModel() {
        return this.model;
    }

    public void setModel(TexturedModel model) {
        this.model = model;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
		return this.rotX;
	}

    public void setRotX(float rotX) {
		this.rotX = rotX;
    }
    
    public float getRotY() {
		return this.rotY;
	}

    public void setRotY(float rotY) {
		this.rotY = rotY;
    }
    
    public float getRotZ() {
		return this.rotZ;
	}

    public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getTextureIndex() {
        return this.textureIndex;
    }

    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }
    
    
}
