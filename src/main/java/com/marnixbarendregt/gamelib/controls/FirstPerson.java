package com.marnixbarendregt.gamelib.controls;

import com.marnixbarendregt.gamelib.main.Camera;
import com.marnixbarendregt.gamelib.main.Terrain;
import com.marnixbarendregt.gamelib.main.Window;
import org.joml.Vector3f;

/**
 * Created by marnixbarendregt on 23/09/2017.
 */
public class FirstPerson extends Controls {

    private boolean left, right, forward, backward, up, down = false;
    private float forwardSpeed, backwardSpeed, leftSpeed, rightSpeed;
    private Camera camera;
    private boolean canFly, isFlying = false;
    private boolean gravity;
    private float bodyHeight = 3.0f;
    private Terrain terrain;

    public FirstPerson(Camera camera, Window window, Terrain terrain, float fbSpeed, float lrSpeed, boolean canFly, boolean gravity) {
        this.camera = camera;
        this.forwardSpeed = fbSpeed;
        this.backwardSpeed = fbSpeed / 2;
        this.leftSpeed = this.rightSpeed = lrSpeed;
        this.canFly = canFly;
        this.gravity = gravity;
        this.terrain = terrain;

        if (!gravity) isFlying = true;

        new KeyListener(window) {
            @Override
            public void onPress(int key) {
                switch(key) {
                    case Keys.KEY_A:
                        left = true;
                        break;
                    case Keys.KEY_D:
                        right = true;
                        break;
                    case Keys.KEY_W:
                        forward = true;
                        break;
                    case Keys.KEY_S:
                        backward = true;
                        break;
                    case Keys.KEY_SPACE:
                        if (canFly && isFlying) {
                            up = true;
                        }

                        break;
                    case Keys.KEY_LEFT_SHIFT:
                        if (canFly && isFlying) {
                            down = true;
                        }
                        break;
                    case Keys.KEY_R:
                        if (canFly) isFlying = !isFlying;
                }
            }

            @Override
            public void onRelease(int key) {
                switch(key) {
                    case Keys.KEY_A:
                        left = false;
                        break;
                    case Keys.KEY_D:
                        right = false;
                        break;
                    case Keys.KEY_W:
                        forward = false;
                        break;
                    case Keys.KEY_S:
                        backward = false;
                        break;
                    case Keys.KEY_SPACE:
                        if (up) up = false;

                        break;
                    case Keys.KEY_LEFT_SHIFT:
                        down = false;
                        break;
                }
            }

            @Override
            public void onRepeat(int key) {

            }
        };

        MouseListener mouse = new MouseListener(window, 250f) {
            @Override
            public void onMove(float deltaX, float deltaY) {
                camera.increaseRotation(new Vector3f(deltaY, deltaX, 0.0f));
            }
        };
    }

    public void move() {
        float xOffset = 0.0f;
        float yOffset = 0.0f;
        float zOffset = 0.0f;

        float xPos = camera.getPosition().x;
        float zPos = camera.getPosition().z;
        float currentHeight = terrain.getHeightAt((int) xPos, (int) zPos);

        if ((camera.getPosition().y - currentHeight) < bodyHeight && !isFlying) {
            down = false;
            isFlying = false;
            camera.setPosition(new Vector3f(xPos, currentHeight + bodyHeight, zPos));
        }

        if (gravity && !isFlying) {
            yOffset -= 0.8f;
        }

        if (forward) {
            xOffset += moveInDirectionX(camera.getRotation().y, forwardSpeed);
            zOffset += -moveInDirectionZ(camera.getRotation().y, forwardSpeed);
        }
        if (backward) {
            xOffset += -moveInDirectionX(camera.getRotation().y, backwardSpeed);
            zOffset += moveInDirectionZ(camera.getRotation().y, backwardSpeed);
        }
        if (right) {
            xOffset += moveInDirectionX(camera.getRotation().y + 90, rightSpeed);
            zOffset += -moveInDirectionZ(camera.getRotation().y + 90, rightSpeed);
        }
        if (left) {
            xOffset += -moveInDirectionX(camera.getRotation().y + 90, leftSpeed);
            zOffset += moveInDirectionZ(camera.getRotation().y + 90, leftSpeed);
        }

        if (up && isFlying) {
            yOffset += backwardSpeed;
        }
        if (down && isFlying) {
            yOffset -= backwardSpeed;
        }

        camera.increasePosition(new Vector3f(xOffset, yOffset,  zOffset));
    }

    public float getForwardSpeed() {
        return forwardSpeed;
    }

    public void setForwardSpeed(float forwardSpeed) {
        this.forwardSpeed = forwardSpeed;
    }

    public float getBackwardSpeed() {
        return backwardSpeed;
    }

    public void setBackwardSpeed(float backwardSpeed) {
        this.backwardSpeed = backwardSpeed;
    }

    public float getLeftSpeed() {
        return leftSpeed;
    }

    public void setLeftSpeed(float leftSpeed) {
        this.leftSpeed = leftSpeed;
    }

    public float getRightSpeed() {
        return rightSpeed;
    }

    public void setRightSpeed(float rightSpeed) {
        this.rightSpeed = rightSpeed;
    }
}
