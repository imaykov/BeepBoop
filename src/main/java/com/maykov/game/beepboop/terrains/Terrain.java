package com.maykov.game.beepboop.terrains;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import com.maykov.game.beepboop.model.RawModel;
import com.maykov.game.beepboop.render.Loader;
import com.maykov.game.beepboop.texture.TerrainTexturePack;
import com.maykov.game.beepboop.toolbox.MathTool;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


public class Terrain {
    private static final float SIZE = 800f;
    private static final int VERTEX_COUNT = 128;
    private static final float MAX_HEIGHT = 40;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

    private float x;
    private float z;
    private RawModel model;
    private TerrainTexturePack texturePack;
    private float[][] heights;



    public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, String heightMap) {
        this.texturePack = texturePack;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader, heightMap);
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public RawModel getModel() {
        return this.model;
    }

    public void setModel(RawModel model) {
        this.model = model;
    }

    public TerrainTexturePack getTexturePack() {
        return this.texturePack;
    }

    public void setTexturePack(TerrainTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    public boolean isOnThisTerrain(float worldX, float worldZ) {
        boolean result = true;
        if (worldX < x || worldX > (x + SIZE) || worldZ < z || worldZ > (z + SIZE)) {
            result = false;
        }
        return result;

    }

    public float getHeightOfTerrain(float worldX, float worldZ) {
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSquareSize = SIZE/ ((float) heights.length - 1);
        int gridX = (int) (Math.floor(terrainX / gridSquareSize));
        int gridZ = (int) (Math.floor(terrainZ / gridSquareSize));
        if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
            //System.out.println("Using wrong Terrain");
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
        float answer;
        if (xCoord <= (1 - zCoord)) {
            answer = MathTool.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), 
                new Vector3f(1 , heights[gridX + 1][gridZ], 0), 
                new Vector3f(0 , heights[gridX][gridZ + 1], 1), 
                new Vector2f(xCoord, zCoord));
        } else {
            answer = MathTool.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), 
                new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), 
                new Vector3f(0, heights[gridX][gridZ + 1], 1), 
                new Vector2f(xCoord, zCoord));
        }
        return answer;

        


    }


    private RawModel generateTerrain(Loader loader, String heightMap) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(heightMap));
        } catch (Exception e) {
            System.err.println("Error getting Heightmap! Error: " + e);
        }
        int VERTEX_COUNT = image.getHeight();
        
        heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        

		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
        int vertexPointer = 0;
        Vector3f tmpNormal;
        float height;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
                height = getHeight(j, i, image);
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
                vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
                heights[j][i] = height;
                tmpNormal = calculateNormal(j, i, image);
				normals[vertexPointer*3] = tmpNormal.x;
				normals[vertexPointer*3+1] = tmpNormal.y;
				normals[vertexPointer*3+2] = tmpNormal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVao(vertices, textureCoords, normals, indices);
    }
    
    private Vector3f calculateNormal(int x, int z, BufferedImage image) {
        float heightL = getHeight(x - 1, z, image);
        float heightR = getHeight(x + 1, z, image);
        float heightD = getHeight(x, z - 1, image);
        float heightU = getHeight(x, z + 1, image);

        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        normal.normalise();
        return normal;
    }

    private float getHeight (int x, int z, BufferedImage image) {
        float result = 0f;
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            // Do nothing
        } else {
            result = image.getRGB(x, z);
            result += MAX_PIXEL_COLOR / 2f;
            result /= MAX_PIXEL_COLOR / 2f;
            result *= MAX_HEIGHT;
        }
        return result;
    }
    
}
