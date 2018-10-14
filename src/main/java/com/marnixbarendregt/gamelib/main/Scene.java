package com.marnixbarendregt.gamelib.main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marnixbarendregt on 20/09/2017.
 */
public class Scene {
    private List<Entity> entities;
    private List<Terrain> terrains;

    public Scene() {
        entities = new ArrayList<>();
        terrains = new ArrayList<>();
    }

    public void add(Entity entity) {
        entities.add(entity);
        entity.setId(entities.size() - 1);
    }

    public void add(Terrain terrain) {
        terrains.add(terrain);
    }

    public void remove(Entity entity) {
        entities.remove(entity);
        entity.setId(-1);
    }

    public void remove(Terrain terrain) {
        terrains.remove(terrain);
    }

    public List<Entity> getEntities() {
        return entities;
    }
    public List<Terrain> getTerrains() { return terrains; }


    public void cleanUp() {
        for (Entity entity : entities) {
            entity.getMesh().cleanUp();
        }

        for (Terrain terrain : terrains) {
            terrain.getMesh().cleanUp();
        }
    }

}
