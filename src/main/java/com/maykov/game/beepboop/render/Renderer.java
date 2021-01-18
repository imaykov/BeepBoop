package com.maykov.game.beepboop.render;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.maykov.game.beepboop.entities.Entity;
import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.model.TexturedModel;
import com.maykov.game.beepboop.shader.StaticShader;
import com.maykov.game.beepboop.texture.ModelTexture;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class Renderer {
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;

    private Matrix4f projectionMatrix;
    private StaticShader shader;

    public Renderer(StaticShader shader) {
        this.shader = shader;
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
        // System.out.println("projectionMatrix= " + projectionMatrix);
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(.5f, 0f, 1f, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Map<TexturedModel, List<Entity>> entities) {
        Set<TexturedModel> models = entities.keySet();
        List<Entity> tmpEntities = null;
        for (TexturedModel model : models) {
            prepareTexturedModel(model);
            tmpEntities = entities.get(model);
            for (Entity entity : tmpEntities) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, 
                    model.getRawModel().getVertexCount(), 
                    GL11.GL_UNSIGNED_INT, 
                    0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        ModelTexture texture = model.getTexture();
        GL30.glBindVertexArray(rawModel.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = 
            MathTool.createTransformationMatrix(entity.getPosition(), 
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }

    /*
    public void render(Entity entity, StaticShader shader) {
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getRawModel();
        ModelTexture texture = texturedModel.getTexture();
        GL30.glBindVertexArray(model.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = 
            MathTool.createTransformationMatrix(entity.getPosition(), 
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, 
            model.getVertexCount(), 
            GL11.GL_UNSIGNED_INT, 
            0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    */

    private void createProjectionMatrix() {
        
        float aspectRatio = ((float) Display.getDisplayMode().getWidth()) / ((float) Display.getDisplayMode().getHeight());
        float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
        projectionMatrix.m23 = -1f;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
        projectionMatrix.m33 = 0;

    }
    
}
