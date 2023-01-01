package tom.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RibbonBG {

	protected BufferedImage background;

	protected int width;

	protected int height;

	protected int x = 0;

	protected int y = 0;

	protected int dx;

	protected int dy;

	boolean isRepeated;

	public RibbonBG(BufferedImage background, int width, int height,
			int dx, int dy, boolean isRepeated) {
		super();
		this.background = background;
		this.width = width;
		this.height = height;
		this.dx = dx;
		this.dy = dy;
		this.isRepeated = isRepeated;
	}

	public boolean isUpEnd() {
		return y + height >= background.getHeight() - dy;
	}

	public boolean isDownEnd() {
		return y < dy;
	}

	public boolean isLeftEnd() {
		return x + width >= background.getWidth() - dx;
	}

	public boolean isRightEnd() {
		return x < dx;
	}

	public void rollUp() {
		if (this.isRepeated || !this.isUpEnd())
			y = (y + dy) % background.getHeight();
	}

	public void rollDown() {
		if (this.isRepeated || !this.isDownEnd()) {
			y -= dy;
			if (y < 0)
				y = background.getHeight() + y;
		}
	}

	public void rollLeft() {
		if (this.isRepeated || !this.isLeftEnd())
			x = (x + dx) % background.getWidth();
	}

	public void rollRight() {
		if (this.isRepeated || !this.isRightEnd()) {
			x -= dx;
			if (x < 0)
				x = background.getWidth() + x;
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void paint(Graphics g) {
		int x1 = this.x;
		int y1 = this.y;
		int x2 = x1 + width - 1;
		int y2 = y1 + height - 1;
		if (x2 >= background.getWidth())
			x2 = background.getWidth() - 1;
		if (y2 >= background.getHeight())
			y2 = background.getHeight() - 1;
		int wx1 = 0;
		int wy1 = 0;
		int wx2 = x2 - x1 + 1;
		int wy2 = y2 - y1 + 1;
		g.drawImage(background, wx1, wy1, wx2, wy2, x1, y1, x2, y2, null);

		if (wx2 < width - 1) {
			x1 = (x2 + 1) % background.getWidth();
			x2 = x1 + width - wx2;
			wx1 = wx2;
			wx2 = wx1 + (x2 - x1);
			g.drawImage(background, wx1, wy1, wx2, wy2, x1, y1, x2, y2, null);
		}

		if (wy2 < height - 1) {
			y1 = (y2 + 1) % background.getHeight();
			y2 = y1 + height - wy2;
			wy1 = wy2;
			wy2 = wy1 + (y2 - y1);
			g.drawImage(background, wx1, wy1, wx2, wy2, x1, y1, x2, y2, null);
			if (wx1 > 0) {
				x1 = (x2 + 1) % background.getWidth();
				x2 = x1 + (width - x1);
				wx1 = 0;
				wx2 = x2 - x1 + 1;
				g.drawImage(background, wx1, wy1, wx2, wy2, x1, y1, x2, y2,
						null);
			}
		}

	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public void rollCenter() {
		y = (background.getHeight() - height) / 2;
		x = (background.getWidth() - width) / 2;
	}

	public void rollUpEnd() {
		y = background.getHeight() - height;
	}

	public void rollDownEnd() {
		y = 0;
	}

	public void rollLeftEnd() {
		x = background.getWidth() - width;
	}

	public void rollRightEnd() {
		x = 0;
	}

}
