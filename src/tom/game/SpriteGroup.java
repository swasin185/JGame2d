package tom.game;

import java.awt.Graphics;

public abstract class SpriteGroup {

	protected State state;

	public SpriteGroup(State state) {
	}

	protected Sprite[] list;

	public void actionAll() {
		for (int i = 0; i < list.length; i++)
			list[i].setAction(true);
	}

	public boolean action(int x, int y) {
		boolean idle = false;
		int i = 0;
		while (!idle && i < list.length) {
			idle = !list[i].isActioned();
			if (idle) {
				list[i].setX(x);
				list[i].setY(y);
				list[i].setAction(true);
			}
			i++;
		}
		return idle;
	}

	public void update() {
		for (Sprite sp : list)
			if (sp.isActioned())
				sp.update();
	}

	public void paint(Graphics g) {
		for (Sprite sp : list)
			if (sp.isActioned())
				sp.paint(g);
	}

	public State getState() {
		return state;
	}
	
	public int getActionTotal() {
		int count = 0;
		for (int i=0; i<list.length; i++) {
			if (list[i].isActioned()) count++;
		}
		return count;
	}
}
