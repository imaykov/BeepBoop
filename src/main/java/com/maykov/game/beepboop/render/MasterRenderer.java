package com.maykov.game.beepboop.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.entities.Entity;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.model.TexturedModel;
import com.maykov.game.beepboop.shader.NormalMappingRenderer;
import com.maykov.game.beepboop.shader.StaticShader;
import com.maykov.game.beepboop.shader.TerrainShader;
import com.maykov.game.beepboop.skybox.SkyboxRenderer;
import com.maykov.game.beepboop.terrains.Terrain;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MasterRenderer {
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1300;
    private float ambientOcclusion = 0.0f;

    private Vector3f skyColor = new Vector3f(.53f, .81f, .94f);
    
    private Matrix4f projectionMatrix;

    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private NormalMappingRenderer normalMappingRenderer;
    

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private Map<TexturedModel, List<Entity>> normalMappingEntities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    private SkyboxRenderer skyboxRenderer;


    public MasterRenderer(Loader loader) {
        controlCulling(true);
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
        normalMappingRenderer = new NormalMappingRenderer(projectionMatrix);

    }

    public void render(List<Light> lights, Camera camera) {
        prepare();

        // Entity rendering
        shader.start();
        shader.loadLights(lights);
        shader.loadViewMatrix(camera);
        shader.loadAmbientOcclusion(ambientOcclusion);
        renderer.render(entities);
        shader.stop();

        // Normal Mapping Entities rendering
        normalMappingRenderer.render(normalMappingEntities, lights, camera);
        

        // Terrain Rendering
        terrainShader.start();
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        
        skyboxRenderer.render(camera);

        entities.clear();
        terrains.clear();
        normalMappingEntities.clear();
    }

    public static void controlCulling(boolean shouldEnable) {
        if (shouldEnable) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
        } else {
            GL11.glDisable(GL11.GL_CULL_FACE);
        }
    }



    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processEntity(Entity entity) {
        TexturedModel model = entity.getModel();
        List<Entity> batch = entities.get(model);
        if (batch == null) {
            batch = new ArrayList<>();
            batch.add(entity);
            entities.put(model, batch);
        } else {
            batch.add(entity);
        }
    }

    public void processNormalMappingEntity(Entity entity) {
        TexturedModel model = entity.getModel();
        List<Entity> batch = normalMappingEntities.get(model);
        if (batch == null) {
            batch = new ArrayList<>();
            batch.add(entity);
            normalMappingEntities.put(model, batch);
        } else {
            batch.add(entity);
        }
        
    }

    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
        normalMappingRenderer.cleanUp();
    }

    public float getAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    public void setAmbientOcclusion(float ambientOcclusion) {
        this.ambientOcclusion = ambientOcclusion;
    }

    public void prepare() {
        skyboxRenderer.incrementTime(6f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClearColor(skyColor.x, skyColor.y, skyColor.z, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
    }

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


    public Vector3f getSkyColor() {
        return this.skyColor;
    }

    public void setSkyColor(Vector3f skyColor) {
        this.skyColor = skyColor;
    }

    public void setSkyColor(float r, float g, float b) {
        this.skyColor.x = r;
        this.skyColor.y = g;
        this.skyColor.z = b;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    
}
