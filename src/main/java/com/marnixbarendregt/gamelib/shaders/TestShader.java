package com.marnixbarendregt.gamelib.shaders;

/**
 * Created by marnixbarendregt on 21/09/2017.
 */
public class TestShader extends ShaderProgram {

    public TestShader() {
        super("resources/shaders/test/test.vert", "resources/shaders/test/test.frag");

        try {
            createUniform("projectionMatrix");
            createUniform("worldMatrix");
            createUniform("modelViewMatrix");
            createUniform("color");
            createUniform("texture_sampler");
            createUniform("hasTexture");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
