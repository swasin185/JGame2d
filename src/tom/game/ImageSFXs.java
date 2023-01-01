package tom.game;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

public class ImageSFXs {

	private static ConvolveOp blurOp = null;
	static {
		float ninth = 1.0f / 9.0f;
		float[] blurKernel = { ninth, ninth, ninth, ninth, ninth, ninth, ninth,
				ninth, ninth };
		ImageSFXs.blurOp = new ConvolveOp(new Kernel(3, 3, blurKernel),
				ConvolveOp.EDGE_NO_OP, null);

	}

	private static RescaleOp negOp = new RescaleOp(-1.0f, 255f, null);

	public static BufferedImage blurImage(BufferedImage img) {
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		blurOp.filter(img, effect);
		return effect;
	}

	public static BufferedImage copyImage(BufferedImage img) {
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = effect.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return effect;
	}

	public static BufferedImage fadeImage(BufferedImage img, float alpha) {
		if (alpha < 0 || alpha > 1)
			throw new RuntimeException("Alpha should be 0.0-1.0");
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = effect.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return effect;
	}

	public static BufferedImage flipImage(BufferedImage img, boolean isHorizon,
			boolean isVertical) {
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = effect.createGraphics();
		int x1 = 0;
		int y1 = 0;
		int x2 = img.getWidth();
		int y2 = img.getHeight();
		if (isHorizon) {
			y1 = img.getHeight();
			y2 = 0;
		}
		if (isVertical) {
			x1 = img.getWidth();
			x2 = 0;
		}
		g.drawImage(img, x1, y1, x2, y2, 0, 0, img.getWidth(), img.getHeight(),
				null);
		g.dispose();
		return effect;
	}

	public static BufferedImage negImage(BufferedImage img) {
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_RGB);
		ImageSFXs.negOp.filter(img, effect);
		return effect;
	}

	public static BufferedImage resizeImage(BufferedImage img, int width,
			int height) {
		BufferedImage effect = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = effect.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		return effect;
	}

	public static BufferedImage rotateImage(BufferedImage img, int angle) {
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = effect.createGraphics();
		AffineTransform rot = new AffineTransform();
		rot.rotate(Math.toRadians(angle), img.getWidth() / 2,
				img.getHeight() / 2);
		g.transform(rot);
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return effect;
	}

	public static BufferedImage tileImage(BufferedImage img, int width,
			int height) {
		BufferedImage effect = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = effect.createGraphics();
		for (int x = 0; x < width; x += img.getWidth())
			for (int y = 0; y < height; y += img.getHeight()) {
				g.drawImage(img, x, y, null);
			}
		g.dispose();
		return effect;
	}

	public static BufferedImage zapImage(BufferedImage img, float likelihood) {
		int redCol = 0xf90000; // nearly full-on red
		int yellowCol = 0xf9fd00; // a mix of red and green

		int imWidth = img.getWidth();
		int imHeight = img.getHeight();

		int[] pixels = new int[imWidth * imHeight];
		img.getRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);

		for (int i = 0; i < pixels.length; i++) {
			if (Math.random() <= likelihood) {
				if (Math.random() <= 0.8)
					pixels[i] = pixels[i] | redCol;
				else
					pixels[i] = pixels[i] | yellowCol;
			}
		}
		BufferedImage effect = new BufferedImage(img.getWidth(), img
				.getHeight(), BufferedImage.TYPE_INT_ARGB);

		effect.setRGB(0, 0, imWidth, imHeight, pixels, 0, imWidth);
		return effect;
	}
}
