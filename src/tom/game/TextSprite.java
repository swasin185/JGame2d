package tom.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextSprite extends Sprite {

	protected Font font;

	protected String text;

	public TextSprite(int x, int y, String text, Color color, Font font) {
		super(x, y, -1, color, null);
		this.font = font;
		this.setText(text);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(this.color);
		g.setFont(font);
		g.drawString(text, x, y);
	}

	public void setText(String text) {
		this.text = text;
	}

}
