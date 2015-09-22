package deviouscraker.roughriders.entity.spaniard;

import java.awt.image.BufferedImage;

/**
 * @author Zachery Jun 25, 2015
 */
public class Spaniard {

	private BufferedImage standing;
	private BufferedImage ducking;

	public Spaniard(BufferedImage standing, BufferedImage ducking) {
		setStanding(standing);
		setDucking(ducking);
	}

	public BufferedImage getStanding() {
		return standing;
	}

	public void setStanding(BufferedImage standing) {
		this.standing = standing;
	}

	public BufferedImage getDucking() {
		return ducking;
	}

	public void setDucking(BufferedImage ducking) {
		this.ducking = ducking;
	}

}
