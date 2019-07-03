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
				4, 0, 1, 1, 5, 4,
				5, 1, 2, 2, 6, 5,
				6, 2, 3, 3, 7, 6,
				7, 3, 0, 0, 4, 7,
				7, 4, 5, 5, 6, 7,
				3, 2, 1, 1, 0, 3
		};		
	}
	
}
