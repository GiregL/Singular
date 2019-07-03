package core;

import org.joml.Vector3f;

import rendering.Mesh;

public class GameItem {
	
	private final Mesh mesh;
	private final Vector3f position;
	private float scale;
	private final Vector3f rotation;
	
	public GameItem(Mesh mesh) {
		this.mesh = mesh;
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
		scale = 1.0f;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getRotation() {
		return rotation;
	}
	
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float x) {
		this.scale = x;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
}
