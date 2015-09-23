package deviouscraker.roughriders;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import fourotherguys.lib.util.images.*;
import deviouscraker.roughriders.lib.Resource;

/**
 * @author Zachery Jun 26, 2015
 */
public class Background {
	private AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

	private BufferedImage background = GetImage.getBufferedImage("Background.png", this);

	public Background() {
		at.scale(Resource.Background.SCALE_X, Resource.Background.SCALE_Y);
	}

	public BufferedImage getBackground() {
		return background;
	}

	public AffineTransform getAffineTransform() {
		return at;
	}

}
