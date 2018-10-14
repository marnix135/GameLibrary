package com.marnixbarendregt.gamelib.controls;

import com.marnixbarendregt.gamelib.main.Window;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by marnixbarendregt on 18/09/2017.
 */
public abstract class KeyListener {

    public KeyListener(Window window) {
        glfwSetKeyCallback(window.getWindow(), (window2, key, scancode, action, mods) -> {
            switch (action) {
                case GLFW_PRESS:
                    onPress(key);
                    break;

                case GLFW_RELEASE:
                    onRelease(key);
                    break;

                case GLFW_REPEAT:
                    onRepeat(key);
                    break;
            }
        });
    }

    public abstract void onPress(int key);
    public abstract void onRelease(int key);
    public abstract void onRepeat(int key);
}
