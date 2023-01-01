package tom.gamedemo.jvrobot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tom.game.ImageLoader;
import tom.game.ImageSFXs;
import tom.game.Map;
import tom.game.Sprite;
import tom.game.State;

public class Robot extends Sprite implements IRobot {
	public Robot(int row, int col, int size, Color color, State state) {
		super(col * size, row * size, size, color, state);
		this.setSpeed(1);
		this.map = ((FieldState) state).getMap();
	}

	private static BufferedImage[] robotImages;

	static {
		robotImages = new BufferedImage[4];
		robotImages[NORTH] = ImageLoader
				.loadImageRes("tom/gamedemo/jvrobot/jvrobot.gif");
		robotImages[NORTH] = ImageSFXs.resizeImage(robotImages[NORTH], 20, 20);
		robotImages[EAST] = ImageSFXs.rotateImage(robotImages[NORTH], 90);
		robotImages[SOUTH] = ImageSFXs.rotateImage(robotImages[NORTH], 180);
		robotImages[WEST] = ImageSFXs.rotateImage(robotImages[NORTH], 270);
	}

	private FieldMap map;

	private byte direction = NORTH;

	private int moveCount = 0;

	private int turnCount = 0;

	private int delayTime = 100;

	private int delayMove;

	private double eCapacity = 500;

	private double eConsume = 1;

	private double energy = 0;

	private double speed = 1;

	private boolean isAutoPilot = false;

	@Override
	public void paint(Graphics g) {
		g.setColor(this.color);
		g.fillRect(x, y, size, size);
		g.drawImage(robotImages[direction], x, y, null);
	}

	public void forward() {
		int xOld = this.x;
		int yOld = this.y;
		moveCount++;
		switch (direction) {
		case NORTH:
			if (y > 0)
				y -= size;
			break;
		case EAST:
			if (x < state.getWidth() - size - 1)
				x += size;
			break;
		case SOUTH:
			if (y < state.getHeight() - size - 1)
				y += size;
			break;
		case WEST:
			if (x > 0)
				x -= size;
			break;
		}
		if (this.getBlock(map) > Map.SPACE) {
			x = xOld;
			y = yOld;
		} else {
			this.delay();
		}
	}

	public void right() {
		this.turnCount++;
		if (direction == 3)
			direction = 0;
		else
			direction++;
		this.delay();
	}

	public void left() {
		this.turnCount++;
		if (direction == 0)
			direction = 3;
		else
			direction--;
		this.delay();
	}

	public void forward(int move) {
		if (!this.isAutoPilot) {
			this.isAutoPilot = true;
			for (int i = 0; i < move; i++)
				this.forward();
			this.isAutoPilot = false;
		}
	}

	private void delay() {
		if (energy > eConsume) {
			energy -= eConsume;
		} else {
			this.setSpeed(1);
		}

		try {
			Thread.sleep(delayMove);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(double speed) throws RuntimeException {
		if (speed < 1 || speed > 2)
			throw new RuntimeException("Speed value must between 1.0 - 2.0");
		this.speed = speed;
		this.eConsume = Math.pow(4, speed - 1);
		this.delayMove = (int) Math.round(delayTime / speed);
	}

	public final void moveTo(int row, int column) {
		this.delay();
		int x = column * size;
		int y = row * size;
		int dx = Math.abs(x - this.x) / size;
		int ix = this.x < x ? 1 : -1;

		int dy = Math.abs(y - this.y) / size;
		int iy = this.y < y ? 1 : -1;
		if (dx > 0) {
			if (ix > 0)
				this.set(EAST);
			if (ix < 0)
				this.set(WEST);
			this.forward(dx);
		}
		if (dy > 0) {
			if (iy > 0)
				this.set(SOUTH);
			if (iy < 0)
				this.set(NORTH);
			this.forward(dy);
		}
	}

	/**
	 * Set direction of this robot. When called in RobotProgram.setup() it will
	 * set start position of this robot at the conner of field.
	 * <P>
	 * <blockquote>
	 * 
	 * <pre>
	 *                    set(EAST) - start at upper, left conner 
	 *                    set(WEST) - start at lower, right conner
	 *                    set(NORTH) - start at lower, left conner 
	 *                    set(SOUTH) - start at upper, right conner
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param direction
	 *            Direction for {@code RobotProgram.DIRECTION} such as EAST,
	 *            WEST, NORTH, SOUTH.
	 */
	public final void set(byte direction) {
		if (this.direction == WEST && direction == NORTH)
			this.right();
		else if (this.direction == NORTH && direction == WEST)
			this.left();
		while (this.direction != direction)
			if ((direction - this.direction) > 0)
				this.right();
			else
				this.left();
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy += energy;
		if (this.energy > this.eCapacity)
			this.energy = this.eCapacity;
	}
}
