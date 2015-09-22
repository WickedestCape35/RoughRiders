package deviouscraker.roughriders.entity;

import java.awt.image.BufferedImage;

import deviouscraker.roughriders.Main;
import deviouscraker.roughriders.Save;
import deviouscraker.roughriders.Window;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jun 25, 2015
 */
public class Player extends Person {

	/*
	 * X and Y coordinates
	 */
	private float x;
	private float y;

	/*
	 * The different available stances
	 */
	private BufferedImage standingPistol;
	private BufferedImage standingRifle;
	private BufferedImage duckingPistol;
	private BufferedImage duckingRifle;

	/*
	 * Needed to know when the user is using the rifle
	 */
	private boolean hasRifle;

	/*
	 * Used to know when the player is reloading, generally in order to limit
	 * certain functions, (crouch/shooting) or as in the Window.class, when to
	 * show the reloadingPercent field
	 */
	private boolean reloading = false;

	/*
	 * Used mainly by the Window.class to be drawn on the screen to notify the
	 * user how far the reloading is
	 */
	private int reloadingPercent = 0;

	/*
	 * The max amount of ammo for each weapon, can be increased through the
	 * purchase of upgrades.
	 */
	private int maxRifleAmmo = 5;
	private int maxPistolAmmo = maxRifleAmmo * 2;

	/*
	 * Used for resets
	 */
	private int defaultMaxRifleAmmo = maxRifleAmmo;

	/*
	 * The amount of ammo currently in the weapon
	 */
	private int currentRifleAmmo = maxRifleAmmo;
	private int currentPistolAmmo = maxPistolAmmo;

	/*
	 * The starting damage of the weapons
	 */
	private int pistolDamage = 5;
	private int rifleDamage = pistolDamage * 2;

	/*
	 * Used for resets
	 */
	private int defaultPistolDamage = pistolDamage;

	/*
	 * The speed that the player moves across the screen.
	 */
	private int increment;

	/*
	 * Points are a currency, used in the shop.
	 */
	private int points = 0;

	/*
	 * Used by the shop for the ability to purchase. It scales the price of
	 * items as well as the effects on the player.
	 */
	private int ammoUpgradesPurchased = 0;
	private int powerUpgradesPurchased = 0;
	private int reloadUpgradesPurchased = 0;
	private int healthUpgradesPurchased = 0;

	/*
	 * In the future will be used to denote what fort the user is on
	 */
	private int currentFort = 0;

	/*
	 * How much health is given per upgrade.
	 */
	private int healthUpgradeAmount = 5;

	/*
	 * How much damage is increased by per upgrade
	 */
	private int damageUpgradeAmount = 1;

	/*
	 * How much ammo the capacity is increased by per upgrade.
	 */
	private int ammoUpgradeAmount = 1;

	private boolean hasFired = false;

	public Player(int x, int y, int healthPoints, BufferedImage standingPistol, BufferedImage duckingPistol, BufferedImage standingRifle, BufferedImage duckingRifle) {

		/*
		 * passes in the health points like normal, however also passes in null
		 * for the stances as that requires specially handling
		 */
		super(healthPoints, null, null);

		setX(x);
		setY(y);

		setStandingPistol(standingPistol);
		setStandingRifle(standingRifle);
		setDuckingPistol(duckingPistol);
		setDuckingRifle(duckingRifle);

		setIncrement(5);

		setHasRifle(false);
		checkRifleStatus();
	}

	public void load() {
		currentFort = Integer.parseInt(Main.save.values.get(Save.SAVE_LEVEL));
		ammoUpgradesPurchased = Integer.parseInt(Main.save.values.get(Save.SAVE_AMMO));
		healthUpgradesPurchased = Integer.parseInt(Main.save.values.get(Save.SAVE_HEALTH));
		powerUpgradesPurchased = Integer.parseInt(Main.save.values.get(Save.SAVE_POWER));
		reloadUpgradesPurchased = Integer.parseInt(Main.save.values.get(Save.SAVE_RELOAD));
		points = Integer.parseInt(Main.save.values.get(Save.SAVE_POINTS));

		checkUpgrades();
		currentPistolAmmo = maxPistolAmmo;
	}

	public void checkUpgrades() {
		maxRifleAmmo = defaultMaxRifleAmmo + ammoUpgradesPurchased * ammoUpgradeAmount;
		maxPistolAmmo = maxRifleAmmo * 2;

		pistolDamage = defaultPistolDamage + powerUpgradesPurchased * damageUpgradeAmount;
		rifleDamage = pistolDamage * 2;

		setMaxHealthPoints(this.maxHealth + healthUpgradesPurchased * healthUpgradeAmount);

		refreshHealth();
	}

	public void refreshHealth() {
		this.healthPoints = this.maxHealth;
	}

	public void checkRifleStatus() {
		if (isHasRifle()) {
			if (isDucking()) {
				setCurrentStance(this.getDuckingRifle());
			} else {
				setCurrentStance(this.getStandingRifle());
			}
		} else {
			if (isDucking()) {
				setCurrentStance(this.getDuckingPistol());
			} else {
				setCurrentStance(this.getStandingPistol());
			}
		}
	}

	public void bulletWait() {
		Thread thread = null;

		if (isHasRifle()) {
			thread = new Thread(new Runnable() {

				@Override
				public void run() {
					hasFired = true;
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					hasFired = false;
				}

			});
		} else if (!isHasRifle()) {
			thread = new Thread(new Runnable() {

				@Override
				public void run() {

					hasFired = true;
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					hasFired = false;
				}

			});
		}

		thread.start();
	}

	@Override
	public void onDeath() {
		// TODO
	}

	@Override
	public void triggerDuck() {
		if (!isReloading()) {
			duck();
		}
	}

	@Override
	public void duck() {
		if (isDucking()) {
			setDucking(false);
		} else {
			setDucking(true);
		}
		checkRifleStatus();
	}

	/*
	 * Invoked by the KeyListener in the Window.class, corresponds with two
	 * keys, if they are pressed it moves the player in the proper direction. As
	 * long as the player is in bounds.
	 */
	public void moveLeft() {
		if (this.getX() > 0 && !isDucking()) {
			this.x -= getIncrement();
		}
	}

	/*
	 * Invoked by the KeyListener in the Window.class, corresponds with two
	 * keys, if they are pressed it moves the player in the proper direction. As
	 * long as the player is in bounds.
	 */
	public void moveRight() {
		// TODO, UNSURE OF WHERE THE FAR RIGHT SIDE BOUNDARY WILL BE
		if (this.getX() + this.currentStance.getWidth() * Resource.Person.SCALE_X < 400 && !isDucking()) {
			this.x += getIncrement();
		}
	}

	/*
	 * Invoked by the KeyListener in the Window.class, corresponds with two
	 * keys, if they are pressed it moves the player in the proper direction. As
	 * long as the player is in bounds.
	 */
	public void moveUp() {
		if (this.getY() > 0 && !isDucking()) {
			this.y -= getIncrement();
		}
	}

	/*
	 * Invoked by the KeyListener in the Window.class, corresponds with two
	 * keys, if they are pressed it moves the player in the proper direction. As
	 * long as the player is in bounds.
	 */
	public void moveDown() {
		if (this.getY() + this.currentStance.getHeight() * Resource.Person.SCALE_Y < Window.gameDimension.getHeight() - getIncrement() && !isDucking()) {
			this.y += getIncrement();
		}
	}

	/*
	 * Invoked by the KeyListener in the Window.class. First checks that the
	 * player is neither ducking nor reloading, and has ammo left in the weapon,
	 * if it doesn't, calls the reload() method itself, then fire a bullet of
	 * the appropriate speed and power based on the weapon that is being used.
	 */
	@Override
	public void triggerFire() {
		if (!isDucking() && !isReloading() && !hasFired()) {
			if (isHasRifle()) {
				if (currentRifleAmmo > 0) {
					Main.bh.newBullet(this, rifleDamage);
					currentRifleAmmo--;
					bulletWait();
				} else {
					reload();
				}
			} else {
				if (currentPistolAmmo > 0) {
					Main.bh.newBullet(this, pistolDamage);
					currentPistolAmmo--;
					bulletWait();
				} else {
					reload();
				}
			}
		}
	}

	/*
	 * Creates a thread to run the reload() method after a certain amount of
	 * time has passed, a new thread is required to ensure that the base game is
	 * continuing to run
	 */
	public void reload() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				reloadingPercent = 0;
				try {
					if (isHasRifle()) {
						reloading = true;
						while (reloadingPercent <= 100) {
							Thread.sleep((5000 - getReloadUpgradesPurchased() * 500) / 10);
							reloadingPercent += 10;
						}
						currentRifleAmmo = maxRifleAmmo;
						reloading = false;
					} else {
						reloading = true;
						while (reloadingPercent <= 100) {
							Thread.sleep((2500 - getReloadUpgradesPurchased() * 250) / 10);
							reloadingPercent += 10;
						}
						currentPistolAmmo = maxPistolAmmo;
						reloading = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		if (!isReloading()) {
			if (isHasRifle()) {
				if (currentRifleAmmo != maxRifleAmmo) {
					thread.start();
				}
			} else {
				if (currentPistolAmmo != maxPistolAmmo) {
					thread.start();
				}
			}
		}
	}

	/*
	 * 
	 * ALL GETTERS AND SETTERS LIE BELOW THIS COMMENT
	 * 
	 */

	/*
	 * Gets the amount of points the user has
	 */
	public int getPoints() {
		return points;
	}

	/*
	 * Adds points to the user from kills.
	 */
	public void addPoints(int points) {
		this.points += points;
	}

	/*
	 * Removes points from the user after purchases.
	 */
	public void removePoints(int points) {
		this.points -= points;
	}

	public int getAmmoUpgradesPurchased() {
		return ammoUpgradesPurchased;
	}

	public void addAmmoUpgradesPurchased() {
		this.ammoUpgradesPurchased++;

	}

	public int getPowerUpgradesPurchased() {
		return powerUpgradesPurchased;
	}

	public void addPowerUpgradesPurchased() {
		this.powerUpgradesPurchased++;
	}

	public int getReloadUpgradesPurchased() {
		return reloadUpgradesPurchased;
	}

	public void addReloadUpgradesPurchased() {
		if (getReloadUpgradesPurchased() < 10) {
			this.reloadUpgradesPurchased++;
		}
	}

	public int getHealthUpgradesPurchased() {
		return healthUpgradesPurchased;
	}

	public void addHealthUpgradesPurchased() {
		this.healthUpgradesPurchased++;
	}

	/*
	 * Returns the x coordinate
	 */
	@Override
	public float getX() {
		return x;
	}

	/*
	 * Sets the x coordinate
	 */
	@Override
	public void setX(float x) {
		this.x = x;
	}

	/*
	 * Returns the y coordinate
	 */
	@Override
	public float getY() {
		return y;
	}

	/*
	 * Sets the y coordinate
	 */
	@Override
	public void setY(float y) {
		this.y = y;
	}

	public BufferedImage getStandingPistol() {
		return standingPistol;
	}

	public void setStandingPistol(BufferedImage standingPistol) {
		this.standingPistol = standingPistol;
	}

	public BufferedImage getStandingRifle() {
		return standingRifle;
	}

	public void setStandingRifle(BufferedImage standingRifle) {
		this.standingRifle = standingRifle;
	}

	public BufferedImage getDuckingPistol() {
		return duckingPistol;
	}

	public void setDuckingPistol(BufferedImage duckingPistol) {
		this.duckingPistol = duckingPistol;
	}

	public BufferedImage getDuckingRifle() {
		return duckingRifle;
	}

	public void setDuckingRifle(BufferedImage duckingRifle) {
		this.duckingRifle = duckingRifle;
	}

	public boolean isHasRifle() {
		return hasRifle;
	}

	public void setHasRifle(boolean ifHasRifle) {
		if (!isReloading()) {
			this.hasRifle = ifHasRifle;
		}
	}

	public int getReloadingPercent() {
		return reloadingPercent;
	}

	public boolean isReloading() {
		return reloading;
	}

	public void setIncrement(int increment) {
		this.increment = increment;
	}

	public int getIncrement() {
		return increment;
	}

	@Override
	public String getName() {
		return Resource.Origin.PLAYER;
	}

	public String getCurrentAmmo() {
		if (isHasRifle()) {
			return Integer.toString(currentRifleAmmo);
		} else {
			return Integer.toString(currentPistolAmmo);
		}
	}

	public String getMaxAmmo() {
		if (isHasRifle()) {
			return Integer.toString(maxRifleAmmo);
		} else {
			return Integer.toString(maxPistolAmmo);
		}
	}

	public int getCurrentFort() {
		return currentFort;
	}

	public boolean hasFired() {
		return this.hasFired;
	}

	public void triggerReload() {
		if (currentPistolAmmo != maxPistolAmmo || currentRifleAmmo != maxRifleAmmo) {
			reload();
		}
	}

}
