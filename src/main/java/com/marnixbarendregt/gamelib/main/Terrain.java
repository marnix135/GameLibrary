package com.marnixbarendregt.gamelib.main;

import com.marnixbarendregt.gamelib.mesh.Mesh;
import com.marnixbarendregt.gamelib.utils.Noise;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Created by marnixbarendregt on 27/09/2017.
 */
public class Terrain {

    private int width;
    private int depth;
    private int indicesSize;
    private Mesh mesh;
    private Matrix4f modelViewMatrix;

    public Terrain(int width, int depth) {
        this.width = width;
        this.depth = depth;
        this.modelViewMatrix = new Matrix4f().identity();

        float[][] heights = new float[width][depth];

        float[] vertices = new float[width * depth * 3];
        int[] indices = new int[6 * (width - 1) * (depth - 1)];

        this.indicesSize = indices.length;

        Noise noise = new Noise();


        int pointer = 0;

        for (int z = 0; z < depth; z++) {
            for (int x = 0; x < width; x++) {
                int index = x + z * width;

                // heights
                float y = noise.generateTerrainNoise((float) x * 0.1f, (float) z * 0.1f, 45.0f, 3f, 0.3f);
                heights[x][z] = y;

                // Vertices
                vertices[3 * index] = (float) x;
                vertices[3 * index + 1] = y;
                vertices[3 * index + 2] = (float) z;


                // Indices
                if (z < (depth - 1) && x < (width - 1)) {
                    int topLeft = x + z * width;
                    int topRight = (x + 1) + z * width;
                    int bottomLeft = x + (z + 1) * width;
                    int bottomRight = (x + 1) + (z + 1) * width;

                    indices[pointer++] = topLeft;
                    indices[pointer++] = bottomLeft;
                    indices[pointer++] = topRight;
                    indices[pointer++] = topRight;
                    indices[pointer++] = bottomLeft;
                    indices[pointer++] = bottomRight;
                }
            }
        }
        float[] normals = calcNormals(vertices);

        this.mesh = new Mesh(vertices, indices, new float[0], normals);
    }


    public void render() {
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, indicesSize, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }

    public Matrix4f getModelViewMatrix() {
        modelViewMatrix.identity();
        modelViewMatrix.translate(new Vector3f(0.0f, 0.0f, 0.0f));
        modelViewMatrix.rotate((float) Math.toRadians(0.0f), new Vector3f(1, 0, 0));
        modelViewMatrix.rotate((float) Math.toRadians(0.0f), new Vector3f(0, 1, 0));
        modelViewMatrix.rotate((float) Math.toRadians(0.0f), new Vector3f(0, 0, 1));
        modelViewMatrix.scale(1.0f);

        return modelViewMatrix;
    }

    private float[] calcNormals(float[] posArr) {
        Vector3f v0 = new Vector3f();
        Vector3f v1 = new Vector3f();
        Vector3f v2 = new Vector3f();
        Vector3f v3 = new Vector3f();
        Vector3f v4 = new Vector3f();
        Vector3f v12 = new Vector3f();
        Vector3f v23 = new Vector3f();
        Vector3f v34 = new Vector3f();
        Vector3f v41 = new Vector3f();
        List<Float> normals = new ArrayList<>();
        Vector3f normal = new Vector3f();
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < width; col++) {
                if (row > 0 && row < depth -1 && col > 0 && col < width -1) {
                    int i0 = row*width*3 + col*3;
                    v0.x = posArr[i0];
                    v0.y = posArr[i0 + 1];
                    v0.z = posArr[i0 + 2];

                    int i1 = row*width*3 + (col-1)*3;
                    v1.x = posArr[i1];
                    v1.y = posArr[i1 + 1];
                    v1.z = posArr[i1 + 2];
                    v1 = v1.sub(v0);

                    int i2 = (row+1)*width*3 + col*3;
                    v2.x = posArr[i2];
                    v2.y = posArr[i2 + 1];
                    v2.z = posArr[i2 + 2];
                    v2 = v2.sub(v0);

                    int i3 = (row)*width*3 + (col+1)*3;
                    v3.x = posArr[i3];
                    v3.y = posArr[i3 + 1];
                    v3.z = posArr[i3 + 2];
                    v3 = v3.sub(v0);

                    int i4 = (row-1)*width*3 + col*3;
                    v4.x = posArr[i4];
                    v4.y = posArr[i4 + 1];
                    v4.z = posArr[i4 + 2];
                    v4 = v4.sub(v0);

                    v1.cross(v2, v12);
                    v12.normalize();

                    v2.cross(v3, v23);
                    v23.normalize();

                    v3.cross(v4, v34);
                    v34.normalize();

                    v4.cross(v1, v41);
                    v41.normalize();

                    normal = v12.add(v23).add(v34).add(v41);
                    normal.normalize();
                } else {
                    normal.x = 0;
                    normal.y = 1;
                    normal.z = 0;
                }
                normal.normalize();
                normals.add(normal.x);
                normals.add(normal.y);
                normals.add(normal.z);
            }
        }

        float[] normalArr = new float[normals.size()];
        for (int i = 0; i < normals.size(); i++) {
            normalArr[i] = normals.get(i);
        }
        return normalArr;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
