package com.maykov.game.beepboop.shader;

import java.util.List;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class StaticShader extends ShaderProgram {
    private static final int MAX_LIGHTS = 3;

    private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition[];
    private int locationLightColor[];
    private int locationLightAttenuation[];
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationAmbientOcclusion;
    private int locationNumberOfRows;
    private int locationOffset;

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
        // locationLightPosition = super.getUniformLocation("lightPosition");
        // locationLightColor = super.getUniformLocation("lightColor");
        locationShineDamper = super.getUniformLocation("shineDamper");
        locationReflectivity = super.getUniformLocation("reflectivity");
        locationAmbientOcclusion = super.getUniformLocation("ambientOcclusion");
        locationNumberOfRows = super.getUniformLocation("numberOfRows");
        locationOffset = super.getUniformLocation("offset");

        locationLightPosition = new int[MAX_LIGHTS];
        locationLightColor = new int[MAX_LIGHTS];
        locationLightAttenuation = new int[MAX_LIGHTS];

        for (int i = 0; i < MAX_LIGHTS; i++) {
            locationLightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
            locationLightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
            locationLightAttenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
        }

        // System.out.println("transProj ints: " + locationProjectionMatrix + ", " + locationProjectionMatrix);
    }

    public void loadNumberOfRows(int numberOfRows) {
        super.loadFloat(locationNumberOfRows, numberOfRows);
    }

    public void loadOffset(float xOffset, float yOffset) {
        super.loadVector(locationOffset, new Vector2f(xOffset, yOffset));
    }

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(locationShineDamper, damper);
        super.loadFloat(locationReflectivity, reflectivity);
    }

    public void loadAmbientOcclusion(float ambientOcclusion) {
        super.loadFloat(locationAmbientOcclusion, ambientOcclusion);

    }

    public void loadLights(List<Light> lights) {
        Light light;
        Vector3f empty = new Vector3f(0f, 0f, 0f);
        Vector3f attenuationDefault = new Vector3f(1f, 0f, 0f);
        int size = lights.size();
        for (int i = 0; i < MAX_LIGHTS; i++) {
            if (i < size) {
                light = lights.get(i);
                super.loadVector(locationLightPosition[i], light.getPosition());
                super.loadVector(locationLightColor[i], light.getColor());
                super.loadVector(locationLightAttenuation[i], light.getAttenuation());
            } else {
                super.loadVector(locationLightPosition[i], empty);
                super.loadVector(locationLightColor[i], empty);
                super.loadVector(locationLightAttenuation[i], attenuationDefault);
            }
        }
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
