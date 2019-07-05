package core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import core.world.GameItem;
import core.world.World;
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
		
		World world = new World();
		
		Camera cam = new Camera();
		cam.mouseInput(window.getWindow());
		
		Transformation transformation = new Transformation();
		
		/*
		 * RENDERING
		 */
		
		ParameterFunction render = () -> {
			Matrix4f projectionMatrix = transformation.getProjectionMatrix(70.0f, 1080, 720, 0.1f, 1000.0f);
			Matrix4f viewMatrix = transformation.getViewMatrix(cam);
			
			
			for (GameItem item : world.getItems()) {
				
				Matrix4f modelMatrix = item.getModelMatrix();
				
				shader.bind();
				
				shader.setUniform("model_matrix", modelMatrix);
				shader.setUniform("view_matrix", viewMatrix);
				shader.setUniform("projection_matrix", projectionMatrix);
				
				item.getMesh().render();
				
				shader.unbind();			
			}
		};
		
		/*
		 * UPDATES
		 */
		
		ParameterFunction update = () -> {
			if (window.isFocused())
				cam.update();
		};
		
		/*
		 * INPUT
		 */
		
		GLFWKeyCallbackI input = (inputWindow, key, scancode, action, mods) -> {
			
			if (window.isFocused()) {
				if (key == GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
					window.setFocused(false);
					GLFW.glfwSetInputMode(window.getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
					//GLFW.glfwSetWindowShouldClose(window.getWindow(), true);					
				}
				cam.processKeyboardInput(inputWindow, key, scancode, action, mods);
			}
			
		};
		
		window.loop(render, update, input);
	}
	
}
