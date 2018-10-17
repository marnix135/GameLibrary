package com.marnixbarendregt.gamelib.main;

import com.marnixbarendregt.gamelib.shaders.TerrainShader;
import com.marnixbarendregt.gamelib.shaders.TestShader;
import com.marnixbarendregt.gamelib.materials.Material;
import com.marnixbarendregt.gamelib.shaders.ShaderProgram;
import org.joml.Vector3f;

import java.util.List;

/**
 * Created by marnixbarendregt on 18/09/2017.
 */
public class Renderer {
    private TestShader testShader;
    private TerrainShader terrainShader;
    private ShaderProgram currentShader;

    public Renderer() {
        testShader = new TestShader();
        terrainShader = new TerrainShader();
    }

    public void render(Scene scene, Camera camera) {
        List<Terrain> terrains = scene.getTerrains();

        for (Terrain terrain : terrains) {
            currentShader = terrainShader;

            currentShader.bind();
            currentShader.setUniform("projectionMatrix", camera.getProjectionMatrix());
            currentShader.setUniform("worldMatrix", camera.getWorldMatrix());
            currentShader.setUniform("modelViewMatrix", terrain.getModelViewMatrix());
            currentShader.setUniform("color", new Vector3f(0.4f, 0.9f, 0.4f));
            currentShader.setUniform("useSun", scene.getUseSun());
            currentShader.setUniform("sunPos", scene.getSunPos());
            if (scene.getFogColor() != null) {
                currentShader.setUniform("useFog", true);
                currentShader.setUniform("fogColor", scene.getFogColor());
            } else {
                currentShader.setUniform("useFog", false);
            }

            terrain.render();

            currentShader.unbind();
        }

        List<Entity> entities = scene.getEntities();

        for (Entity entity : entities) {
            switch (entity.getMaterial()) {
                case Material.PHONG:
                    currentShader = testShader;
                    break;
                case Material.TEST:
                    currentShader = testShader;
                    break;
            }

            currentShader.bind();

            currentShader.setUniform("projectionMatrix", camera.getProjectionMatrix());
            currentShader.setUniform("worldMatrix", camera.getWorldMatrix());
            currentShader.setUniform("modelViewMatrix", entity.getModelViewMatrix());
            currentShader.setUniform("color", entity.getColor().getRGB());

            if (entity.hasTexture()) {
                currentShader.setUniform("hasTexture", true);
                currentShader.setUniform("texture_sampler", 0);
            } else {
                currentShader.setUniform("hasTexture", false);
            }

            entity.render();


            currentShader.unbind();
        }
    }

    public void cleanup() {
        testShader.cleanup();
        terrainShader.cleanup();
    }
}
