package core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {
	
	private final Matrix4f projectionMatrix;
	private final Matrix4f viewMatrix;
	
	public Transformation() {
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}
	
	public Matrix4f getViewMatrix(Camera cam) {
		viewMatrix.identity();
		viewMatrix.rotate((float) Math.toRadians(cam.getPitch()), new Vector3f(1, 0, 0));
		viewMatrix.rotate((float) Math.toRadians(cam.getYaw()), new Vector3f(0, 1, 0));
		
		Vector3f negativeCameraPos = new Vector3f(-cam.getPosition().x, -cam.getPosition().y, -cam.getPosition().z);
		
		viewMatrix.translate(negativeCameraPos);
		
		return viewMatrix;
		
	}
	
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float znear, float zfar) {
		float ratio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, ratio, znear, zfar);
		return projectionMatrix;
	}
}
