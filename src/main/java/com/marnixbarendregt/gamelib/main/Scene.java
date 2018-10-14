package com.marnixbarendregt.gamelib.main;

import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marnixbarendregt on 20/09/2017.
 */
public class Scene {
    private List<Entity> entities;
    private List<Terrain> terrains;
    private boolean useSun = false;
    private Vector3f sunPosition = new Vector3f();

    public Scene() {
        entities = new ArrayList<>();
        terrains = new ArrayList<>();
    }

    public void useSun(boolean useSun) {
        this.useSun = useSun;
    }

    public boolean getUseSun() {
        return this.useSun;
    }

    public void setSunPos(Vector3f sunPosition) {
        this.sunPosition = sunPosition;
    }

    public Vector3f getSunPos() {
        return this.sunPosition;
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
