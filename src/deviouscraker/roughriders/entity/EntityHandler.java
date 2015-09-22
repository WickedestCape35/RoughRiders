package deviouscraker.roughriders.entity;

import java.util.ArrayList;

/**
 * @author Zachery Jun 26, 2015
 */
public class EntityHandler {

	private ArrayList<Entity> entityContainer = new ArrayList<Entity>();

	public EntityHandler() {}

	public void add(Entity e) {
		entityContainer.add(e);
	}

	public void removeEntity(Entity e) {
		entityContainer.remove(e);
	}

	public void run() {
		for (int i = 0; i < entityContainer.size(); i++) {
			entityContainer.get(i).update();
		}
	}

}
