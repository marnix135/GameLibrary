package com.marnixbarendregt.gamelib.utils;

import org.joml.Vector3f;

/**
 * Created by marnixbarendregt on 18/09/2017.
 */
public class Color {

    private float r;
    private float g;
    private float b;
    private float a;
    private boolean useAlpha = false;

    public Color(int r, int g, int b) {
        this.r = (float) r / 255;
        this.g = (float) g / 255;
        this.b = (float) b / 255;

    }

    public Color(int r, int g, int b, int a) {
        this.r = (float) r / 255;
        this.g = (float) g / 255;
        this.b = (float) b / 255;
        this.a = (float) a / 255;
        this.useAlpha = true;
    }

    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.useAlpha = true;
    }

    public Vector3f getRGB() {
        return new Vector3f(r, g, b);
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getA() {
        if (!useAlpha) {
            return 1.0f;
        }

        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public boolean hasAlpha() {
        return useAlpha;
    }
}
