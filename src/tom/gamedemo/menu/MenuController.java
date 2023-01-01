package tom.gamedemo.menu;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import tom.game.GamePanel;
import tom.game.ImageLoader;
import tom.game.State;
import tom.game.StateController;

public class MenuController extends StateController {

	public static void main(String[] args) {
		GamePanel game = new GamePanel(30, new MenuController());
		Toolkit tk = Toolkit.getDefaultToolkit();
		BufferedImage im = ImageLoader.loadImage(ClassLoader
				.getSystemResource("tom/gamedemo/menu/Cursor.png"));
		Cursor invisCursor = tk.createCustomCursor(im, new Point(0, 28), null);
		game.setCursor(invisCursor);
		game.run("Menu Demo");

	}

	public MenuController() {
		super("Main", 800, 600);
	}

	@Override
	public void changeState() {
		super.changeState();
	}

	@Override
	public State createState(String name) {
		State s = null;
		if (name.equals("Main"))
			s = new Menu(this.getWidth(), this.getHeight(), name);
		else if (name.equals("Option"))
			s = new OptionMenu(this.getWidth(), this.getHeight(), name);

		s.setStatus(State.PLAYING);
		return s;
	}

}
