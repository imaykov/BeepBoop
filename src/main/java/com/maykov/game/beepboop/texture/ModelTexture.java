package com.maykov.game.beepboop.texture;

public class ModelTexture {
    private int textureId;
    private int normalMap;

    

    private float shineDamper = 1;
    private float reflectivity = 0;
    private boolean hasTransparency = false;
    private int numberOfRows = 1;


    

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

    public boolean isHasTransparency() {
        return this.hasTransparency;
    }

    public void setHasTransparency(boolean hasTransparency) {
        this.hasTransparency = hasTransparency;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNormalMap() {
        return this.normalMap;
    }

    public void setNormalMap(int normalMap) {
        this.normalMap = normalMap;
    }
    
}
