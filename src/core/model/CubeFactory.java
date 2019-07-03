package core.model;

public class CubeFactory {
	
	public static float[] getVertices() {
		return new float[] {
				0.0f, 0.0f, 0.0f,			// 0
				1.0f, 0.0f, 0.0f,			// 1
				1.0f, 0.0f, 1.0f,			// 2
				0.0f, 0.0f, 1.0f,			// 3
				
				0.0f, 1.0f, 0.0f,			// 4
				1.0f, 1.0f, 0.0f,			// 5
				1.0f, 1.0f, 1.0f,			// 6
				0.0f, 1.0f, 1.0f,			// 7
		};
		
	}
	
	public static int[] getIndices() {
		return new int[] {
				1, 0, 4, 4, 5, 1,
				2, 1, 5, 5, 6, 2,
				3, 2, 6, 6, 7, 3,
				0, 3, 7, 7, 4, 0,
				5, 4, 7, 7, 6, 5,
				1, 2, 3, 3, 0, 1
		};		
	}
	
}
