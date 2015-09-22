package deviouscraker.roughriders.entity;

/**
 * @author Zachery Jun 26, 2015
 */
public interface Entity {

	public abstract void setX(float x);

	public abstract float getX();

	public abstract void setY(float y);

	public abstract float getY();

	public abstract void update();

	public abstract void onCreate(EntityHandler eh);

}
