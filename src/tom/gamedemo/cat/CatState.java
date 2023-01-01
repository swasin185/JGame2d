package tom.gamedemo.cat;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import tom.game.State;

public class CatState extends State {
	protected JumpingCat cat;

	public CatState(int width, int height, String name) {
		super(width, height, name);
		this.setBackground(Color.RED);
		this.setStatus(PLAYING);
		cat = new JumpingCat(200, 200, this);
	}

	public int getFloor() {
		return this.getHeight() - 70;
	}

	@Override
	public void init() {
	}

	@Override
	public void update() {
		super.update();
		cat.update();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		cat.paint(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_RIGHT)
			cat.setKeyRight(true);
		else if (code == KeyEvent.VK_LEFT)
			cat.setKeyLeft(true);
		else if (code == KeyEvent.VK_SPACE)
			cat.setKeyUp(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_RIGHT)
			cat.setKeyRight(false);
		else if (code == KeyEvent.VK_LEFT)
			cat.setKeyLeft(false);
		else if (code == KeyEvent.VK_SPACE)
			cat.setKeyUp(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// this.setNextState("Cat2");
	}
}
