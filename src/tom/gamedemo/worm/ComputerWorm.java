package tom.gamedemo.worm;

import java.awt.Color;
import java.util.Set;

import tom.game.Sprite;

public class ComputerWorm extends Worm {

	private int level = 0;

	public ComputerWorm(Color color, int x, int y, WormState state) {
		super(color, x, y, state);
	}

	private void moveToNearestFood() {
		WormState st = (WormState) state;
		Set<Sprite> foods = st.getFoods();
		int distance = 0;
		int minDistance = 0;
		int minX = 0;
		int minY = 0;
		for (Sprite food : foods) {
			distance = Math.abs(food.getX() - this.getX())
					+ Math.abs(food.getY() - this.getY());
			if (minDistance == 0 || minDistance > distance) {
				minDistance = distance;
				minX = food.getX();
				minY = food.getY();
			}
		}
		int dx = this.getX() - minX;
		int dy = this.getY() - minY;
		if (Math.abs(dx) >= Worm.STEP)
			if (dx < 0)
				this.setDirection(Worm.MOVE_RIGHT);
			else
				this.setDirection(Worm.MOVE_LEFT);
		if (Math.abs(dy) >= Worm.STEP)
			if (dy < 0)
				this.setDirection(Worm.MOVE_DOWN);
			else
				this.setDirection(Worm.MOVE_UP);
	}

	public void update() {
		super.update();
		if (level == 0) {
			if (state.getRound() % 10 == 0)
				super.setDirection((int) (Math.random() * 3 + 0.5) + 1);
		} else if (level == 1) {
			this.moveToNearestFood();
		}
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

}
