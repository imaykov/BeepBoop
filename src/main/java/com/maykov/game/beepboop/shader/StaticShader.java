package com.maykov.game.beepboop.shader;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationAmbientOcclusion;

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationLightPosition = super.getUniformLocation("lightPosition");
        locationLightColor = super.getUniformLocation("lightColor");
        locationShineDamper = super.getUniformLocation("shineDamper");
        locationReflectivity = super.getUniformLocation("reflectivity");
        locationAmbientOcclusion = super.getUniformLocation("ambientOcclusion");
        // System.out.println("transProj ints: " + locationProjectionMatrix + ", " + locationProjectionMatrix);
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(locationShineDamper, damper);
        super.loadFloat(locationReflectivity, reflectivity);
    }

    public void loadAmbientOcclusion(float ambientOcclusion) {
        super.loadFloat(locationAmbientOcclusion, ambientOcclusion);

    }

    public void loadLight(Light light) {
        super.loadVector(locationLightPosition, light.getPosition());
        super.loadVector(locationLightColor, light.getColor());
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f matrix = MathTool.createViewMatrix(camera);
        super.loadMatrix(locationViewMatrix, matrix);
    }
    
    
}
