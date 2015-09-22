package deviouscraker.roughriders.entity.spaniard;

import deviouscraker.roughriders.Main;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jul 2, 2015
 */
public class SpaniardRanks {

	public void getNewSpaniard(float x, float y, String id) {

		if (id == Resource.Spaniard.PRIVATE) {
			new SpaniardEntity(2, 10, x, y, Main.w.st.getSpaniard(0), Resource.Spaniard.PRIVATE).setValue(1);
		} else if (id == Resource.Spaniard.CORPORAL) {
			new SpaniardEntity(5, 20, x, y, Main.w.st.getSpaniard(1), Resource.Spaniard.CORPORAL).setValue(3);
		} else if (id == Resource.Spaniard.SERGEANT) {
			new SpaniardEntity(8, 30, x, y, Main.w.st.getSpaniard(2), Resource.Spaniard.SERGEANT).setValue(5);
		} else if (id == Resource.Spaniard.LIEUTENANT) {
			new SpaniardEntity(11, 40, x, y, Main.w.st.getSpaniard(3), Resource.Spaniard.LIEUTENANT).setValue(10);
		} else if (id == Resource.Spaniard.CAPTAIN) {
			new SpaniardEntity(14, 50, x, y, Main.w.st.getSpaniard(4), Resource.Spaniard.CAPTAIN).setValue(15);
		} else if (id == Resource.Spaniard.COMMANDER) {
			new SpaniardEntity(20, 100, x, y, Main.w.st.getSpaniard(5), Resource.Spaniard.COMMANDER).setValue(25);
		}
	}

}
