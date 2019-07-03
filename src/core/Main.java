package core;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import rendering.Camera;
import rendering.Mesh;
import rendering.Window;
import rendering.shaders.ShaderProgram;
import util.IFunctionalInput;
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
		
		float[] positions = new float[] {
				0.0f, 0.0f, 0.0f,	1.0f, 0.0f, 0.0f,	0.0f, 1.0f, 0.0f,
				1.0f, 0.0f, 0.0f,	1.0f, 1.0f, 0.0f,	0.0f, 1.0f, 0.0f,
				
				1.0f, 0.0f, 1.0f,	1.0f, 0.0f, 0.0f,	1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f, 	1.0f, 1.0f, 1.0f,	1.0f, 0.0f, 1.0f
		};
		
		GameItem cube = new GameItem(new Mesh(positions));
		Camera cam = new Camera();
		cam.setPosition(0.0f, 0.0f, 10.0f);
		
		Transformation transformation = new Transformation();
		
		ParameterFunction render = () -> {
			
			Matrix4f projectionMatrix = transformation.getProjectionMatrix(70.0f, 1080, 720, 0.1f, 1000.0f);
			Matrix4f viewMatrix = transformation.getViewMatrix(cam);
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(cube, viewMatrix);
			
			shader.bind();
			
			shader.setUniform("modelview_matrix", modelViewMatrix);
			shader.setUniform("projection_matrix", projectionMatrix);
			
			cube.getMesh().render();
			
			shader.unbind();
		};
		
		ParameterFunction update = () -> {
			cam.movePosition(0.01f, 0.0f, 0.0f);
		};
		
		GLFWKeyCallbackI input = (inputWindow, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
				GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
			}
		};
		
		window.loop(render, update, input);
	}
	
}
