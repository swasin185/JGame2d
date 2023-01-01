package tom.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Map {

	public static final int SPACE = 0;

	protected byte[][] map;

	protected short scale;

	protected int xInit;

	protected int yInit;

	protected int width;

	protected int height;

	protected int colBegin;

	protected int colEnd;

	protected int rowBegin;

	protected int rowEnd;

	protected BufferedImage[] icons;

	public Map(int row, int col, int scale, State state) {
		map = new byte[row][col];
		this.scale = (short) scale;
		this.width = state.getWidth();
		this.height = state.getHeight();
		colBegin = xInit / scale;
		colEnd = (xInit + width - 1) / scale;
		rowBegin = yInit / scale;
		rowEnd = (yInit + height - 1) / scale;
		icons = new BufferedImage[256];
	}

	public byte get(short row, short col) {
		return map[row][col];
	}

	public byte get(int x, int y) {
		short r = (short) (y / scale);
		short c = (short) (x / scale);
		if (r >= 0 && r < map.length && c >= 0 && c < map[0].length) {
			return map[r][c];
		} else
			return Map.SPACE;
	}

	public void set(int row, int col, byte value) {
		map[row][col] = value;
	}

	public void paint(Graphics g) {
		for (int r = rowBegin; r <= rowEnd; r++)
			for (int c = colBegin; c <= colEnd; c++) {
				if (icons[map[r][c]] != null)
					g.drawImage(icons[map[r][c]], c * scale - xInit, r * scale
							- yInit, null);
			}
		// for (int i = colBegin * scale; i < x + width; i += scale)
		// g.drawLine(i - x, 0, i - x, height);
		// for (int i = rowBegin * scale; i < y + height; i += scale)
		// g.drawLine(0, i - y, width, i - y);
	}

	@Deprecated
	public boolean isCollided(Sprite sprite) {
		boolean collided = false;
		// int r = (sprite.getY() + y ) / scale;
		// int c = (sprite.getX() + x) / scale;
		// if (map[r][c] != 0) {
		// collided = true;
		// }
		int h2 = sprite.getHeight() / 2;
		int w2 = (sprite.getWidth() / 2);
		int r1 = (sprite.getY() + yInit - h2) / scale;
		int r2 = (sprite.getY() + yInit + h2) / scale;
		int c1 = (sprite.getX() + xInit - w2) / scale;
		int c2 = (sprite.getX() + xInit + w2) / scale;
		for (int r = r1; r <= r2 && !collided; r++)
			for (int c = c1; c <= c2 && !collided; c++) {
				if (map[r][c] != 0) {
					// collided = sprite.isCollided(c * scale - x, r * scale -
					// y,
					// scale, scale);
				}
			}
		return collided;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean slideX(int dx) {
		int x = this.xInit + dx;
		boolean isSlided = (x >= 0)
				&& ((x + width - 1) / scale) <= map[0].length - 1;
		if (isSlided) {
			this.xInit = x;
			colBegin = x / scale;
			colEnd = (x + width - 1) / scale;
		}
		return isSlided;
	}

	public void slideY(int dy) {
		this.yInit += dy;
		rowBegin = yInit / scale;
		rowEnd = (yInit + height - 1) / scale;
	}

	public int getFloor(Sprite sprite) {
		int floor = this.getHeight();
		int c = (sprite.getX() + this.xInit) / scale;
		for (int r = (sprite.getY() + (sprite.getSize() / 2)) / scale; r <= rowEnd
				&& floor == this.getHeight(); r++) {
			if (map[r][c] != 0) {
				floor = (r * scale - this.yInit);
			}
		}
		return floor;
	}

	public short getScale() {
		return scale;
	}

	public boolean loadFromFile(URL url) {
		boolean successed = false;
		File file = null;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			int row = 0;
			int col = 0;
			while (line != null) {
				for (col = 0; col < line.length(); col++) {
					this.map[row][col] = (byte) line.charAt(col);
					if (this.map[row][col] == 32
							|| this.map[row][col] == (byte) '.')
						this.map[row][col] = Map.SPACE;
				}
				line = br.readLine();
				row++;
			}
			br.close();
			successed = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return successed;
	}

	public int getXInit() {
		return xInit;
	}

	public int getYInit() {
		return yInit;
	}

	public void setIcon(byte code, BufferedImage img) {
		icons[code] = img;
	}

}
