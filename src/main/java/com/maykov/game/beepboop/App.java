package com.maykov.game.beepboop;

import java.util.ArrayList;
import java.util.List;

import com.maykov.game.beepboop.entities.Camera;
import com.maykov.game.beepboop.entities.Entity;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.gui.DisplayManager;
import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.model.TexturedModel;
import com.maykov.game.beepboop.render.Loader;
import com.maykov.game.beepboop.render.MasterRenderer;
import com.maykov.game.beepboop.render.ObjLoader;
import com.maykov.game.beepboop.shader.StaticShader;
import com.maykov.game.beepboop.texture.ModelTexture;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("START Main! 10:09pm 1/17");
        DisplayManager.createDisplay();
        DisplayManager.updateDisplay();

        Loader l = new Loader();
        StaticShader shader = new StaticShader();
        MasterRenderer r = new MasterRenderer();
        r.setAmbientOcclusion(0.1f);


        RawModel model1 = ObjLoader.loadObjModel("res/dragon.obj", l);
        ModelTexture texture1 = new ModelTexture(l.loadTexture("res/redTexture.png"));
        texture1.setShineDamper(50);
        texture1.setReflectivity(.2f);
        TexturedModel texturedModel1 = new TexturedModel(model1, texture1);
        Entity entity1 = new Entity(texturedModel1, new Vector3f(-3f, 0f, -13f), 0f, 0f, 0f, .6f);

        RawModel model2 = ObjLoader.loadObjModel("res/trashcan.obj", l);
        ModelTexture texture2 = new ModelTexture(l.loadTexture("res/grayTexture.png"));
        texture2.setShineDamper(4f);
        texture2.setReflectivity(4f);
        TexturedModel texturedModel2 = new TexturedModel(model2, texture2);
        Entity entity2 = new Entity(texturedModel2, new Vector3f(3f, 0f, -8f), 0f, 0f, 0f, .9f);

        Entity entity3 = new Entity(texturedModel1, new Vector3f(3f, 0f, -11f), 0f, 180f, 0f, .3f);

        
        Camera camera = new Camera();
        camera.increasePosition(0f, 8f, 3f);
        camera.increaseRotation(15f, 0f, 0f);
        Light light = new Light(new Vector3f(5f, 5f, 0f), new Vector3f(1f, 1f, 1f));
        
        List<Entity> allEntities = new ArrayList<>();
        allEntities.add(entity1);
        allEntities.add(entity2);
        allEntities.add(entity3);
        

        while (!Display.isCloseRequested()) {
            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camera.increasePosition(0f, 0f, -.1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camera.increasePosition(.1f, 0f, 0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camera.increasePosition(0f, 0f, .1f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camera.increasePosition(-.1f, 0f, 0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
                camera.increasePosition(0f, .1f, 0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
                camera.increasePosition(0f, -.1f, 0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7)) {
                camera.increaseRotation(.1f, 0f, 0f);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1)) {
                camera.increaseRotation(-.1f, 0f, 0f);
            }
            for (Entity entity : allEntities) {
                entity.increaseRotation(0f, .5f, 0f);
                r.processEntity(entity);
                
            }
            

            r.render(light, camera);
            DisplayManager.updateDisplay();
        }

        r.cleanUp();
        l.cleanUp();
        DisplayManager.closeDisplay();
        System.out.println( "STOP Main!");

    }
}
