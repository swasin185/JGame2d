package tom.gamedemo.jvrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import tom.game.State;

public class FieldState extends State {

	private FieldMap map;

	private Robot robot;

	public FieldState(int width, int height, String name) {
		super(width, height, name);
		this.setBackground(Color.BLACK);
		this.setStatus(PLAYING);
	}

	@Override
	public void init() {
		super.init();
		map = new FieldMap(32, 32, 20, this);
		map.loadFromFile(ClassLoader
				.getSystemResource("tom/gamedemo/jvrobot/map1.map"));

		robot = new Robot(0, 0, map.getScale(), Color.YELLOW, this);
		robot.setEnergy(100);
	}

	@Override
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		map.paint(g);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		robot.paint(g);
	}

	@Override
	public void update() {
		super.update();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP)
			robot.forward();
		else if (code == KeyEvent.VK_LEFT)
			robot.left();
		else if (code == KeyEvent.VK_RIGHT)
			robot.right();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		final int row = e.getY() / robot.getSize();
		final int col = e.getX() / robot.getSize();
		new Thread() {
			@Override
			public void run() {
				robot.moveTo(row, col);
			}
		}.start();
	}

	public FieldMap getMap() {
		return this.map;
	}
}
