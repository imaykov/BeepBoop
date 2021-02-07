package com.maykov.game.beepboop.fontRendering;

import com.maykov.game.beepboop.shader.ShaderProgram;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/shaders/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fontFragment.txt";

	private int locationColor;
	private int locationTranslation;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		locationColor = getUniformLocation("color");
		locationTranslation = getUniformLocation("translation");
		
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "textureCoords");

	}

	protected void loadColor(Vector3f color) {
		loadVector(locationColor, color);
	}

	protected void loadTranslation(Vector2f translation) {
		loadVector(locationTranslation, translation);
	}


}
