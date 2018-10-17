package com.marnixbarendregt.gamelib.shaders;

public class TerrainShader extends ShaderProgram {

    public TerrainShader() {
        super("resources/shaders/terrain/terrain.vert", "resources/shaders/terrain/terrain.frag");

        try {
            createUniform("projectionMatrix");
            createUniform("worldMatrix");
            createUniform("modelViewMatrix");
            createUniform("color");
            createUniform("sunPos");
            createUniform("useSun");
            createUniform("useFog");
            createUniform("fogColor");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
