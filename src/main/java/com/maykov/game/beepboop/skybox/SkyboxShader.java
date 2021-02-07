package com.maykov.game.beepboop.skybox;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.gui.DisplayManager;
import com.maykov.game.beepboop.shader.ShaderProgram;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SkyboxShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/skyboxVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/skyboxFragmentShader.txt";

	private static final float ROTATE_SPEED = .05f;
	
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationCubeMap1;
	private int locationCubeMap2;
	private int locationBlendFactor;

	private float rotation = 0;
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(locationProjectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera){
        Matrix4f matrix = MathTool.createViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
		matrix.m32 = 0;
		rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
		super.loadMatrix(locationViewMatrix, matrix);
	}

	public void connectTextureUnits() {
		loadInt(locationCubeMap1, 0);
		loadInt(locationCubeMap2, 1);
	}

	public void loadBlendFactor(float blend) {
		loadFloat(locationBlendFactor, blend);
	}
	
	@Override
	protected void getAllUniformLocations() {
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationCubeMap1 = super.getUniformLocation("cubeMap1");
		locationCubeMap2 = super.getUniformLocation("cubeMap2");
		locationBlendFactor = super.getUniformLocation("blendFactor");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
