package tom.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static BufferedImage loadImageRes(String uri) {
		return ImageLoader.loadImage(ClassLoader.getSystemResource(uri));
	}

	public static BufferedImage[] loadImagesRes(String uri, int num) {
		return ImageLoader.loadImages(ClassLoader.getSystemResource(uri), num);
	}

	public static BufferedImage loadImage(URL url) {
		BufferedImage bim = null;
		try {
			bim = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bim;
	}

	public static BufferedImage loadImage(String fileName) {
		BufferedImage bim = null;
		try {
			bim = ImageIO.read(new URL("file:" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bim;
	}

	public static BufferedImage[] loadImagesRes(String name, int num, String ext) {
		BufferedImage[] images = new BufferedImage[num];
		for (int i = 0; i < num; i++) {
			System.out.println(name + i + ext);
			images[i] = ImageLoader.loadImageRes(name + i + ext);
		}
		return images;
	}

	public static BufferedImage[] loadImages(URL url, int num) {
		BufferedImage[] images = new BufferedImage[num];
		BufferedImage stripImage = ImageLoader.loadImage(url);
		int width = stripImage.getWidth() / num;
		int height = stripImage.getHeight();
		Graphics2D g2d;
		for (int i = 0; i < num; i++) {
			images[i] = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			g2d = images[i].createGraphics();
			g2d.drawImage(stripImage, 0, 0, width, height, i * width, 0,
					(i + 1) * width, height, null);
			g2d.dispose();
		}
		return images;
	}

	public static BufferedImage[] loadImages(String fileName, int num) {
		URL url = null;
		try {
			url = new URL("file:" + fileName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ImageLoader.loadImages(url, num);
	}

}
