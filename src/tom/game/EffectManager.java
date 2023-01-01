package tom.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

public class EffectManager extends SoundManager {
	private AnimateSprite[] sprites;

	public EffectManager(BufferedImage[] images, URL urlSound, int count) {
		this(images, urlSound, count, 1);
	}
	
	public EffectManager(BufferedImage[] images, URL urlSound, int count, int frame) {
		super(urlSound, count);
		sprites = new AnimateSprite[count];
		for (int i = 0; i < sprites.length; i++)
			sprites[i] = new AnimateSprite(0, 0, images, false, false, frame, null);
	}

	public void play(int x, int y) {
		super.play();
		sprites[super.current].action(x, y);
	}

	public void play(int x, int y, Sprite sprite) {
		if (ignoreSprites.contains(sprite)) {
			ignoreSprites.remove(sprite);
		} else {
			this.play(x, y);
		}
	}

	public void paint(Graphics g) {
		for (AnimateSprite sp : sprites)
			if (sp.isActioned())
				sp.paint(g);
	}
}
