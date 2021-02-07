package com.maykov.game.beepboop.terrains;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TerrainList implements Iterable<Terrain> {
    List<Terrain> terrains;

    public TerrainList() {
        terrains = new ArrayList<>();
    }

    public void add(Terrain terrain) {
        terrains.add(terrain);
    }

    public Terrain get(int index) {
        return terrains.get(index);
    }

    public int size() {
        return terrains.size();
    }

    public float getHeightAt(float worldX, float worldZ) {
        int w = 0;
        int size = terrains.size();
        float result = 0f;
        boolean noResult = true;
        Terrain tmpTerrain;
        while(w < size && noResult) {
            tmpTerrain = terrains.get(w);
            if (tmpTerrain.isOnThisTerrain(worldX, worldZ)) {
                noResult = false;
                result = tmpTerrain.getHeightOfTerrain(worldX, worldZ);
            }

            w++;
        }
        return result;
    }

    @Override
    public Iterator<Terrain> iterator() {
        return terrains.iterator();
    }
    
}
