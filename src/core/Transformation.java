package core;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import core.world.GameItem;
import rendering.Camera;

public class Transformation {
	
	private final Matrix4f projectionMatrix;
	private final Matrix4f viewMatrix;
	private final Matrix4f modelViewMatrix;
	
	public Transformation() {
		modelViewMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}
	
	public Matrix4f getViewMatrix(Camera cam) {
		Vector3f pos = cam.getPosition();
		Vector3f rot = cam.getRotation();
		viewMatrix.identity();
		viewMatrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1, 0, 0))
			.rotate((float) Math.toRadians(rot.y), new Vector3f(0, 1, 0));
		
		viewMatrix.translate(-pos.x, -pos.y, -pos.z);
		return viewMatrix;
		
	}
	
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float znear, float zfar) {
		float ratio = width / height;
		projectionMatrix.identity();
		projectionMatrix.perspective(fov, ratio, znear, zfar);
		return projectionMatrix;
	}
	
	public Matrix4f getModelViewMatrix(GameItem item, Matrix4f matrix) {
		Vector3f rotation = item.getRotation();
		modelViewMatrix.identity().translate(item.getPosition()).
	        rotateX((float)Math.toRadians(-rotation.x)).
	        rotateY((float)Math.toRadians(-rotation.y)).
	        rotateZ((float)Math.toRadians(-rotation.z)).
	        scale(item.getScale());
	    Matrix4f viewCurr = new Matrix4f(viewMatrix);
	    return viewCurr.mul(modelViewMatrix);
	}
	
}
