package deviouscraker.roughriders.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import deviouscraker.roughriders.Main;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jun 25, 2015
 */
public abstract class Person implements Entity {

	protected int healthPoints;
	protected int maxHealth;

	protected BufferedImage standing;
	protected BufferedImage ducking;
	protected BufferedImage currentStance;

	private boolean isDucking = false;

	protected AffineTransform at;

	public Person(int healthPoints, BufferedImage standing, BufferedImage ducking) {

		setMaxHealthPoints(healthPoints);
		setStanding(standing);
		setDucking(ducking);
		setCurrentStance(this.standing);
		this.healthPoints = maxHealth;
		onCreate(Main.eh);
	}

	public abstract void triggerDuck();

	public abstract void triggerFire();

	public abstract void onDeath();

	public abstract String getName();

	@Override
	public void onCreate(EntityHandler eh) {
		eh.add(this);
	}

	@Override
	public void update() {
		setAffineTransform(this);
		checkForDeath();
	}

	public void hit(int damage) {
		healthPoints -= damage;
		checkForDeath();
	}

	public void checkForDeath() {
		if (healthPoints <= 0) {
			onDeath();
		}
	}

	public void duck() {
		if (isDucking()) {
			currentStance = standing;
			setDucking(false);
		} else {
			currentStance = ducking;
			setDucking(true);
		}
	}

	public void dispose() {
		standing = null;
		ducking = null;
		currentStance = null;
		at = null;
	}

	public boolean isDucking() {
		return isDucking;
	}

	public void setDucking(boolean isDucking) {
		this.isDucking = isDucking;
	}

	protected void setAffineTransform(Entity e) {
		at = AffineTransform.getTranslateInstance(e.getX(), e.getY());

		setPersonScale();
	}

	public void setPersonScale() {
		at.scale(Resource.Person.SCALE_X, Resource.Person.SCALE_Y);
	}

	public AffineTransform getAffineTransform() {
		return at;
	}

	public void setStanding(BufferedImage standing) {
		this.standing = standing;
	}

	public void setDucking(BufferedImage ducking) {
		this.ducking = ducking;
	}

	public BufferedImage getCurrentStance() {
		return currentStance;
	}

	public void setCurrentStance(BufferedImage currentStance) {
		this.currentStance = currentStance;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setMaxHealthPoints(int healthPoints) {
		this.maxHealth = healthPoints;
	}

}
