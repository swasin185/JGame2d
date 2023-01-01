package tom.game;

import java.awt.image.BufferedImage;

public class Animation implements Cloneable {

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Animation ani = new Animation(this.images, this.autoAnimated,
				this.autoRepeated);
		ani.frame = this.frame;
		ani.current = this.current;
		ani.seq = this.seq;
		return ani;
	}

	private BufferedImage[] images;

	private boolean autoAnimated;

	private boolean autoRepeated;

	private int autoResetFrame = -1;

	private int current;

	private int frame;

	private int seq;

	public Animation(BufferedImage[] images, boolean isAutoAnimated,
			boolean isAutoRepeated) {
		this(images, isAutoAnimated, isAutoRepeated, 1);
	}

	public Animation(BufferedImage[] images, boolean isAutoAnimated,
			boolean isAutoRepeated, int frame) {
		this.images = images;
		this.autoAnimated = isAutoAnimated;
		this.autoRepeated = isAutoRepeated;
		if (images != null) {
			current = 0;
		}
		this.seq = 0;
		this.frame = frame;
	}

	public boolean isFinished() {
		return !autoRepeated && current == -1;
	}

	public void animate() {
		if (!isFinished()) {
			this.seq++;
			this.current = this.seq / this.frame;
			if (this.current == images.length) {
				if (autoRepeated) {
					this.seq = 0;
					this.current = 0;
				} else {
					this.current = -1;
				}
			}

		}
	}

	public BufferedImage getCurrentImage() {
		BufferedImage img = null;
		if (!isFinished()) {
			img = images[current];
			if (this.isAutoAnimated())
				this.animate();
		}
		return img;
	}

	public void setCurrentImage(int index) {
		this.current = index;
	}

	public int getLength() {
		return images.length;
	}

	public void reset() {
		this.current = 0;
		this.seq = 0;
	}

	public boolean isAutoAnimated() {
		return autoAnimated
				|| (this.autoResetFrame > -1 && this.current != this.autoResetFrame);
	}

	public void setAutoAnimated(boolean autoAnimated) {
		this.autoAnimated = autoAnimated;
	}

	public boolean isAutoRepeated() {
		return autoRepeated;
	}

	public void setAutoRepeated(boolean autoRepeated) {
		this.autoRepeated = autoRepeated;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public void setAutoResetFrame(int autoResetFrame) {
		this.autoResetFrame = autoResetFrame;
	}

}
