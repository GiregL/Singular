package rendering;

import org.joml.Vector3f;

public class Camera {
	
	private final Vector3f position;
	private final Vector3f rotation;
	
	public Camera() {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		this.position = pos;
		this.rotation = rot;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
	public void movePosition(float x, float y, float z) {
		if (z != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y)) * -1.0f * z;
			position.z += (float) Math.cos(Math.toRadians(rotation.y)) * z;
		}
		if (x != 0) {
			position.x += (float) Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * x;
			position.z += (float) Math.cos(Math.toRadians(rotation.y - 90)) * x;
		}
		position.y += y;
	}
	
	public Vector3f getRotation() {
		return this.rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public void moveRotation(float x, float y, float z) {
		rotation.x += x;
		rotation.y += y;
		rotation.z += z;
	}
	
}
