package deviouscraker.roughriders.entity.spaniard;

import java.util.ArrayList;

/**
 * @author Zachery Jun 29, 2015
 */
public class SpaniardHandler {

	private ArrayList<SpaniardEntity> spaniards = new ArrayList<SpaniardEntity>();

	public SpaniardHandler() {}

	public void addSpaniard(SpaniardEntity e) {
		spaniards.add(e);
	}

	public SpaniardEntity getSpaniard(int i) {
		return spaniards.get(i);
	}

	public int getSize() {
		return spaniards.size();
	}

	public void removeSpaniard(SpaniardEntity se) {
		spaniards.remove(se);
	}

}
