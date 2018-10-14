package com.marnixbarendregt.gamelib.controls;

import com.marnixbarendregt.gamelib.main.Window;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Created by marnixbarendregt on 23/09/2017.
 */
public abstract class MouseListener {

    private Window window;

    private double prevX;
    private double prevY;
    private double sensitivity;

    private double width;
    private double height;


    public MouseListener(Window window, float sensitivity) {
        this.window = window;
        this.sensitivity = (double) sensitivity;

        this.width = (double) window.getWidth();
        this.height = (double) window.getHeight();
        this.prevX = this.width / 2;
        this.prevY = this.height / 2;

        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetCursorPosCallback(window.getWindow(), (newWindow, newX, newY) -> {
            double deltaX = (newX - width / 2) / width / 2;
            double deltaY = (newY - height / 2) / height / 2;

            if (newX != prevX || newY != prevY) {
                onMove((float) (deltaX * sensitivity), (float) (deltaY * sensitivity));
            }

            prevX = newX;
            prevY = newY;

            glfwSetCursorPos(window.getWindow(), width / 2, height / 2);
        });
    }

    public abstract void onMove(float deltaX, float deltaY);
}
