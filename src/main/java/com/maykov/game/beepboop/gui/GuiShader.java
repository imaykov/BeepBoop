package com.maykov.game.beepboop.gui;

import com.maykov.game.beepboop.shader.ShaderProgram;

import org.lwjgl.util.vector.Matrix4f;

public class GuiShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/shaders/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/guiFragmentShader.txt";

    private int locationTransformationMatrix;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(locationTransformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
    
    
}
