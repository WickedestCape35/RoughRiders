package deviouscraker.roughriders.lib;

/*
 * This class holds several subclasses which then hold various constants for use throughout the program
 */
public class Resource {

	/*
	 * Constants related to the game
	 */
	public static final class Game {
		/*
		 * These are placed on the bar at the top of the window
		 */
		public static final String VERSION = "0.2.0";
		public static final String STATE = "ALPHA";
		public static final String NAME = "Rough Riders";
	}

	/*
	 * Constants related to the speed of the bullet
	 */
	public static final class BulletSpeed {
		public static final float PISTOL = 2f;
		@Deprecated
		public static final float RIFLE = PISTOL / 4f;
	}

	/*
	 * Constants related to the naming of the spaniards
	 */
	public static final class Spaniard {
		public static final String PRIVATE = "PRIVATE";
		public static final String CORPORAL = "CORPORAL";
		public static final String SERGEANT = "SERGEANT";
		public static final String LIEUTENANT = "LIEUTENANT";
		public static final String CAPTAIN = "CAPTAIN";
		public static final String COMMANDER = "COMMANDER";
	}

	/*
	 * These constants are the scales of the person(s), including both spaniards
	 * and the player
	 */
	public static final class Person {
		public static final float SCALE_X = 2;
		public static final float SCALE_Y = 2;
	}

	/*
	 * These constants are for the scale of the background
	 */
	public static final class Background {
		public static final float SCALE_X = 1.6875f;
		public static final float SCALE_Y = 1.5f;
	}

	/*
	 * These constants are for the name of player and computer objects, used to
	 * determine bullet direction.
	 */
	public static final class Origin {
		public static final String PLAYER = "PLAYER";
		public static final String COMPUTER = "COMPUTER";
	}
}
