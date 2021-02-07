package com.maykov.game.beepboop.render;

import java.util.List;

import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.shader.TerrainShader;
import com.maykov.game.beepboop.terrains.Terrain;
import com.maykov.game.beepboop.texture.TerrainTexturePack;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class TerrainRenderer {
    private TerrainShader shader;
    
    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTerrainTextures();
        shader.stop();

    }

    public void render(List<Terrain> terrains) {
        for(Terrain terrain: terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, 
                    terrain.getModel().getVertexCount(), 
                    GL11.GL_UNSIGNED_INT, 
                    0);
            unbindTerrain();
        }
    }

    private void bindTextures(Terrain terrain) {
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBgTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getRTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getGTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBTexture().getTextureId());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBlendMap().getTextureId());

    }

    private void prepareTerrain(Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(1, 0);
        bindTextures(terrain);
    }

    private void unbindTerrain() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = 
            MathTool.createTransformationMatrix(new Vector3f(terrain.getX(), 0f,terrain.getZ()), 0f, 0f, 0f, 1f);
        shader.loadTransformationMatrix(transformationMatrix);
    }
    
}
