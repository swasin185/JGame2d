package tom.gamedemo.jvrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tom.game.ImageLoader;
import tom.game.ImageSFXs;
import tom.game.Map;
import tom.game.State;

public class FieldMap extends Map {

	private static final byte STONE = (byte) '#';
	private static final byte ENERGY = (byte) 'E';

	private static BufferedImage asteroidImg;
	private static BufferedImage energyImg;

	static {
		asteroidImg = ImageLoader
				.loadImageRes("tom/gamedemo/jvrobot/stone.jpg");
		energyImg = ImageLoader.loadImageRes("tom/gamedemo/jvrobot/energy.jpg");
	}

	public FieldMap(int row, int col, int scale, State state) {
		super(row, col, scale, state);
		asteroidImg = ImageSFXs.resizeImage(asteroidImg, scale, scale);
		energyImg = ImageSFXs.resizeImage(energyImg, scale, scale);
		super.setIcon(STONE, asteroidImg);
		super.setIcon(ENERGY, energyImg);
	}

	public void paint(Graphics g) {
		// g.setColor(Color.GREEN);
		// BufferedImage img = null;
		// for (int row = rowBegin; row < super.rowEnd; row++)
		// for (int col = colBegin; col < super.colEnd; col++) {
		// if (map[row][col] == STONE)
		// img = asteroidImg;
		// else if (map[row][col] == ENERGY)
		// img = energyImg;
		// else
		// img = null;
		// if (img != null)
		// g.drawImage(img, col * scale, row * scale, null);
		// }
		super.paint(g);
		g.setColor(Color.LIGHT_GRAY);
		for (int i = colBegin * scale; i < xInit + width; i += scale)
			g.drawLine(i - xInit, 0, i - xInit, height);
		for (int i = rowBegin * scale; i < yInit + height; i += scale)
			g.drawLine(0, i - yInit, width, i - yInit);
	}
}
