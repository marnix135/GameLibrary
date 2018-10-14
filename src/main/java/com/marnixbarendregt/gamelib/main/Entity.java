package com.marnixbarendregt.gamelib.main;


import com.marnixbarendregt.gamelib.mesh.Mesh;
import com.marnixbarendregt.gamelib.utils.Color;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Created by marnixbarendregt on 20/09/2017.
 */
public class Entity {

    private Mesh mesh;
    private int material;
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    private Matrix4f modelViewMatrix;
    private int id = -1;
    private Color color;
    private Texture texture;
    private boolean hasTexture = false;

    public Entity(Mesh mesh, int material, Vector3f position, Vector3f rotation, float scale) {
        this.mesh = mesh;
        this.material = material;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        this.modelViewMatrix = new Matrix4f();

        this.color = new Color(0.0f, 0.0f, 0.0f);
    }

    public Entity(Mesh mesh, int material, Color color, Vector3f position, Vector3f rotation, float scale) {
        this.mesh = mesh;
        this.material = material;

        this.color = color;

        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        this.modelViewMatrix = new Matrix4f();

        this.color = color;
    }

    public Entity(Mesh mesh, int material, Texture texture, Vector3f position, Vector3f rotation, float scale) {
        this.mesh = mesh;
        this.material = material;

        this.texture = texture;

        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        this.modelViewMatrix = new Matrix4f();

        this.color = new Color(0.0f, 0.0f, 0.0f);

        this.hasTexture = true;
    }

    public void render() {
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);

        if (hasTexture) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.getId());
            glEnableVertexAttribArray(1);
        }
        if (mesh.hasNormals()) {
            glEnableVertexAttribArray(2);
        }


        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        if (hasTexture) {
            glDisableVertexAttribArray(1);
        }
        if (mesh.hasNormals()) {
            glDisableVertexAttribArray( 2);
        }

        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public Matrix4f getModelViewMatrix() {
        modelViewMatrix.identity();
        modelViewMatrix.translate(position);
        modelViewMatrix.rotate((float) Math.toRadians(-rotation.x), new Vector3f(1, 0, 0));
        modelViewMatrix.rotate((float) Math.toRadians(-rotation.y), new Vector3f(0, 1, 0));
        modelViewMatrix.rotate((float) Math.toRadians(-rotation.z), new Vector3f(0, 0, 1));
        modelViewMatrix.scale(scale);

        return modelViewMatrix;
    }

    public int getMaterial() {
        return material;
    }

    // Position
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void increasePosition(Vector3f offset) {
        this.position.add(offset);
    }

    // Rotation
    public Vector3f getRotation() {
        checkRotation();
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
        checkRotation();
    }

    public void increaseRotation(Vector3f rotationChange) {
        this.rotation.add(rotationChange);
        checkRotation();
    }


    // Scale
    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void increaseScale(float scaleChange) {
        this.scale += scaleChange;
    }




    public void setId(int id) {
        this.id = id;
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void checkRotation() {
        rotation.x = rotation.x % 360;
        rotation.y = rotation.y % 360;
        rotation.z = rotation.z % 360;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean hasTexture() { return hasTexture; }
}
