package deviouscraker.roughriders.entity;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import deviouscraker.api.util.GetImage;
import deviouscraker.roughriders.Main;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jun 26, 2015
 */
public class BulletHandler {

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public BulletHandler() {

	}

	public void newBullet(Person p, int bulletDamage) {
		bullets.add(new Bullet(bulletDamage, p.getX(), p.getY(), p.getName()));
	}

	public void removeBullet(int b) {
		bullets.remove(b);
	}

	public Bullet getBullet(int i) {
		return this.bullets.get(i);
	}

	public ArrayList<Bullet> getBulletList() {
		return bullets;
	}

	/*
	 * @throws BulletOriginException
	 *             thrown if the origin is neither the player nor the computer.
	 * 
	 */
	public void moveBullets() throws BulletOriginException {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);

			if (b != null) {

				boolean origin;

				/*
				 * Moves the bullets to the right if shot from the player, or
				 * left if from the computer
				 */
				if (b.getPersonOrigin().equals(Resource.Origin.PLAYER)) {
					b.setX(b.getX() + b.getBulletSpeed());
					origin = true;
				} else if (b.getPersonOrigin().equals(Resource.Origin.COMPUTER)) {
					b.setX(b.getX() - b.getBulletSpeed());
					origin = false;
				} else {
					throw new BulletOriginException("Bullet origin is equal to " + b.getPersonOrigin());
				}

				/*
				 * Checks to see if the bullet is out of bounds, if so, deletes
				 * it
				 */
				if (b.getX() > Main.w.getSize().getWidth() || b.getX() < 0) {
					Main.eh.removeEntity(bullets.get(i));
					bullets.remove(i);
					break;
				}
				/*
				 * Checks to see if the bullet has made contact with either the
				 * player, or a Spaniard
				 */
				if (origin) {
					for (int j = 0; j < Main.sh.getSize(); j++) {
						if (!Main.sh.getSpaniard(j).isDucking()) {
							if ((Math.floor(b.getX()) - 2 <= Main.sh.getSpaniard(j).getX()) && (Math.floor(b.getX()) + 2 >= Main.sh.getSpaniard(j).getX()) && b.getY() >= Main.sh.getSpaniard(j).getY() + (4 * Resource.Person.SCALE_Y) && b.getY() <= Main.sh.getSpaniard(j).getY() + (20 * Resource.Person.SCALE_Y)) {
								Main.sh.getSpaniard(j).hit(b.getBulletDamage());
								Main.eh.removeEntity(bullets.get(i));
								bullets.remove(i);
								break;
							}
						}
					}
				} else {

				}

				b.setAffineTransform();
			}
		}
	}

	public class BulletOriginException extends Exception {
		private static final long serialVersionUID = 1L;

		/*
		 * Thrown by the BulletHandler class if, for some reason the
		 * personOrigin variable is not 0 or 1.
		 */
		public BulletOriginException(String e) {
			super(e);
		}

	}

	public class Bullet implements Entity {

		private float x;
		private float y;

		private int bulletDamage;
		private float bulletSpeed;

		private String personOrigin;

		private BufferedImage bulletImage = GetImage.getBufferedImage("Bullet.png", this);
		private AffineTransform at;

		public Bullet(int bulletDamage, float xOrigin, float yOrigin, String personOrigin) {
			if (personOrigin.equals(Resource.Origin.PLAYER)) {
				setX(xOrigin + (18 * Resource.Person.SCALE_X));
			} else {
				setX(xOrigin);
			}

			setBulletSpeed(Resource.BulletSpeed.PISTOL);
			setBulletDamage(bulletDamage);
			setY(yOrigin + (14 * Resource.Person.SCALE_Y));
			setPersonOrigin(personOrigin);

			onCreate(Main.eh);

			setAffineTransform();
		}

		public BufferedImage getImage() {
			return bulletImage;
		}

		public void setPersonOrigin(String origin) {
			this.personOrigin = origin;
		}

		public String getPersonOrigin() {
			return this.personOrigin;
		}

		@Override
		public void setX(float x) {
			this.x = x;
		}

		@Override
		public float getX() {
			return x;
		}

		@Override
		public void setY(float y) {
			this.y = y;
		}

		@Override
		public float getY() {
			return y;
		}

		@Override
		public void update() {}

		@Override
		public void onCreate(EntityHandler eh) {
			eh.add(this);

		}

		public void setAffineTransform() {
			at = AffineTransform.getTranslateInstance(getX(), getY());
			at.scale(Resource.Person.SCALE_X, Resource.Person.SCALE_Y);
		}

		public AffineTransform getAffineTransform() {
			return at;
		}

		public int getBulletDamage() {
			return bulletDamage;
		}

		public void setBulletDamage(int bulletDamage) {
			this.bulletDamage = bulletDamage;
		}

		public float getBulletSpeed() {
			return bulletSpeed;
		}

		public void setBulletSpeed(float bulletSpeed) {
			this.bulletSpeed = bulletSpeed;
		}

	}

}
