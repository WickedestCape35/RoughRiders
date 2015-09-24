package deviouscraker.roughriders;

import java.io.FileNotFoundException;

import deviouscraker.roughriders.entity.BulletHandler;
import deviouscraker.roughriders.entity.BulletHandler.BulletOriginException;
import deviouscraker.roughriders.entity.EntityHandler;
import deviouscraker.roughriders.entity.spaniard.SpaniardHandler;
import deviouscraker.roughriders.entity.spaniard.SpaniardRanks;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jun 24, 2015
 */
public class Main {

	public static Window w;
	public static Save save;

	public static EntityHandler eh = new EntityHandler();
	public static BulletHandler bh = new BulletHandler();
	public static SpaniardHandler sh = new SpaniardHandler();
	public static SpaniardRanks sr = new SpaniardRanks();

	public static boolean hasSave = false;

	public static void main(String[] args) throws FileNotFoundException {

		/*
		 * String s = "2.0";
		 * 
		 * Double d = Double.parseDouble(s);
		 * 
		 * System.out.println(d);
		 */

		w = new Window();
		save = new Save();

		if (hasSave) {
			save.load();
			w.player.load();
		}

		sr.getNewSpaniard(700, 100, Resource.Spaniard.PRIVATE);
		sr.getNewSpaniard(832, 212.5f, Resource.Spaniard.PRIVATE);
		sr.getNewSpaniard(900, 212.5f, Resource.Spaniard.PRIVATE);
		sr.getNewSpaniard(100, 100, Resource.Spaniard.PRIVATE);
		sr.getNewSpaniard(100, 200, Resource.Spaniard.CORPORAL);
		sr.getNewSpaniard(100, 300, Resource.Spaniard.SERGEANT);
		sr.getNewSpaniard(100, 400, Resource.Spaniard.LIEUTENANT);
		sr.getNewSpaniard(100, 500, Resource.Spaniard.CAPTAIN);
		sr.getNewSpaniard(100, 600, Resource.Spaniard.COMMANDER);

		while (true) {

			System.out.print("");

			while (!w.isPaused) {
				eh.run();
				w.run();
				try {
					bh.moveBullets();
				} catch (BulletOriginException e) {
					e.printStackTrace();
				}

				if (!w.isVisible()) {
					save.save();
					System.exit(0);
				}
			}

			if (!w.isVisible()) {
				System.exit(0);
			}
		}
	}
}
