package tom.gamedemo.worm;

import java.awt.Color;
import java.awt.Graphics;

import tom.game.Sprite;
import tom.game.State;

public class Worm extends Sprite {

	public static int NO_MOVE = 0;
	public static int MOVE_LEFT = 1;
	public static int MOVE_RIGHT = 4;
	public static int MOVE_UP = 3;
	public static int MOVE_DOWN = 2;

	private int direction = Worm.NO_MOVE;

	protected static final int STEP = 5;

	private int[] xCells;

	private int[] yCells;

	private int head;

	private int distance = 0;

	private int delay = 50;

	public Worm(Color color, int x, int y, State state) {
		super(x, y, STEP, color, state);
		xCells = new int[30];
		yCells = new int[30];
		for (int i = 0; i < xCells.length; i++) {
			xCells[i] = x;
			yCells[i] = y;
		}
		super.setAction(true);
	}

	public void setDirection(int direct) {
		if (direct + this.direction != 5)
			this.direction = direct;
	}

	public void update() {
		if (super.actioned) {
			if (direction != Worm.NO_MOVE)
				distance++;

			if (direction == Worm.MOVE_LEFT)
				x -= STEP;
			if (direction == Worm.MOVE_RIGHT)
				x += STEP;
			if (direction == Worm.MOVE_UP)
				y -= STEP;
			if (direction == Worm.MOVE_DOWN)
				y += STEP;
			if (x < 0)
				x = state.getWidth() - STEP;
			if (x > state.getWidth() - STEP)
				x = 0;
			if (y < 0)
				y = state.getHeight() - STEP;
			if (y > state.getHeight() - STEP)
				y = 0;
			
			super.actioned = !(this.direction > 0 && this.isCollided(x, y));

			head = (head + 1) % xCells.length;
			xCells[head] = x;
			yCells[head] = y;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isCollided(Worm worm) {
		boolean collided = false;
		int i = 0;
		while (!collided && i < xCells.length) {
			collided = worm.isCollided(xCells[i], yCells[i]);
			i++;
		}
		return collided;
	}

	public boolean isCollided(int x, int y) {
		boolean collided = false;
		int i = 0;
		while (!collided && i < xCells.length) {
			collided = (x >= xCells[i] && x < xCells[i] + STEP
					&& y >= yCells[i] && y < yCells[i] + STEP);
			i++;
		}
		return collided;
	}

	public boolean isCollided(Sprite food) {
		int x = food.getX();
		int y = food.getY();
		boolean collided = false;
		int i = 0;
		int diff = Worm.STEP;
		while (!collided && i < xCells.length) {
			collided = (x >= xCells[i] - diff && x < xCells[i] + STEP + diff
					&& y >= yCells[i] - diff && y < yCells[i] + STEP + diff);
			i++;
		}
		return collided;
	}

	protected int getDistance() {
		return distance;
	}

	public void eatFood() {
		int[] xTemp = xCells;
		int[] yTemp = yCells;
		int grow = 20;
		xCells = new int[xCells.length + grow];
		yCells = new int[xCells.length];
		int tail = (head + 1) % xTemp.length;
		for (int i = 0; i <= grow; i++) {
			xCells[i] = xTemp[tail];
			yCells[i] = yTemp[tail];
		}
		for (int i = grow + 1; i < xCells.length; i++) {
			tail = (tail + 1) % xTemp.length;
			xCells[i] = xTemp[tail];
			yCells[i] = yTemp[tail];
		}
		head = xCells.length - 1;
		delay -= (delay * 0.1);
	}

	public int getSize() {
		return xCells.length;
	}

	public void paint(Graphics g) {
		g.setColor(color);
		for (int i = 0; i < xCells.length; i++)
			g.fillRect(xCells[i], yCells[i], STEP, STEP);
	}

}
