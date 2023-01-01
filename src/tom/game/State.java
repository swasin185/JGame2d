package tom.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class State {

	public static final int STOP = 0;

	public static final int PLAYING = 1;

	public static final int PAUSE = 2;

	public static final int OVER = 3;

	public static final int CLEAR = 4;

	private int status = STOP;

	private int round;

	private int time;

	private int start;

	private SoundClip bgClip;

	private BufferedImage bgImage;

	private int width;

	private int height;

	private String name;

	private Color background;

	private String preState;

	private Sprite player;

	public Sprite getPlayer() {
		return player;
	}

	public void setPlayer(Sprite player) {
		if (this.player != player) {
			this.player = player;
			this.player.setState(this);
		}
	}

	public String getPreState() {
		return preState;
	}

	public void setPreState(String preState) {
		this.preState = preState;
	}

	private String nextState;

	public State(int width, int height, String name) {
		this.width = width;
		this.height = height;
		this.name = name;
		this.loadResource();
		time = 0;
		start = 0;
	}

	public void update() {
		if (this.isCleared())
			this.setStatus(CLEAR);
		else if (this.isOvered())
			this.setStatus(State.OVER);
		round++;
		time = (int) (System.nanoTime() / 1000000000) - start;
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void loadResource() {

	}

	public void init() {
		this.close();
		nextState = null;
		round = 0;
		if (start == 0)
			start = (int) (System.nanoTime() / 1000000000);

	}

	public void close() {
		if (bgClip != null)
			bgClip.stop();
	}

	public void paint(Graphics g) {
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		switch (status) {
		case PLAYING:
			if (this.status == PAUSE || this.status == STOP) {
				start = (int) (System.nanoTime() / 1000000000) - time;
				if (bgClip != null)
					bgClip.loop();
			} else if (this.status == OVER)
				init();
			break;
		case PAUSE:
			time = (int) (System.nanoTime() / 1000000000) - start;
			if (bgClip != null)
				bgClip.pause();
			break;
		case OVER:
		case CLEAR:
			if (bgClip != null)
				bgClip.stop();
			break;
		}
		this.status = status;
	}

	protected void showStatus(Graphics g) {
		long mem = Runtime.getRuntime().freeMemory() >> 10;
		g.drawString("Memory(kb): ", 5, 15);
		g.drawString(String.valueOf(mem), 75, 15);
		g.drawString("Time(ms): ", 5, 30);
		g.drawString(this.getTimeText(), 75, 30);
		g.drawString("Round(r): ", 5, 45);
		g.drawString(String.valueOf(round), 75, 45);
	}

	public int getTime() {
		return time;
	}

	public String getTimeText() {
		int minute = time / 60;
		int second = (time % 60);
		return String.format("%02d:%02d", minute, second);
	}

	public void setBgImage(BufferedImage bgImage) {
		this.bgImage = bgImage;
	}

	public void setBgClip(SoundClip bgClip) {
		this.bgClip = bgClip;
	}

	public boolean isCleared() {
		return false;
	}

	public boolean isOvered() {
		return false;
	}

	public String getName() {
		return name;
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

	public int getRound() {
		return round;
	}

	public void paintMenu(Graphics g) {
	}

	public void paintBackground(Graphics g) {
		if (bgImage != null)
			g.drawImage(bgImage, 0, 0, null);
		else {
			g.setColor(this.background);
			g.fillRect(0, 0, this.width, this.height);
		}
	}

	public void paintEffect(Graphics g) {
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public String getNextState() {
		return nextState;
	}

	public void setNextState(String stateName) {
		nextState = stateName;
	}
}
