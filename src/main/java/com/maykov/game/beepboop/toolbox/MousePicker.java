package com.maykov.game.beepboop.toolbox;

import com.maykov.game.beepboop.entities.Camera;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class MousePicker {
    private Vector3f currentRay;

    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;
    private Camera camera;

    public MousePicker(Camera camera, Matrix4f projectionMatrix) {
        this.camera = camera;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = MathTool.createViewMatrix(camera);
    }

    public Vector3f getCurrentRay() {
        return currentRay;
    }

    public void update() {
        viewMatrix = MathTool.createViewMatrix(camera);
        currentRay = claculateMouseRay();

    }

    private Vector3f claculateMouseRay() {
        Vector3f result = null;
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();
        Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        result = toWorldCoords(eyeCoords);

        return result;
    }

    private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
        Vector2f result = new Vector2f();
        result.x = (2f * mouseX) / Display.getDisplayMode().getWidth() - 1f;
        result.y = (2f * mouseY) / Display.getDisplayMode().getHeight() - 1f;


        return result;
    }

    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjectionMatrix = Matrix4f.invert(projectionMatrix, null);
        Vector4f eyeCoords = Matrix4f.transform(invertedProjectionMatrix, clipCoords, null);

        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }

    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedViewMatrix = Matrix4f.invert(viewMatrix, null);
        Vector4f rayWorld = Matrix4f.transform(invertedViewMatrix, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        mouseRay.normalise();

        return mouseRay;

    }
    
}
