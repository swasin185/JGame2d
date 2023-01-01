package tom.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class NumberSprite extends Sprite {
	protected BufferedImage[] images;

	protected int[] digits;

	protected int offset;

	protected int space;

	public NumberSprite(int x, int y, BufferedImage[] images, int length) {
		super(x, y, 0, null, null);
		this.images = images;
		digits = new int[length];
		for (int i = 0; i < digits.length; i++)
			digits[i] = -1;
		this.space = images[0].getWidth();
	}

	@Override
	public void paint(Graphics g) {
		int x = this.x;
		int y = this.y;
		offset = 0;
		for (int i = 0; i < digits.length; i++) {
			if (digits[i] >= 0)
				g.drawImage(images[digits[i]], x + offset, y, null);
			offset += space;
		}
	}

	public void setValue(int num) {
		if (num > 0) {
			int j;
			for (int i = digits.length - 1; i >= 0; i--) {
				j = num % 10;
				num /= 10;
				if (num == 0 && j == 0)
					digits[i] = -1;
				else
					digits[i] = j;
			}
		}
	}
}
