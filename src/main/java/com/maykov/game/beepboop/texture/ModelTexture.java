package com.maykov.game.beepboop.texture;

public class ModelTexture {
    private int textureId;

    private float shineDamper = 1;
    private float reflectivity = 0;

    

    public ModelTexture(int textureId) {
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }

    public float getShineDamper() {
        return this.shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return this.reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
    
}
