package com.maykov.game.beepboop.shader;

import java.util.List;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * The TerrainShader is the shader program for terrain
 * 
 */
public class TerrainShader extends ShaderProgram {
    private static final int MAX_LIGHTS = 3;

    private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition[];
    private int locationLightColor[];
    private int locationLightAttenuation[];
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationAmbientOcclusion;
    private int locationBgTexture;
    private int locationRTexture;
    private int locationGTexture;
    private int locationBTexture;
    private int locationBlendMap;


    public TerrainShader() {
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
        locationBgTexture = super.getUniformLocation("bgTexture");
        locationRTexture = super.getUniformLocation("rTexture");
        locationGTexture = super.getUniformLocation("gTexture");
        locationBTexture = super.getUniformLocation("bTexture");
        locationBlendMap = super.getUniformLocation("blendMap");

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

    public void loadShineVariables(float damper, float reflectivity) {
        super.loadFloat(locationShineDamper, damper);
        super.loadFloat(locationReflectivity, reflectivity);
    }

    public void loadAmbientOcclusion(float ambientOcclusion) {
        super.loadFloat(locationAmbientOcclusion, ambientOcclusion);

    }

    public void connectTerrainTextures() {
        super.loadInt(locationBgTexture, 0);
        super.loadInt(locationRTexture, 1);
        super.loadInt(locationGTexture, 2);
        super.loadInt(locationBTexture, 3);
        super.loadInt(locationBlendMap, 4);

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
