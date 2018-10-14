package com.marnixbarendregt.gamelib.main;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by marnixbarendregt on 20/09/2017.
 */
public class Camera {

    private final float FOV, NEAR, FAR;
    private Matrix4f projectionMatrix;
    private Matrix4f worldMatrix;
    private Vector3f position;
    private Vector3f rotation;
    private boolean customMatrix = false;

    public Camera(Window window, float pov, float near, float far) {
        this.FOV = (float) Math.toRadians(pov);
        this.NEAR = near;
        this.FAR = far;

        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.rotation = new Vector3f(0.0f, 0.0f, 0.0f);

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        this.projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, NEAR, FAR);
        this.worldMatrix = new Matrix4f();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getWorldMatrix() {
        if (!customMatrix) {
            worldMatrix.identity();
            worldMatrix.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
            worldMatrix.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
            worldMatrix.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
            worldMatrix.translate(position.negate());

            position.negate();
        }

        return worldMatrix;
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

    public void checkRotation() {
        rotation.x = rotation.x % 360;
        rotation.y = rotation.y % 360;
        rotation.z = rotation.z % 360;
    }


    public void setCustomMatrix(Matrix4f matrix) {
        customMatrix = true;
        this.worldMatrix = matrix;
    }

    public void disableCustomMatrix() {
        customMatrix = false;
    }
}
