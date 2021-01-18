package com.maykov.game.beepboop.model;

import com.maykov.game.beepboop.texture.ModelTexture;

public class TexturedModel {
    private RawModel rawModel;
    private ModelTexture texture;
    
    public TexturedModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return this.rawModel;
    }

    public ModelTexture getTexture() {
        return this.texture;
    }

    
}
