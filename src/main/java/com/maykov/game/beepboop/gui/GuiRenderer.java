package com.maykov.game.beepboop.gui;

import java.util.List;

import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.render.Loader;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class GuiRenderer {
    private final RawModel quad;
    private GuiShader shader;

    public GuiRenderer(Loader loader) {
        shader = new GuiShader();
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        quad = loader.loadToVao(positions, 2);
    }

    public void render(List<GuiTexture> guis) {
        shader.start();
        GL30.glBindVertexArray(quad.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        for (GuiTexture gui: guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix = MathTool.createTransformationMatrix(gui.getPosition(), gui.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }

    public void cleanUp() {
        shader.cleanUp();
    }


    
}
