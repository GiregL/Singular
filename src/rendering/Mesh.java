package rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh {
	
	private final int vaoId;
	private final int vboId;
	private final int colorVboId;
	private final int veoId;
	private final int vertexCount;
	
	public Mesh(float[] positions, float[] colors, int[] indices) {
		FloatBuffer buffer = null;
		IntBuffer intBuffer = null;
		try {
			buffer = MemoryUtil.memAllocFloat(positions.length);
			intBuffer = MemoryUtil.memAllocInt(indices.length);
			this.vertexCount = indices.length;
			buffer.put(positions).flip();
			intBuffer.put(indices).flip();
			
			vaoId = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoId);
			
			vboId = GL20.glGenBuffers();
			GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboId);
			GL20.glBufferData(GL20.GL_ARRAY_BUFFER, buffer, GL20.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
			
			buffer.clear();
			buffer.put(colors).flip();
			
			colorVboId = GL30.glGenVertexArrays();
			GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, colorVboId);
			GL20.glBufferData(GL20.GL_ARRAY_BUFFER, buffer, GL20.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
			
			veoId = GL20.glGenBuffers();
			GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, veoId);
			GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, intBuffer, GL20.GL_STATIC_DRAW);
			
			GL30.glBindVertexArray(0);
		} finally {
			if (buffer != null) {
				MemoryUtil.memFree(buffer);
			}
			if (intBuffer != null) {
				MemoryUtil.memFree(intBuffer);
			}
		}
	}
	
	public void render() {
		GL30.glBindVertexArray(vaoId);
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glDrawElements(GL11.GL_TRIANGLES, this.vertexCount, GL11.GL_UNSIGNED_INT, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public int getVaoId() {
		return vaoId;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public void cleanUp() {
		GL30.glDisableVertexAttribArray(0);

        GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
        GL20.glDeleteBuffers(vboId);
        GL20.glDeleteBuffers(veoId);

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
	}
	
}
