package rendering;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import util.LoggerFactory;
import util.ParameterFunction;

public class Window {
	
	private long window;
	
	public Window(String name, int width, int height) {
		LoggerFactory.createInfoLog("Creating Window " + name + " with width: " + width + ", height: " + height);
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit()) {
			LoggerFactory.createErrorLog("Failed to initialize GLFW");
			System.exit(1);
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		this.window = glfwCreateWindow(width, height, name, NULL, NULL);
		
		if (window == NULL) {
			LoggerFactory.createErrorLog("Failed to create window");
			System.exit(1);
		}
		
		try (MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(window, pWidth, pHeight);
			
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(
					window,
					(vidMode.width() - pWidth.get(0)),
					(vidMode.height() - pHeight.get(0)));
		}
		
		glfwMakeContextCurrent(this.window);
		
		GL.createCapabilities();
		
		glfwSwapInterval(1); // vsync
		
		glfwShowWindow(window);
		
		// Settings
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		LoggerFactory.createInfoLog("Successfully created window");
	}
	
	public long getWindow() {
		return this.window;
	}
	
	public boolean isCloseRequested() {
		return glfwWindowShouldClose(window);
	}
	
	public void loop(ParameterFunction render, ParameterFunction update, GLFWKeyCallbackI input) {
		glfwSetKeyCallback(window, input);
		
		glClearColor(0.2f, 0.5f, 1.0f, 1.0f);
		
		while (!isCloseRequested()) {
			update.function();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			render.function();
			glfwSwapBuffers(window);
			
			glfwPollEvents();
			
		}
	}
	
}
