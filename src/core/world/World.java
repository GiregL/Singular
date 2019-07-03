package core.world;

import java.util.ArrayList;
import java.util.List;

public class World {
	
	private List<GameItem> items;

	public World() {
		this.items = new ArrayList<GameItem>();
	}
	
	public void addItem(GameItem item) {
		this.items.add(item);
	}
	
	public boolean removeItem(GameItem item) {
		return this.items.remove(item);
	}
	
	
}
