package deviouscraker.roughriders.entity.spaniard;

import deviouscraker.roughriders.Main;
import deviouscraker.roughriders.entity.Person;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jun 26, 2015
 */
public class SpaniardEntity extends Person {

	private String trueName;

	private float x;
	private float y;

	private int attackPoints;

	private Spaniard spaniard;

	private int value;

	public SpaniardEntity(int attackPoints, int healthPoints, float x, float y, Spaniard spaniard, String trueName) {
		super(healthPoints, spaniard.getStanding(), spaniard.getDucking());
		setX(x);
		setY(y);
		setAttackPoints(attackPoints);
		setSpaniard(spaniard);
		setTrueName(trueName);
		Main.sh.addSpaniard(this);
	}

	@Override
	public void triggerDuck() {}

	@Override
	public void onDeath() {
		Main.sh.removeSpaniard(this);
		Main.eh.removeEntity(this);
		dispose();
		Main.w.player.addPoints(this.value);
	}

	@Override
	public void triggerFire() {}

	public int getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public SpaniardEntity setValue(int value) {
		this.value = value;

		return this;
	}

	@Override
	public String getName() {
		return Resource.Origin.COMPUTER;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getY() {
		return this.y;
	}

	public void setSpaniard(Spaniard spaniard) {
		this.spaniard = spaniard;
	}

	public Spaniard getSpaniard() {
		return this.spaniard;
	}

}
