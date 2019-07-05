package core.world;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import core.model.CubeFactory;
import rendering.Mesh;

public class World {
	
	private List<GameItem> items;

	public World() {
		this.items = new ArrayList<GameItem>();
		
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
		
		for (float i = 0; i < 20; i++) {
			for (float j = 0; j < 20; j++) {
				for (float k = 0; k < 20; k++) {
					addItem(new GameItem(new Vector3f(i * 2, j * 2, k * 2), cubeMesh));
				}
			}
		}
	}
	
	public void addItem(GameItem item) {
		this.items.add(item);
	}
	
	public boolean removeItem(GameItem item) {
		return this.items.remove(item);
	}
	
	public List<GameItem> getItems() {
		return this.items;
	}
}
