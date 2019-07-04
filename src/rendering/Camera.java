package rendering;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import core.CameraDirection;

public class Camera {
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f up;
	private Vector3f right;
	
	public CameraDirection dir;
	
	private float yaw;
	private float pitch;
	
	private boolean firstMouse;
	
	private float sensitivity;
	private float moveSpeed;
	
	private float lastX; private float lastY;
	
	public Camera() {
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.right = new Vector3f(1, 0, 0);
		this.up = new Vector3f(0, 1, 0);
		this.sensitivity = 0.05f;
		this.moveSpeed = 0.1f;
		this.lastX = Window.WIDTH / 2;
		this.lastY = Window.HEIGHT / 2;
	}
	
	public Camera(Vector3f pos, Vector3f rot) {
		this.position = pos;
		this.rotation = rot;
		this.right = new Vector3f(1, 0, 0);
		this.up = new Vector3f(0, 1, 0);
		this.sensitivity = 0.05f;
		this.moveSpeed = 0.1f;
		this.lastX = Window.WIDTH / 2;
		this.lastY = Window.HEIGHT / 2;
	}
	
	public void init(long window) {
		GLFW.glfwSetCursorEnterCallback(window, (windowhandler, isEntered) -> {
			if (isEntered) {
				this.firstMouse = true;
			}
		});
		
		GLFW.glfwSetCursorPosCallback(window, (windowhandler, xpos, ypos) -> {
			
		    if(firstMouse)
		    {
		        lastX = (float) xpos;
		        lastY = (float) ypos;
		        firstMouse = false;
		    }
			
			float xOffset = (float) (xpos - lastX); xOffset *= sensitivity;
			float yOffset = (float) (lastY - ypos); yOffset *= sensitivity;
			lastX = (float) xpos; lastY = (float) ypos;
			
			yaw += xOffset; pitch += yOffset;
			if (pitch > 90.0f) pitch = 90.0f;
			if (pitch < -90.0f) pitch = -90.0f;
			
			updateCameraVectors();
		});
	}
	
	private void updateCameraVectors() {
		this.rotation.x = (float) (Math.cos(Math.toRadians(this.pitch)) * Math.cos(Math.toRadians(this.yaw)));
		this.rotation.y = (float) Math.sin(Math.toRadians((double) pitch));
		this.rotation.z = (float) (Math.cos(Math.toRadians(this.pitch)) * Math.sin(Math.toRadians(this.yaw)));
		this.rotation = this.rotation.normalize();
		this.right = this.rotation.cross(new Vector3f(0, 1, 0)).normalize();
		this.up = this.right.cross(this.rotation).normalize();
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
	public void processInput() {
		if (dir == CameraDirection.FRONT) {
			this.position = this.position.add(this.rotation.mul(this.moveSpeed));
		}
		if (dir == CameraDirection.BACK) {
			this.position = this.position.sub(this.rotation.mul(this.moveSpeed));
		}
		if (dir == CameraDirection.LEFT) {
			this.position = this.position.sub(this.rotation.cross(this.up).mul(this.moveSpeed)).normalize();
		}
		if (dir == CameraDirection.RIGHT) {
			this.position = this.position.add(this.rotation.cross(this.up).mul(this.moveSpeed)).normalize();
		}
		if (dir == CameraDirection.UP) {
			this.position.y += this.moveSpeed;
		}
		if (dir == CameraDirection.DOWN) {
			this.position.y -= this.moveSpeed;
		}
	}
	
	public Vector3f getRotation() {
		return this.rotation;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}

	public float getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(float sensitivity) {
		this.sensitivity = sensitivity;
	}

	
	public Vector3f getUp() {
		return up;
	}

	public Vector3f getRight() {
		return right;
	}
	
	
	
}
