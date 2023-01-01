package tom.gamedemo.cat;

import java.awt.Color;
import java.awt.Graphics;
import java.net.URL;

import tom.game.ImageLoader;
import tom.game.ImageSFXs;
import tom.game.Map;
import tom.game.RibbonBG;

public class RollingState extends CatState {

	private RibbonBG bg1;
	private RibbonBG bg2;
	private RibbonBG bg3;
	private Map map;

	public RollingState(int width, int height, String name) {
		super(width, height, name);
		this.setBackground(Color.BLACK);
		URL url = ClassLoader
				.getSystemResource("tom/gamedemo/cat/mountains.gif");
		bg1 = new RibbonBG(ImageLoader.loadImage(url), this.getWidth(), this
				.getHeight(), 1, 5, true);
		url = ClassLoader.getSystemResource("tom/gamedemo/cat/houses.gif");
		bg2 = new RibbonBG(ImageLoader.loadImage(url), this.getWidth(), this
				.getHeight(), 2, 5, true);
		url = ClassLoader.getSystemResource("tom/gamedemo/cat/trees.gif");
		bg3 = new RibbonBG(ImageLoader.loadImage(url), this.getWidth(), this
				.getHeight(), 4, 5, true);
		map = new Map(20, 100, 20, this);
		map.setIcon((byte)1, ImageSFXs.resizeImage(ImageLoader.loadImageRes("tom/gamedemo/cat/stone.jpg"), 20, 20));
		for (int i = 0; i < 29; i++)
			map.set(16, i, (byte) 1);
		for (int i = 30; i < 40; i++)
			map.set(12, i, (byte) 1);
		for (int i = 42; i < 50; i++)
			map.set(8, i, (byte) 1);
		for (int i = 52; i < 57; i++)
			map.set(15, i, (byte) 1);
		for (int i = 60; i < 70; i++)
			map.set(13, i, (byte) 1);
		for (int i = 72; i < 80; i++)
			map.set(10, i, (byte) 1);

	}

	@Override
	public void paintBackground(Graphics g) {
		bg1.paint(g);
		bg2.paint(g);
		bg3.paint(g);
		map.paint(g);
	}

	@Override
	public int getFloor() {
		return map.getFloor(cat);
	}

	@Override
	public void update() {
		super.update();
		if (cat.isFalling()
				|| cat.isJumping()
				|| ((cat.isKeyLeft() || cat.isKeyRight()) && cat.getBlock(map) == 0)) {
			if (cat.isKeyLeft() && map.slideX(-cat.getStep())) {
				bg1.rollRight();
				bg2.rollRight();
				bg3.rollRight();

			} else if (cat.isKeyRight() && map.slideX(cat.getStep())) {
				bg1.rollLeft();
				bg2.rollLeft();
				bg3.rollLeft();
			}
		}

	}
}
