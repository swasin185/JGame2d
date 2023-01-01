package tom.gamedemo.ball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import tom.game.EffectManager;
import tom.game.State;

public class SpaceshipState extends BallState {
	private UFO ufo;

	public SpaceshipState(int width, int height, String name) {
		super(width, height, name);
	}

	@Override
	public void init() {
		super.init();
		ufo = new UFO(this);
		ufo.setAction(true);

	}

	@Override
	public boolean isOvered() {
		return !ufo.isActioned();
	}

	@Override
	public void update() {
		super.update();
		ballGroup.hitSpaceShip(ufo);
		ufo.update();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.white);
		super.showStatus(g);
		if (ballGroup != null)
			ballGroup.paint(g);
		if (ufo != null)
			ufo.paint(g);
		hitSound.paint(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (this.getStatus() == State.PLAYING) {
			if (code == KeyEvent.VK_UP)
				ufo.setMoveUp(true);
			else if (code == KeyEvent.VK_DOWN)
				ufo.setMoveDown(true);
			else if (code == KeyEvent.VK_LEFT)
				ufo.setMoveLeft(true);
			else if (code == KeyEvent.VK_RIGHT)
				ufo.setMoveRight(true);
			else if (code == KeyEvent.VK_ESCAPE)
				this.setStatus(State.PAUSE);
		} else
			this.setStatus(State.PLAYING);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP)
			ufo.setMoveUp(false);
		else if (code == KeyEvent.VK_DOWN)
			ufo.setMoveDown(false);
		else if (code == KeyEvent.VK_LEFT)
			ufo.setMoveLeft(false);
		else if (code == KeyEvent.VK_RIGHT)
			ufo.setMoveRight(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	public EffectManager getHitSound() {
		return hitSound;
	}

	public EffectManager getCatchSound() {
		return catchSound;
	}

	public void paintMenu(Graphics g) {
		if (this.getStatus() == State.STOP || this.getStatus() == State.PAUSE) {
			g.setColor(Color.WHITE);
			g.fillRect(200, 200, 240, 150);
			g.setColor(Color.BLUE);
			g.drawString("Control spaceship to avoid the ball!", 220, 240);
			g.drawString("Stay alive as long as you can.", 220, 270);
			g.drawString("Press any key to continue.", 220, 300);
		}
		if (this.getStatus() == State.OVER) {
			g.setColor(Color.RED);
			g.fillRect(230, 250, 150, 50);
			g.setColor(Color.WHITE);
			g.drawString("Game Over!", 260, 280);
		}
	}

}
