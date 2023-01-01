package tom.gamedemo.menu;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import tom.game.ButtonSprite;
import tom.game.ImageLoader;
import tom.game.State;

public class Menu extends State {

	private int slideX;
	private ButtonSprite playButton;
	private ButtonSprite optionButton;
	private ButtonSprite creditButton;
	private ButtonSprite exitButton;

	public Menu(int width, int height, String name) {
		super(width, height, name);
	}

	@Override
	public void loadResource() {
		this.setBgImage(ImageLoader.loadImage(ClassLoader
				.getSystemResource("tom/gamedemo/menu/BG.jpg")));
		playButton = new ButtonSprite(0, 250, ImageLoader
				.loadImageRes("tom/gamedemo/menu/Play.gif"), ImageLoader
				.loadImageRes("tom/gamedemo/menu/Play1.gif"));
		optionButton = new ButtonSprite(0, 310, ImageLoader
				.loadImageRes("tom/gamedemo/menu/Option.gif"), ImageLoader
				.loadImageRes("tom/gamedemo/menu/Option1.gif"));
		creditButton = new ButtonSprite(0, 370, ImageLoader
				.loadImageRes("tom/gamedemo/menu/Credit.gif"), ImageLoader
				.loadImageRes("tom/gamedemo/menu/Credit1.gif"));
		exitButton = new ButtonSprite(0, 430, ImageLoader
				.loadImageRes("tom/gamedemo/menu/Exit.gif"), ImageLoader
				.loadImageRes("tom/gamedemo/menu/Exit1.gif"));
	}

	@Override
	public void init() {
		super.init();
		slideX = 0;
	}

	private void slide() {
		if (slideX < 400) {
			slideX += 15;
			playButton.setX(slideX);
			optionButton.setX(slideX);
			creditButton.setX(slideX);
			exitButton.setX(slideX);
		}
	}

	@Override
	public void paintMenu(Graphics g) {
		playButton.paint(g);
		optionButton.paint(g);
		creditButton.paint(g);
		exitButton.paint(g);
	}

	@Override
	public void update() {
		super.update();
		this.slide();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (exitButton.isCollided(x, y))
			System.exit(0);
		if (optionButton.isCollided(x, y)) {
			this.setNextState("Option");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (exitButton.isCollided(x, y))
			exitButton.setButtonStatus(true);
		else
			exitButton.setButtonStatus(false);
		if (optionButton.isCollided(x, y))
			optionButton.setButtonStatus(true);
		else
			optionButton.setButtonStatus(false);
		
		if (creditButton.isCollided(x, y))
			creditButton.setButtonStatus(true);
		else
			creditButton.setButtonStatus(false);
		
		if (playButton.isCollided(x, y))
			playButton.setButtonStatus(true);
		else
			playButton.setButtonStatus(false);
	}
}