package rendering;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh {
	
	private final int vaoId;
	private final int vboId;
	private final int vertexCount;
	
	public Mesh(float[] positions) {
		FloatBuffer buffer = null;
		try {
			buffer = MemoryUtil.memAllocFloat(positions.length);
			this.vertexCount = positions.length / 3;
			buffer.put(positions).flip();
			
			vaoId = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoId);
			
			vboId = GL20.glGenBuffers();
			GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vboId);
			GL20.glBufferData(GL20.GL_ARRAY_BUFFER, positions, GL20.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, 0);
			
			GL30.glBindVertexArray(0);
		} finally {
			if (buffer != null) {
				MemoryUtil.memFree(buffer);
			}
		}
	}
	
	public void render() {
		GL30.glBindVertexArray(vaoId);
		GL30.glEnableVertexAttribArray(0);
		GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, this.vertexCount);
		GL30.glDisableVertexAttribArray(0);
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

        // Delete the VAO
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(vaoId);
	}
	
}
