package tom.game;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;

public class SplashScreen extends Frame {

	private static final long serialVersionUID = 1L;
	private BufferedImage img;

	public SplashScreen(URL imgURL, int x, int y) {
		this.setLocation(x, y);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		img = ImageLoader.loadImage(imgURL);
		this.setSize(img.getWidth(), img.getHeight());
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(img, 0, 0, this);
	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
