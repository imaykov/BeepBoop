package com.maykov.game.beepboop.model;

public class RawModel {
    private int vaoId;
    private int vertexCount;

    public RawModel(int vaoId, int vertexCount) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }

    public int getVaoId() {
        return this.vaoId;
    }

    public void setVaoId(int vaoId) {
        this.vaoId = vaoId;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

}
