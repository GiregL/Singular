package rendering.shaders;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import util.LoggerFactory;

public class ShaderProgram {
	
	private final int programId;
	
	private int vertexShaderId;
	private int fragmentShaderId;
	
	private final Map<String, Integer> uniforms;
	
	public ShaderProgram() {
		this.uniforms = new HashMap<String, Integer>();
		this.programId = GL20.glCreateProgram();
		if (this.programId == 0) {
			LoggerFactory.createErrorLog("Failed to create Shader Program");
			System.exit(1);
		}
	}
	
	public void createVertexShader(String code) {
		this.vertexShaderId = createShader(code, GL20.GL_VERTEX_SHADER);
	}
	
	public void createFragmentShader(String code) {
		this.fragmentShaderId = createShader(code, GL20.GL_FRAGMENT_SHADER);
	}
	
	public int createShader(String code, int type) {
		int shaderId = GL20.glCreateShader(type);
		if (shaderId == 0) {
			LoggerFactory.createErrorLog("Failed to create Shader");
			System.exit(1);
		}
		
		GL20.glShaderSource(shaderId, code);
		GL20.glCompileShader(shaderId);
		
		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
			LoggerFactory.createErrorLog("Error compiling shader code : " + GL20.glGetShaderInfoLog(shaderId, 1024));
			System.exit(1);
		}
		
		GL20.glAttachShader(programId, shaderId);
		return shaderId;
	}
	
	public void link() {
		GL20.glLinkProgram(programId);
		if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
			LoggerFactory.createErrorLog("Error while linking shader : " + GL20.glGetProgramInfoLog(programId, 1024));
			System.exit(1);
		}
		
		if (vertexShaderId != 0) {
            GL20.glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
        	GL20.glDetachShader(programId, fragmentShaderId);
        }

        GL20.glValidateProgram(programId);
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }
	}
	
	public void bind() {
		GL20.glUseProgram(programId);
	}
	
	public void unbind() {
		GL20.glUseProgram(0);
	}
	
	public void cleanup() {
		unbind();
		if (programId != 0) {
			GL20.glDeleteProgram(programId);
		}
	}
	
	public void createUniform(String uniformName) {
		int location = GL20.glGetUniformLocation(programId, uniformName);
		if (location < 0) {
			LoggerFactory.createErrorLog("Could not find uniform : " + uniformName);
			System.exit(1);
		}
		uniforms.put(uniformName, location);
	}
	
	public void setUniform(String name, Matrix4f value) {
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(16);
			value.get(buffer);
			GL20.glUniformMatrix4fv(uniforms.get(name), false, buffer);
		}
	}
}
