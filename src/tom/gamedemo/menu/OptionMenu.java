package tom.gamedemo.menu;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import tom.game.ButtonSprite;
import tom.game.ImageLoader;
import tom.game.State;

public class OptionMenu extends State {

	private ButtonSprite backButton;
	private ButtonSprite bgSoundButton;
	private ButtonSprite effectSoundButton;

	public OptionMenu(int width, int height, String name) {
		super(width, height, name);
	}

	public void loadResource() {
		this.setBgImage(ImageLoader.loadImage(ClassLoader
				.getSystemResource("tom/gamedemo/menu/optionBG.jpg")));
		backButton = new ButtonSprite(100, 500, ImageLoader
				.loadImageRes("tom/gamedemo/menu/Back.png"), null);
		bgSoundButton = new ButtonSprite(600, 263, ImageLoader
				.loadImageRes("tom/gamedemo/menu/SoundOff.png"), ImageLoader
				.loadImageRes("tom/gamedemo/menu/SoundOn.png"));
		effectSoundButton = new ButtonSprite(600, 333, ImageLoader
				.loadImageRes("tom/gamedemo/menu/SoundOff.png"), ImageLoader
				.loadImageRes("tom/gamedemo/menu/SoundOn.png"));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		backButton.paint(g);
		bgSoundButton.paint(g);
		effectSoundButton.paint(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (backButton.isCollided(x, y)) {
			this.setNextState("Main");
		} else if (bgSoundButton.isCollided(x, y)) {
			bgSoundButton.setButtonStatus(!bgSoundButton.getButtonStatus());
		} else if (effectSoundButton.isCollided(x, y)) {
			effectSoundButton.setButtonStatus(!effectSoundButton
					.getButtonStatus());
		}
	}
}
