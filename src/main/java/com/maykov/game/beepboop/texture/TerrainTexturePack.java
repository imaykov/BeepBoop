package com.maykov.game.beepboop.texture;

public class TerrainTexturePack {

    private TerrainTexture bgTexture;
    private TerrainTexture rTexture;
    private TerrainTexture gTexture;
    private TerrainTexture bTexture;
    private TerrainTexture blendMap;

    public TerrainTexturePack(TerrainTexture bgTexture, TerrainTexture rTexture,
        TerrainTexture gTexture, TerrainTexture bTexture, TerrainTexture blendMap) {
        this.bgTexture = bgTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
        this.blendMap = blendMap;

    }

    public TerrainTexture getBgTexture() {
        return this.bgTexture;
    }

    public void setBgTexture(TerrainTexture bgTexture) {
        this.bgTexture = bgTexture;
    }

    public TerrainTexture getRTexture() {
        return this.rTexture;
    }

    public void setRTexture(TerrainTexture rTexture) {
        this.rTexture = rTexture;
    }

    public TerrainTexture getGTexture() {
        return this.gTexture;
    }

    public void setGTexture(TerrainTexture gTexture) {
        this.gTexture = gTexture;
    }

    public TerrainTexture getBTexture() {
        return this.bTexture;
    }

    public void setBTexture(TerrainTexture bTexture) {
        this.bTexture = bTexture;
    }

    public TerrainTexture getBlendMap() {
        return this.blendMap;
    }

    public void setBlendMap(TerrainTexture blendMap) {
        this.blendMap = blendMap;
    }

    
}