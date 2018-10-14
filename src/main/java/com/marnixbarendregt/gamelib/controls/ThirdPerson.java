package com.marnixbarendregt.gamelib.controls;

import com.marnixbarendregt.gamelib.main.Camera;
import com.marnixbarendregt.gamelib.main.Entity;
import com.marnixbarendregt.gamelib.main.Window;
import org.joml.Vector3f;

/**
 * Created by marnixbarendregt on 24/09/2017.
 */
public class ThirdPerson extends Controls {

    private boolean left, right, forward, backward = false;
    private float forwardSpeed, backwardSpeed, leftSpeed, rightSpeed;
    private Camera camera;
    private MouseListener mouse;
    private Entity player;
    private float cameraDistance;
    private float cameraHeight;
    private float cameraAngle;
    private float cameraYOffset;

    public ThirdPerson(Camera camera, Window window, Entity player, float fbSpeed, float lrSpeed) {
        this.camera = camera;
        this.player = player;
        this.forwardSpeed = fbSpeed;
        this.backwardSpeed = fbSpeed / 2;
        this.leftSpeed = this.rightSpeed = lrSpeed;
        this.cameraDistance = 8.0f;
        this.cameraHeight = 3f;
        this.cameraAngle = (float) Math.toDegrees(Math.atan(cameraHeight / cameraDistance));
        this.cameraYOffset = 2.0f;


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
                }
            }

            @Override
            public void onRepeat(int key) {

            }
        };

        mouse = new MouseListener(window, 250.0f) {
            @Override
            public void onMove(float deltaX, float deltaY) {
                player.increaseRotation(new Vector3f(0.0f, deltaX, 0.0f));
            }
        };

        player.setPosition(new Vector3f(0.0f, 0.0f, -1.0f));
    }

    public void move() {
        float xOffset = 0.0f;
        float zOffset = 0.0f;

        if (forward) {
            xOffset += moveInDirectionX(player.getRotation().y, forwardSpeed);
            zOffset += -moveInDirectionZ(player.getRotation().y, forwardSpeed);
        }
        if (backward) {
            xOffset += -moveInDirectionX(player.getRotation().y, backwardSpeed);
            zOffset += moveInDirectionZ(player.getRotation().y, backwardSpeed);
        }
        if (right) {
            xOffset += moveInDirectionX(player.getRotation().y + 90, rightSpeed);
            zOffset += -moveInDirectionZ(player.getRotation().y + 90, rightSpeed);
        }
        if (left) {
            xOffset += -moveInDirectionX(player.getRotation().y + 90, leftSpeed);
            zOffset += moveInDirectionZ(player.getRotation().y + 90, leftSpeed);
        }

        player.increasePosition(new Vector3f(xOffset, 0.0f,  zOffset));

        // Camera movement
        float cameraX = player.getPosition().x - (float) (Math.sin(Math.toRadians(player.getRotation().y)) * cameraDistance);
        float cameraZ = player.getPosition().z + (float) (Math.cos(Math.toRadians(player.getRotation().y)) * cameraDistance);

        camera.setPosition(new Vector3f(cameraX, cameraHeight + cameraYOffset, cameraZ));
        camera.setRotation(new Vector3f(cameraAngle, (player.getRotation().y) % 360, 0.0f));
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

    public Entity getPlayer() {
        return player;
    }

    public void setPlayer(Entity player) {
        this.player = player;
    }

    public float getCameraDistance() {
        return cameraDistance;
    }

    public void setCameraDistance(float cameraDistance) {
        this.cameraDistance = cameraDistance;
    }

    public float getCameraHeight() {
        return cameraHeight;
    }

    public void setCameraHeight(float cameraHeight) {
        this.cameraHeight = cameraHeight;
    }

    public float getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(float cameraAngle) {
        this.cameraAngle = cameraAngle;
    }
}
