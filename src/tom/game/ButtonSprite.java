package tom.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ButtonSprite extends Sprite {

	private BufferedImage offImg;
	private BufferedImage onImg;

	public ButtonSprite(int x, int y, BufferedImage offImg, BufferedImage onImg) {
		super(x, y);
		super.width = offImg.getWidth();
		super.height = offImg.getHeight();
		super.width_2 = super.width >> 1;
		super.height_2 = super.height >> 1;
		this.offImg = offImg;
		this.onImg = onImg;
	}

	@Override
	public boolean isCollided(int x, int y) {
		return (x >= (this.x - width_2) && x < (this.x + width_2))
				&& (y >= (this.y - height_2) && y < (this.y + height_2));
	}

	@Override
	public void paint(Graphics g) {
		if (onStatus)
			g.drawImage(onImg, x - super.width_2, y - super.height_2, null);
		else
			g.drawImage(offImg, x - super.width_2, y - super.height_2, null);
	}

	private boolean onStatus = false;

	public void setButtonStatus(boolean isOn) {
		this.onStatus = isOn;
	}

	public boolean getButtonStatus() {
		return this.onStatus;
	}

}
