package core;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	private float roll;
	
	private float moveSpeed = 0.1f;
	private float sensitivity = 0.1f;
	
	private float lastX, lastY, xOffset, yOffset;
	
	private boolean goFront, goBack, goLeft, goRight, goUp, goDown;
	
	public Camera() {
	}
	
	public void mouseInput(long window) {
		GLFW.glfwSetCursorPosCallback(window, (windowHandle, xpos, ypos) -> {
			xOffset = (float) xpos - lastX;
			yOffset = (float) ypos - lastY;
			calculateYaw(xOffset);
			calculatePitch(yOffset);
			
			lastX = (float) xpos;
			lastY = (float) ypos;
		});
	}
	
	public void calculatePitch(float deltaY) {
		float pitchChange = deltaY * sensitivity;
		pitch += pitchChange;
		if (pitch > 90.0f) pitch = 90.0f;
		if (pitch < -90.0f) pitch = -90.0f;
	}
	
	public void calculateYaw(float deltaX) {
		float pitchChange = deltaX * sensitivity;
		yaw += pitchChange;
	}
	
	public void update() {
		if (goFront) {
			position.x += ((float) Math.sin(Math.toRadians(yaw))) * moveSpeed;
			position.z -= ((float) Math.cos(Math.toRadians(yaw))) * moveSpeed;
		}
		if (goBack) {
			position.x -= ((float) Math.sin(Math.toRadians(yaw))) * moveSpeed;
			position.z += ((float) Math.cos(Math.toRadians(yaw))) * moveSpeed;
		}
		
		if (goLeft) {
			position.x += ((float) Math.sin(Math.toRadians(yaw - 90))) * moveSpeed;
			position.z -= ((float) Math.cos(Math.toRadians(yaw - 90))) * moveSpeed;
		}
		if (goRight) {
			position.x += ((float) Math.sin(Math.toRadians(yaw + 90))) * moveSpeed;
			position.z -= ((float) Math.cos(Math.toRadians(yaw + 90))) * moveSpeed;
		}
		
		if (goUp) position.y += moveSpeed;
		if (goDown) position.y -= moveSpeed;
	}
	
	public void processKeyboardInput(long inputWindow, int key, int scancode, int action, int mods) {
		if (action == GLFW.GLFW_PRESS) {
			switch (key) {
			case GLFW.GLFW_KEY_W:
				goFront = true;
				break;
			case GLFW.GLFW_KEY_A:
				goLeft = true;
				break;
			case GLFW.GLFW_KEY_S:
				goBack = true;
				break;
			case GLFW.GLFW_KEY_D:
				goRight = true;;
				break;
			case GLFW.GLFW_KEY_SPACE:
				goUp = true;
				break;
			case GLFW.GLFW_KEY_LEFT_SHIFT:
				goDown = true;
				break;
			}
		} else if (action == GLFW.GLFW_RELEASE) {
			switch (key) {
			case GLFW.GLFW_KEY_W:
				goFront = false;
				break;
			case GLFW.GLFW_KEY_A:
				goLeft = false;
				break;
			case GLFW.GLFW_KEY_S:
				goBack = false;
				break;
			case GLFW.GLFW_KEY_D:
				goRight = false;;
				break;
			case GLFW.GLFW_KEY_SPACE:
				goUp = false;
				break;
			case GLFW.GLFW_KEY_LEFT_SHIFT:
				goDown = false;
				break;
			}
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
}
