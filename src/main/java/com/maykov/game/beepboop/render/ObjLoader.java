package com.maykov.game.beepboop.render;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.maykov.game.beepboop.model.RawModel;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class ObjLoader {
    public static RawModel loadObjModel(String fileName, Loader loader) {
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find or read file! Error: " + e);
        }
        BufferedReader reader = new BufferedReader(fr);
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;
        
        
        try {
            String line = reader.readLine();
            String[] lineSplit = null;
            String[] vertex1 = null;
            String[] vertex2 = null;
            String[] vertex3 = null;
            Vector3f tmpVector3f = null;
            Vector2f tmpVector2f = null;
            int size = 0;

            
            while (line != null && !line.startsWith("f ")) {
                lineSplit = line.split(" ");
                if (line.startsWith("v ")) {
                    tmpVector3f = new Vector3f(Float.parseFloat(lineSplit[1]), 
                        Float.parseFloat(lineSplit[2]), 
                        Float.parseFloat(lineSplit[3]));
                    vertices.add(tmpVector3f);
                }  else if (line.startsWith("vt ")) {
                    tmpVector2f = new Vector2f(Float.parseFloat(lineSplit[1]), 
                        Float.parseFloat(lineSplit[2]));
                    textures.add(tmpVector2f);
                } else if (line.startsWith("vn ")) {
                    tmpVector3f = new Vector3f(Float.parseFloat(lineSplit[1]), 
                        Float.parseFloat(lineSplit[2]), 
                        Float.parseFloat(lineSplit[3]));
                    normals.add(tmpVector3f);
                }
                line = reader.readLine();
            }
            size = vertices.size();
            // verticesArray = new float[3 * size];
            normalsArray = new float[3 * size];
            textureArray = new float[2 * size];
            while (line != null) {
                if (!line.startsWith("f ")) {
                    // Do nothing
                } else {
                    lineSplit = line.split(" ");
                    vertex1 = lineSplit[1].split("/");
                    vertex2 = lineSplit[2].split("/");
                    vertex3 = lineSplit[3].split("/");

                    processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                }
                line = reader.readLine();
            }
            reader.close();
            verticesArray = new float[3 * size];
            indicesArray = new int[indices.size()];
            for (int i = 0; i < size; i++) {
                verticesArray[3 * i] = vertices.get(i).x; 
                verticesArray[3 * i + 1] = vertices.get(i).y; 
                verticesArray[3 * i + 2] = vertices.get(i).z;
            }
            for (int i = 0; i < indicesArray.length; i++) {
                indicesArray[i] = indices.get(i); 
            }
        } catch (Exception e) {
            System.err.println("Problem processing the file! Error: " + e);
        }

        return loader.loadToVao(verticesArray, textureArray, normalsArray, indicesArray);
    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, 
        List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currVertextPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currVertextPointer);
        Vector2f currTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArray[2 * currVertextPointer] = currTexture.x;
        textureArray[2 * currVertextPointer + 1] = 1 - currTexture.y;
        Vector3f currNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[3 * currVertextPointer] = currNormal.x;
        normalsArray[3 * currVertextPointer + 1] = currNormal.y;
        normalsArray[3 * currVertextPointer + 2] = currNormal.z;
    }
}
