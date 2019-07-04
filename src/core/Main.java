package core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

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
		
		Camera cam = new Camera();
		cam.init(window.getWindow());
		cam.setPosition(0.0f, 0.0f, 10.0f);
		
		Transformation transformation = new Transformation();
		
		cam.dir = CameraDirection.NULL;
		
		
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
			cam.processInput();
		};
		
		GLFWKeyCallbackI input = (inputWindow, key, scancode, action, mods) -> {
			if (action == GLFW.GLFW_PRESS) {
				switch (key) {
				case GLFW.GLFW_KEY_W:
					cam.dir = CameraDirection.FRONT; break;
				case GLFW.GLFW_KEY_A:
					cam.dir = CameraDirection.LEFT; break;
				case GLFW.GLFW_KEY_S:
					cam.dir = CameraDirection.BACK; break;
				case GLFW.GLFW_KEY_D:
					cam.dir = CameraDirection.RIGHT; break;
				case GLFW.GLFW_KEY_SPACE:
					cam.dir = CameraDirection.UP; break;
				case GLFW.GLFW_KEY_LEFT_SHIFT:
					cam.dir = CameraDirection.DOWN; break;
				}
			} else if (action == GLFW.GLFW_RELEASE) {
				switch (key) {
				case GLFW_KEY_ESCAPE:
					GLFW.glfwSetWindowShouldClose(window.getWindow(), true); break;
				case GLFW.GLFW_KEY_W:
					cam.dir = CameraDirection.NULL; break;
				case GLFW.GLFW_KEY_A:
					cam.dir = CameraDirection.NULL; break;
				case GLFW.GLFW_KEY_S:
					cam.dir = CameraDirection.NULL; break;
				case GLFW.GLFW_KEY_D:
					cam.dir = CameraDirection.NULL; break;
				case GLFW.GLFW_KEY_SPACE:
					cam.dir = CameraDirection.NULL; break;
				case GLFW.GLFW_KEY_LEFT_SHIFT:
					cam.dir = CameraDirection.NULL; break;
				}
			}
		};
		
		window.loop(render, update, input);
	}
	
}
