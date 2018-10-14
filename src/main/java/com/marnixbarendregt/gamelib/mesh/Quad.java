package com.marnixbarendregt.gamelib.mesh;

/**
 * Created by marnixbarendregt on 22/09/2017.
 */
public class Quad {

    public static Mesh getMesh() {
        float[] positions = {
                -0.2f,  0.2f, 0f, 1f,
                -0.2f, -0.2f, 0f, 1f,
                0.2f, -0.2f, 0f, 1f,
                0.2f, 0.2f, 0f, 1f,

                0.3f,  0.2f, 0f, 1f,
                0.3f, -0.2f, 0f, 1f,
                0.6f, -0.2f, 0f, 1f,
                0.6f, 0.2f, 0f, 1f};

        int[] indices = {
                0, 1, 2,
                2, 3, 0,


        };

        return new Mesh(positions, indices);
    }
}
