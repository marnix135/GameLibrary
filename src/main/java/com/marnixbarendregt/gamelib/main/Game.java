package com.marnixbarendregt.gamelib.main;

import com.marnixbarendregt.gamelib.controls.Controls;
import com.marnixbarendregt.gamelib.utils.Color;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by marnixbarendregt on 18/09/2017.
 */
public abstract class Game {

    private Window currentWindow;
    private long window;
    private Controls controls;

    public void init(Window startingWindow) {
        GL.createCapabilities();

        this.currentWindow = startingWindow;
        this.window = currentWindow.getWindow();

        System.out.println("Starting engine. LWJGL Version " + Version.getVersion());

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);

        loop();

        onClose();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void loop() {
        init();
        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;

        while ( !glfwWindowShouldClose(window) ) {
            long nowTime = System.nanoTime();
            delta += (nowTime - lastTime) / ns;
            lastTime = nowTime;
            if (delta > 1.0) {
                // Update
                update();
                if (controls != null) {
                    controls.move();
                }

                delta--;
            }

            // Render
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            render();

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }

    public abstract void init();
    public abstract void update();
    public abstract void render();
    public abstract void onClose();

    public void setClearColor(Color color) {
        glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
    }

    public void close() {
        glfwSetWindowShouldClose(window, true);
    }
    public void useControls(Controls controls) {
        this.controls = controls;
    }
}
