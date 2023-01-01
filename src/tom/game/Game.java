package tom.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Game extends Thread {

	private BufferedImage savedImage;

	private Graphics g2 = null;

	private boolean paused = false;

	protected StateController controller;

	protected int sleepTime;

	protected int maxSleepTime;

	protected long time;

	protected boolean running = true;

	protected Component scr;

	public Game(int fps, Component scr, StateController controller) {
		this.scr = scr;
		this.controller = controller;
		scr.addKeyListener(controller);
		scr.addMouseListener(controller);
		scr.addMouseMotionListener(controller);
		this.setDaemon(true);
		this.maxSleepTime = 1000 / fps;
		savedImage = new BufferedImage(scr.getWidth(), scr.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		g2 = savedImage.getGraphics();

	}

	protected void adjustSleepTime() {
		this.sleepTime = this.maxSleepTime
				- (int) (System.nanoTime() / 1000000 - time);
	}

	@Override
	public void run() {
		while (running) {
			this.time = System.nanoTime() / 1000000;
			if (controller != null)
				controller.update();
			scr.repaint();
			this.adjustSleepTime();
			if (this.sleepTime < 0) {
				Thread.yield();
			} else
				try {
					Thread.sleep(this.sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	public void paint(Graphics g) {
		tom.game.State st = null;
		if (controller != null)
			st = controller.getCurrentState();
		if (st != null) {
			if (st.getStatus() == tom.game.State.PAUSE) {
				if (!paused) {
					paused = true;
					controller.paint(g2);
				}
				g.drawImage(savedImage, 0, 0, null);
			} else {
				paused = false;
				controller.paint(g);
			}
		}
	}

	protected void showStatus(Graphics gScr) {
		long mem = Runtime.getRuntime().freeMemory() >> 10;
		gScr.setColor(Color.black);
		gScr.fillRect(0, 0, 150, 50);
		gScr.setColor(Color.lightGray);
		gScr.drawString("Time(ms): ", 5, 15);
		gScr.drawString(String.valueOf(time), 75, 15);
		gScr.drawString("Memory(kb): ", 5, 30);
		gScr.drawString(String.valueOf(mem), 75, 30);
		gScr.drawString("Sleep(ms): ", 5, 45);
		gScr.drawString(String.valueOf(this.sleepTime), 75, 45);
	}

}
