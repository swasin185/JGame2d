package tom.game;

import java.awt.Color;
import java.awt.Graphics;

public class Sprite {

	protected String name;

	protected int x = 0;

	protected int y = 0;

	protected State state;

	protected int size;

	protected int width;

	protected int width_2;

	protected int height;

	protected int height_2;

	protected Color color;

	protected Color colorInv;

	protected boolean actioned = false;

	public Sprite() {
	}

	public Sprite(int x, int y, int size, Color color, State state) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.width = size;
		this.height = size;
		this.width_2 = size >> 1;
		this.height_2 = this.width_2;
		this.setColor(color);
		this.state = state;
	}

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public byte getBlock(Map map) {
		return map.get(x + map.getXInit(), y + map.getYInit());
	}

	public boolean isCollided(Sprite sprite) {
		if (this != sprite && sprite != null) {
			int dx = this.x - sprite.x;
			int dy = this.y - sprite.y;
			double ballDistance = Math.sqrt(dx * dx + dy * dy);
			int collideDistance = (this.size + sprite.size) >> 1;
			/* r is ratio of collide difference and ball distance */
			double r = (collideDistance - ballDistance) / ballDistance;
			/* if ball is overlape then seperate balls */
			if (r > 0) {
				this.x += (int) Math.round((this.x - sprite.x) * r);
				this.y += (int) Math.round((this.y - sprite.y) * r);
			}
			return ballDistance <= collideDistance;
		} else
			return false;
	}

	public boolean isCollided(int x, int y) {
		int dx = this.x - x;
		int dy = this.y - y;
		double distance = Math.sqrt(dx * dx + dy * dy) * 2;
		return distance <= size;
	}

	public boolean isCollided(int x, int y, int width, int height) {
		boolean collided = false;
		int thisx = this.x - size / 2;
		int thisy = this.y - size / 2;
		int dx = thisx - x;
		int dy = thisy - y;
		if (dx > 0)
			collided = dx < width;
		else
			collided = dx > -size;

		if (dy > 0)
			collided = collided && dy < height;
		else
			collided = collided && dy > -size;

		if (collided) {
			if (dx > 0 && dx < width) {
				this.setX(x + width + size / 2);
			} else if (dx > -size) {
				this.setX(x - size / 2);
			}
		}
		return collided;
	}

	public void paint(Graphics g) {
		if (color != null) {
			g.setColor(this.color);
			g.fillOval(x - width_2, y - height_2, size, size);
			g.setColor(this.colorInv);
			g.drawOval(x - width_2, y - height_2, size, size);
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	protected void setColor(Color color) {
		this.color = color;
		this.colorInv = new Color((color.getRed() + 127) % 255, (color
				.getGreen() + 127) % 255, (color.getBlue() + 127) % 255);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAction(boolean actioned) {
		this.actioned = actioned;
	}

	public boolean isActioned() {
		return actioned;
	}

	public void update() {
	}

	public void randomColor() {
		int red = (int) (Math.random() * 255);
		int green = (int) (Math.random() * 255);
		int blue = (int) (Math.random() * 255);
		this.setColor(new Color(red, green, blue));
	}

	public void randomXY() {
		this.setX((int) (Math.random() * state.getWidth()));
		this.setY((int) (Math.random() * state.getHeight()));
	}

	public void stop() {

	}

	public int getSize() {
		return size;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return width;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		if (this.state != state) {
			this.state = state;
			this.state.setPlayer(this);
		}
	}

}
