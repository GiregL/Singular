package core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import core.input.MouseInput;
import core.model.CubeFactory;
import core.world.GameItem;
import rendering.Camera;
import rendering.Mesh;
import rendering.Window;
import rendering.shaders.ShaderProgram;
import util.ParameterFunction;
import util.ResourceLoader;

public class Main {
	
	public static void main(String[] args) {
		Window window = new Window("Singular", 1080, 720);
		
		ShaderProgram shader;
		shader = new ShaderProgram();
		shader.createVertexShader(ResourceLoader.getResource("resources/shaders/vert.glsl"));
		shader.createFragmentShader(ResourceLoader.getResource("resources/shaders/frag.glsl"));
		shader.link();
		
		shader.bind();
		shader.createUniform("modelview_matrix");
		shader.createUniform("projection_matrix");
		shader.unbind();
		
		float[] colors = new float[] {
				1.0f, 0.0f, 0.0f,
				0.5f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 0.5f, 0.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 0.0f, 0.5f,
				1.0f, 1.0f, 0.0f,
				0.0f, 1.0f, 1.0f
		};
		
		Mesh cubeMesh = new Mesh(CubeFactory.getVertices(), colors, CubeFactory.getIndices());
		
		GameItem cube1 = new GameItem(cubeMesh);
		GameItem cube2 = new GameItem(cubeMesh);
		GameItem cube3 = new GameItem(cubeMesh);
		
		cube1.setPosition(-5.0f, 0.0f, 0.0f);
		cube2.setPosition(0.0f, -5.0f, 0.0f);
		cube3.setPosition(5.0f, 0.0f, 0.0f);
		
		List<GameItem> items = new ArrayList<GameItem>();
		items.add(cube1); items.add(cube2); items.add(cube3);
		
		MouseInput mouse = new MouseInput();
		mouse.init(window.getWindow());
		Camera cam = new Camera();
		cam.setPosition(0.0f, 0.0f, 10.0f);
		
		Transformation transformation = new Transformation();
		
		final float moveSpeed = 0.1f;
		
		ParameterFunction render = () -> {
			
			Matrix4f projectionMatrix = transformation.getProjectionMatrix(70.0f, 1080, 720, 0.1f, 1000.0f);
			Matrix4f viewMatrix = transformation.getViewMatrix(cam);
			
			for (GameItem item : items) {
				Matrix4f modelViewMatrix = transformation.getModelViewMatrix(item, viewMatrix);
				
				shader.bind();
				
				shader.setUniform("modelview_matrix", modelViewMatrix);
				shader.setUniform("projection_matrix", projectionMatrix);
				
				item.getMesh().render();
				
				shader.unbind();
			}
		};
		
		ParameterFunction update = () -> {
			if (cam.goFront) cam.movePosition(0.0f, 0.0f, -moveSpeed);
			if (cam.goBack) cam.movePosition(0.0f, 0.0f, moveSpeed);
			if (cam.goLeft) cam.movePosition(-moveSpeed, 0.0f, 0.0f);
			if (cam.goRight) cam.movePosition(moveSpeed, 0.0f, 0.0f);
			if (cam.goUp) cam.movePosition(0.0f, moveSpeed, 0.0f);
			if (cam.goDown) cam.movePosition(0.0f, -moveSpeed, 0.0f);
			
		};
		
		GLFWKeyCallbackI input = (inputWindow, key, scancode, action, mods) -> {
			if (action == GLFW.GLFW_PRESS) {
				switch (key) {
				case GLFW.GLFW_KEY_W:
					cam.goFront = true; break;
				case GLFW.GLFW_KEY_A:
					cam.goLeft = true; break;
				case GLFW.GLFW_KEY_S:
					cam.goBack = true; break;
				case GLFW.GLFW_KEY_D:
					cam.goRight = true; break;
				case GLFW.GLFW_KEY_SPACE:
					cam.goUp = true; break;
				case GLFW.GLFW_KEY_LEFT_SHIFT:
					cam.goDown = true; break;
				}
			} else if (action == GLFW.GLFW_RELEASE) {
				switch (key) {
				case GLFW_KEY_ESCAPE:
					GLFW.glfwSetWindowShouldClose(window.getWindow(), true); break;
				case GLFW.GLFW_KEY_W:
					cam.goFront = false; break;
				case GLFW.GLFW_KEY_A:
					cam.goLeft = false; break;
				case GLFW.GLFW_KEY_S:
					cam.goBack = false; break;
				case GLFW.GLFW_KEY_D:
					cam.goRight = false; break;
				case GLFW.GLFW_KEY_SPACE:
					cam.goUp = false; break;
				case GLFW.GLFW_KEY_LEFT_SHIFT:
					cam.goDown = false; break;
				}
			}
			
			mouse.input();
		};
		
		window.loop(render, update, input);
	}
	
}
