package com.marnixbarendregt.gamelib.mesh;

import com.marnixbarendregt.gamelib.utils.OBJParser;

import java.io.IOException;

/**
 * Created by marnixbarendregt on 22/09/2017.
 */
public class Cube {

    public static Mesh getMesh() {
        try {
            return OBJParser.loadMesh("resources/models/cube.obj");
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
