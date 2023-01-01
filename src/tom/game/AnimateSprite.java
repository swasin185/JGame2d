package tom.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AnimateSprite extends Sprite {

	protected Animation mainAni;

	public AnimateSprite(int x, int y, BufferedImage[] images,
			boolean isAutoAnimated, boolean isAutoRepeated, State state) {
		this(x, y, images, isAutoAnimated, isAutoRepeated, 1, state);
	}

	public AnimateSprite(int x, int y, BufferedImage[] images,
			boolean isAutoAnimated, boolean isAutoRepeated, int frame,
			State state) {
		super();
		super.state = state;
		this.mainAni = new Animation(images, isAutoAnimated, isAutoRepeated,
				frame);
		this.x = x;
		this.y = y;
		this.size = images[0].getHeight();
		this.width = images[0].getWidth();
		this.width_2 = this.width >> 1;
		this.height = images[0].getHeight();
		this.height_2 = this.height >> 1;
	}

	public AnimateSprite(int x, int y, BufferedImage image, State state) {
		this(x, y, new BufferedImage[] { image }, false, false, 1, state);
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage img = mainAni.getCurrentImage();
		g.drawImage(img, x - width_2, y - height_2, null);
	}

	public void action() {
		this.mainAni.reset();
		this.mainAni.setAutoAnimated(true);
	}

	public void action(int x, int y) {
		this.setX(x);
		this.setY(y);
		this.action();
	}

	@Override
	public void setAction(boolean actioned) {
		this.mainAni.setAutoAnimated(actioned);
	}

	@Override
	public boolean isActioned() {
		return !this.mainAni.isFinished() && this.mainAni.isAutoAnimated();
	}
}
