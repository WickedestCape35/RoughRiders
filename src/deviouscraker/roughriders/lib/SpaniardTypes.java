package deviouscraker.roughriders.lib;

import java.util.HashMap;

import fourotherguys.lib.util.images.SpriteSheet;
import deviouscraker.roughriders.entity.spaniard.Spaniard;

/**
 * @author Zachery Jun 26, 2015
 */
public class SpaniardTypes {

	private HashMap<Integer, Spaniard> spaniard = new HashMap<Integer, Spaniard>();

	/**
	 * 
	 * @param ss
	 */
	public SpaniardTypes(SpriteSheet ss) {
		buildSpaniardHashMap(ss);
	}

	/**
	 * Builds the spaniard hashmap
	 */
	private void buildSpaniardHashMap(SpriteSheet ss) {
		for (int i = 0; i < 6; i++) {
			Spaniard s = new Spaniard(ss.getSpriteSheet().get("row0").get(i), ss.getSpriteSheet().get("row1").get(i));
			spaniard.put(i, s);
		}
	}

	/**
	 * Simply calls the get() method from the spaniard HashMap
	 * 
	 * @param spaniardID
	 *            : ID of the particular spaniard
	 * @return: a spaniard
	 */
	public Spaniard getSpaniard(int spaniardID) {
		return spaniard.get(spaniardID);
	}
}
