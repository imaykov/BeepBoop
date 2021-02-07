package com.maykov.game.beepboop.entities;

import org.lwjgl.input.Mouse;

public class ThirdPersonCamera extends Camera {
    private Entity following;
    private float distanceFrom = 50;
    private float angleAroundEntity = 0;


    public ThirdPersonCamera(Entity following) {
        super();
        this.following = following;
        pitch = 30;
    }

    public void move() {
        calculateZoom();
        calculatePitch();
        calculateAngleAroundEntity();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculatePosition(horizontalDistance, verticalDistance);
        

    }
    

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * .1f;
        distanceFrom -= zoomLevel;
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * .1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundEntity() {
        if(Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * .3f;
            angleAroundEntity -= angleChange;
        }
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFrom * Math.cos(Math.toRadians(pitch)));
    }
    private float calculateVerticalDistance() {
        return (float) (distanceFrom * Math.sin(Math.toRadians(pitch)));
    }

    private void calculatePosition(float horizontalDistance, float verticalDistance) {
        // Set the y-coordinate of camera
        position.y = following.getPosition().y + verticalDistance;

        // Set the x and z position of the camera
        float angleRot = following.getRotY() + angleAroundEntity /* + 180 */;
        yaw = 180 - angleRot;
        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(angleRot)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(angleRot)));
        position.x = following.getPosition().x - xOffset;
        position.z = following.getPosition().z - zOffset;


    }


}
