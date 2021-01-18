package com.maykov.game.beepboop.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.entities.Entity;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.model.TexturedModel;
import com.maykov.game.beepboop.shader.StaticShader;

public class MasterRenderer {
    private StaticShader shader = new StaticShader();
    private Renderer renderer = new Renderer(shader);

    private float ambientOcclusion = 0.0f;

    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();

    public void render(Light light, Camera camera) {
        renderer.prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);
        shader.loadAmbientOcclusion(ambientOcclusion);
        renderer.render(entities);
        shader.stop();
        entities.clear();
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

    public void cleanUp() {
        shader.cleanUp();
    }

    public float getAmbientOcclusion() {
        return this.ambientOcclusion;
    }

    public void setAmbientOcclusion(float ambientOcclusion) {
        this.ambientOcclusion = ambientOcclusion;
    }
    
}
