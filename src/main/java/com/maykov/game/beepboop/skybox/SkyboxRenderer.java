package com.maykov.game.beepboop.skybox;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.gui.DisplayManager;
import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.render.Loader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

public class SkyboxRenderer {
	private static final float SIZE = 800f;
	
	
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
    };

    // Order for skybox cube is right, left, top, bottom, back, front
    private static String[] TEXTURE_FILES = {"res/skyboxImages/right.png", "res/skyboxImages/left.png", 
        "res/skyboxImages/top.png", "res/skyboxImages/bottom.png", "res/skyboxImages/back.png", 
		"res/skyboxImages/front.png"};
		private static String[] NIGHT_TEXTURE_FILES = {"res/skyboxImages/nightRight.png", 
		"res/skyboxImages/nightLeft.png", "res/skyboxImages/nightTop.png", "res/skyboxImages/nightBottom.png", 
		"res/skyboxImages/nightBack.png", "res/skyboxImages/nightFront.png"};
    private RawModel cube;
	private int texture1;
	private int texture2;
	private SkyboxShader shader;
	private float time = 0;

    public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix) {
        cube = loader.loadToVao(VERTICES, 3);
		texture1 = loader.loadCubeMap(TEXTURE_FILES);
		texture2 = loader.loadCubeMap(NIGHT_TEXTURE_FILES);
        shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();


    }

    public void render(Camera camera) {
        shader.start();
        shader.loadViewMatrix(camera);
        GL30.glBindVertexArray(cube.getVaoId());
        GL20.glEnableVertexAttribArray(0);
        bindTextures();
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
	}
	
	private void bindTextures() {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		float blendFactor = calculateBlendFactor();
		shader.loadBlendFactor(blendFactor);
	}

	public void incrementTime(float factor) {
		time += factor * DisplayManager.getFrameTimeSeconds();
	}

	private float calculateBlendFactor() {
		float radi = (float) Math.toRadians(time);
		float result = .5f * (float) Math.sin(radi / 3f) + .5f;
		return result;
	}
    


    
}
