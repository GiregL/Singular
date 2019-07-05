package core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import core.model.CubeFactory;
import core.world.GameItem;
import core.Camera;
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
		shader.createUniform("view_matrix");
		shader.createUniform("model_matrix");
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
		cam.mouseInput(window.getWindow());
		
		Transformation transformation = new Transformation();
		
		ParameterFunction render = () -> {
			Matrix4f projectionMatrix = transformation.getProjectionMatrix(70.0f, 1080, 720, 0.1f, 1000.0f);
			Matrix4f viewMatrix = transformation.getViewMatrix(cam);
			
			
			for (GameItem item : items) {
				
				Matrix4f modelMatrix = item.getModelMatrix();
				
				shader.bind();
				
				shader.setUniform("model_matrix", modelMatrix);
				shader.setUniform("view_matrix", viewMatrix);
				shader.setUniform("projection_matrix", projectionMatrix);
				
				item.getMesh().render();
				
				shader.unbind();			
			}
		};
		
		ParameterFunction update = () -> {
				cam.update();
		};
		
		GLFWKeyCallbackI input = (inputWindow, key, scancode, action, mods) -> {
			
			if (key == GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE)
				GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
			
			cam.processKeyboardInput(inputWindow, key, scancode, action, mods);
			
		};
		
		window.loop(render, update, input);
	}
	
}
