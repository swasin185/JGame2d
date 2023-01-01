package tom.game;

import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class GameScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private Game game;
	private BufferStrategy bufferStrategy;
	private Toolkit toolkit;

	public GameScreen(int fps, StateController control) {
		this.toolkit = Toolkit.getDefaultToolkit();
		setIgnoreRepaint(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		setSize(control.getWidth(), control.getHeight());

		//this.game = new Game(fps, this, control);
	}

	public void run() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();

		GraphicsDevice gd = ge.getDefaultScreenDevice();
		if (gd.isFullScreenSupported()) {
			DisplayMode dm = new DisplayMode(this.getWidth(), this.getHeight(),
					32, DisplayMode.REFRESH_RATE_UNKNOWN);
			gd.setFullScreenWindow(this);
			gd.setDisplayMode(dm);
			try {
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						GameScreen.this.createBufferStrategy(2);
					}
				});
			} catch (Exception e) {
				System.out.println("Error while creating buffer strategy");
				System.exit(0);
			}
			bufferStrategy = this.getBufferStrategy();
		}
		setVisible(true);
		game.start();
	}

	public void paint(Graphics g) {
		if (game != null && bufferStrategy != null) {
			game.paint(bufferStrategy.getDrawGraphics());
			if (!bufferStrategy.contentsLost())
				bufferStrategy.show();
			toolkit.sync();
		}
	}

}
