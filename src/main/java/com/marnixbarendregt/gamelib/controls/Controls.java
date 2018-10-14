package com.marnixbarendregt.gamelib.controls;

/**
 * Created by marnixbarendregt on 20/09/2017.
 */
public abstract class Controls {
    // TODO: No clue how to implement different types of controls: FPS, Third Person, Keyboard, Gamepad
    public abstract void move();

    public float moveInDirectionX(float theta, float speed) {
        return (float) (Math.sin(Math.toRadians(theta)) * speed);
    }

    public float moveInDirectionY(float theta, float speed) {
        return (float) (Math.cos(Math.toRadians(theta)) * speed);
    }
}
