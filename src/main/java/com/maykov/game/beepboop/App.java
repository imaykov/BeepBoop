package com.maykov.game.beepboop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.maykov.game.beepboop.entities.Entity;
import com.maykov.game.beepboop.entities.Light;
import com.maykov.game.beepboop.entities.Player;
import com.maykov.game.beepboop.entities.ThirdPersonCamera;
import com.maykov.game.beepboop.fontMeshCreator.FontType;
import com.maykov.game.beepboop.fontMeshCreator.GUIText;
import com.maykov.game.beepboop.fontRendering.TextMaster;
import com.maykov.game.beepboop.gui.DisplayManager;
import com.maykov.game.beepboop.gui.GuiRenderer;
import com.maykov.game.beepboop.gui.GuiTexture;
import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.model.TexturedModel;
import com.maykov.game.beepboop.objconverter.ModelData;
import com.maykov.game.beepboop.objconverter.ModelDataNM;
import com.maykov.game.beepboop.objconverter.NormalMappedObjLoader;
import com.maykov.game.beepboop.objconverter.OBJFileLoader;
import com.maykov.game.beepboop.render.Loader;
import com.maykov.game.beepboop.render.MasterRenderer;
import com.maykov.game.beepboop.terrains.Terrain;
import com.maykov.game.beepboop.terrains.TerrainList;
import com.maykov.game.beepboop.texture.ModelTexture;
import com.maykov.game.beepboop.texture.TerrainTexture;
import com.maykov.game.beepboop.texture.TerrainTexturePack;
import com.maykov.game.beepboop.toolbox.MousePicker;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("START Main! 12:25pm 1/18");
        DisplayManager.createDisplay();
        DisplayManager.updateDisplay();

        List<Entity> allEntities = new ArrayList<>();
        List<Entity> normalMapEntities = new ArrayList<>();


        // Create loaders ander renderers
        Loader l = new Loader();

        // Create Text renderer
        TextMaster.init(l);

        FontType font = new FontType(l.loadTexture("Fonts/arial.png"), new File("Fonts/arial.fnt"));
        GUIText text1 = new GUIText("Hello World", 1, font, new Vector2f(.3f, 0f), 1f, false);

        // StaticShader shader = new StaticShader();
        MasterRenderer r = new MasterRenderer(l);
        r.setSkyColor( .05f, .02f, .2f);
        GuiRenderer guiRenderer = new GuiRenderer(l);
        r.setAmbientOcclusion(0.15f);

        // Create GUI stuff
        List<GuiTexture> guiTextures = new ArrayList<>();
        guiTextures.add(new 
            GuiTexture(l.loadTexture("res/xanaTransparent.png"), 
                new Vector2f(-.7f, .7f), 
                new Vector2f(.075f, .25f)));
        guiTextures.add(new 
            GuiTexture(l.loadTexture("res/lyokoLogo.png"), 
                new Vector2f(-.6f, .6f), 
                new Vector2f(.3f, .1f)));

        // Tree
        ModelData mData1 = OBJFileLoader.loadOBJ("res/tree.obj");
        RawModel model1 = l.loadToVao(mData1);
        ModelTexture texture1 = new ModelTexture(l.loadTexture("res/simpleTreeTexture.png"));
        texture1.setShineDamper(1f);
        texture1.setReflectivity(.3f);
        TexturedModel texturedModel1 = new TexturedModel(model1, texture1);
        
        //fern
        ModelData mData2 = OBJFileLoader.loadOBJ("res/fern.obj");
        RawModel model2 = l.loadToVao(mData2);
        ModelTexture texture2 = new ModelTexture(l.loadTexture("res/fernAtlas.png"));
        texture2.setNumberOfRows(2);
        texture2.setHasTransparency(true);
        texture2.setShineDamper(1f);
        texture2.setReflectivity(.1f);
        TexturedModel texturedModel2 = new TexturedModel(model2, texture2);

        //grass
        ModelData mData3 = OBJFileLoader.loadOBJ("res/fern.obj");
        RawModel model3 = l.loadToVao(mData3);
        ModelTexture texture3 = new ModelTexture(l.loadTexture("res/grassTexture.png"));
        texture3.setHasTransparency(true);
        texture3.setShineDamper(1f);
        texture3.setReflectivity(.1f);
        TexturedModel texturedModel3 = new TexturedModel(model3, texture3);

        RawModel model4 = NormalMappedObjLoader.loadOBJ("res/barrel.obj", l);
        ModelTexture texture4 = new ModelTexture(l.loadTexture("res/barrel.png"));
        TexturedModel texturedModel4 = new TexturedModel(model4, texture4);
        texture4.setReflectivity(.5f);
        texture4.setShineDamper(10f);
        texture4.setNormalMap(l.loadTexture("res/barrelNormal.png"));

        normalMapEntities.add(new Entity(texturedModel4, new Vector3f(0f, 5f, 0f), 0f, 0f, 0f, 1f));
        
        

        // create the terrain
        TerrainTexture terrain1 = new TerrainTexture(l.loadTexture("res/grass.png"));
        TerrainTexture terrain2 = new TerrainTexture(l.loadTexture("res/mud.png"));
        TerrainTexture terrain3 = new TerrainTexture(l.loadTexture("res/grassFlowers.png"));
        TerrainTexture terrain4 = new TerrainTexture(l.loadTexture("res/stonePath.png"));
        TerrainTexture terrain5 = new TerrainTexture(l.loadTexture("res/blendMap.png"));
        TerrainTexturePack tp = new TerrainTexturePack(terrain1, terrain2, terrain3, terrain4, terrain5);
        TerrainList grassTerrains = new TerrainList();
        int gridSize = 6;
        for (int i = -gridSize / 2; i < gridSize / 2; i++) {
            for (int i2 = -gridSize / 2; i2 < gridSize / 2; i2++) {
                grassTerrains.add(new Terrain(i, i2, l, tp, "res/heightMap.png"));
            }
        }


        // Create all of the entities
        float tmpFloatX = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
        float tmpFloatY = 0;
        float tmpFloatZ = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
        
        Entity tmpEntity = null;
        int numberOfEachObject = 1500;
        for (int i = 0; i < numberOfEachObject; i++) {
            tmpFloatX = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
            tmpFloatZ = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
            tmpFloatY = grassTerrains.getHeightAt(tmpFloatX, tmpFloatZ);
            tmpEntity = new Entity(texturedModel1, new Vector3f(tmpFloatX, tmpFloatY, tmpFloatZ), 0, 0, 0, 10);
            
            allEntities.add(tmpEntity);
        }
        for (int i = 0; i < numberOfEachObject; i++) {
            tmpFloatX = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
            tmpFloatZ = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
            tmpFloatY = grassTerrains.getHeightAt(tmpFloatX, tmpFloatZ);
            tmpEntity = new Entity(texturedModel2, new Vector3f(tmpFloatX, tmpFloatY, tmpFloatZ), 0, 0, 0, 1);
            tmpEntity.setTextureIndex((int) (Math.floor(Math.random() * 4)));
            allEntities.add(tmpEntity);
        }
        for (int i = 0; i < numberOfEachObject; i++) {
            tmpFloatX = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
            tmpFloatZ = (float)(Math.random() * gridSize * 800f) - gridSize * 400f;
            tmpFloatY = grassTerrains.getHeightAt(tmpFloatX, tmpFloatZ);
            tmpEntity = new Entity(texturedModel3, new Vector3f(tmpFloatX, tmpFloatY, tmpFloatZ), 0, 0, 0, 1);
            allEntities.add(tmpEntity);
        }

        // Create the Player entity
        ModelData mdPlayer = OBJFileLoader.loadOBJ("res/dragon.obj");
        RawModel rmPlayer = l.loadToVao(mdPlayer);
        ModelTexture mtPlayer = new ModelTexture(l.loadTexture("res/redTexture.png"));
        mtPlayer.setReflectivity(.7f);
        mtPlayer.setShineDamper(40f);
        TexturedModel tmPlayer = new TexturedModel(rmPlayer, mtPlayer);
        Player player = new Player(tmPlayer, new Vector3f(), 0f, 0f, 0f, 1f);
        allEntities.add(player);


        ThirdPersonCamera camera = new ThirdPersonCamera(player);
        // camera.increasePosition(0f, 16f, 3f);
        // camera.increaseRotation(0f, 180f, 0f);
        List<Light> lights = new ArrayList<>();
        lights.add(new Light(new Vector3f(0f, 1000f, 0f), new Vector3f(.3f, .3f, .3f)));
        lights.add(new Light(new Vector3f(30f, 10f, -10f), new Vector3f(2f, 2f, 2f), new Vector3f(1, .01f, .002f)));
        lights.add(new Light(new Vector3f(-30f, 10f, -10f), new Vector3f(0f, 0f, 3f), new Vector3f(1, .01f, .002f)));

        MousePicker picker = new MousePicker(camera, r.getProjectionMatrix());

        Terrain playerOnTerrain = null;
        int w = 0;
        int printCounter = 0;
        while (!Display.isCloseRequested()) {
            for (Terrain tmpTerrain: grassTerrains) {
                r.processTerrain(tmpTerrain);
            }
            for (Entity entity : allEntities) {
                // entity.increaseRotation(0f, .5f, 0f);
                r.processEntity(entity);
                
            } 
            for (Entity entity: normalMapEntities) {
                r.processNormalMappingEntity(entity);
            }
            w = 0;
            while(w < grassTerrains.size() && playerOnTerrain == null) {
                if(w == (grassTerrains.size() - 1) || grassTerrains.get(w).isOnThisTerrain(player.getPosition().x, player.getPosition().z)) {
                    playerOnTerrain = grassTerrains.get(w);
                    if(printCounter == 60) {
                        //System.out.println("On terrain:" + w);
                        printCounter = 0;
                    }
                }

                w++;
            }
            /*
            if (printCounter % 100 == 0) {
                System.out.println("MousePicker vector=" + picker.getCurrentRay());
            } */
            
            player.move(playerOnTerrain);
            camera.move();
            picker.update();

            r.render(lights, camera);
            guiRenderer.render(guiTextures);
            TextMaster.render();


            DisplayManager.updateDisplay();
            playerOnTerrain = null;
            //printCounter++;
        }

        TextMaster.cleanUp();
        r.cleanUp();
        guiRenderer.cleanUp();
        l.cleanUp();
        DisplayManager.closeDisplay();
        System.out.println( "STOP Main!");

    }
}
