package tom.gamedemo.cat;

import java.awt.image.BufferedImage;

import tom.game.AnimateSprite;
import tom.game.Animation;
import tom.game.ImageLoader;
import tom.game.ImageSFXs;
import tom.game.Map;

public class JumpingCat extends AnimateSprite {

	private static BufferedImage[] leftImages;
	private static BufferedImage[] rightImages;
	static {
		rightImages = ImageLoader.loadImagesRes("tom/gamedemo/cat/cats.gif", 6);
		leftImages = new BufferedImage[rightImages.length];
		for (int i = 0; i < rightImages.length; i++)
			leftImages[i] = ImageSFXs.flipImage(rightImages[i], false, true);
	}
	private int maginLeft;
	private int maginRight;
	private int step = 5;

	private float gForce = 25f;
	private final float gravity = 1.5f;
	private int jumpTime;
	private int fallTime;
	private boolean keyDown;
	private int jumpY;

	private boolean keyLeft;

	private boolean keyRight;

	private boolean keyUp;

	private Animation leftAni;

	private Animation rightAni;

	public JumpingCat(int x, int y, CatState state) {
		super(x, y, JumpingCat.rightImages, false, true, state);
		maginLeft = 200;
		maginRight = state.getWidth() - maginLeft;
		this.rightAni = super.mainAni;
		this.leftAni = new Animation(JumpingCat.leftImages, false, true);
	}

	private int getJumpY(float force, int time) {
		int lastJumpY = jumpY;
		jumpY = (int) Math.round(force * time - (gravity * time * time));

		return jumpY - lastJumpY;
	}

	public boolean isKeyDown() {
		return keyDown;
	}

	public boolean isKeyLeft() {
		return keyLeft;
	}

	public boolean isKeyRight() {
		return keyRight;
	}

	public boolean isKeyUp() {
		return keyUp;
	}

	public void moveLeft() {
		super.mainAni = leftAni;
		this.mainAni.setAutoAnimated(true);
		if (this.x > 100)
			this.x -= 5;
	}

	public void moveRight() {
		super.mainAni = rightAni;
		this.mainAni.setAutoAnimated(true);
		if (this.x < state.getWidth() - 100)
			this.x += 5;
	}

	public void setKeyDown(boolean keyDown) {
		this.keyDown = keyDown;
	}

	public void setKeyLeft(boolean keyLeft) {
		this.keyLeft = keyLeft;
	}

	public void setKeyRight(boolean keyRight) {
		this.keyRight = keyRight;
	}

	public void setKeyUp(boolean keyUp) {
		this.keyUp = keyUp;
	}

	public void stopMove() {
		this.mainAni.setAutoAnimated(false);
	}

	public boolean isJumping() {
		return this.jumpTime > 0;
	}

	@Override
	public void update() {
		super.update();
		int floor = ((CatState) state).getFloor();
		if ((this.isKeyLeft() || this.isKeyRight()) && !isJumping()) {
			this.mainAni.setAutoAnimated(true);
		} else {
			this.mainAni.setAutoAnimated(false);
			if (jumpTime > 0) {
				this.mainAni.setAutoResetFrame(2);
			} else
				this.mainAni.setAutoResetFrame(0);
		}

		if (this.isKeyLeft() && this.mainAni == rightAni)
			this.mainAni = leftAni;
		if (this.isKeyRight() && this.mainAni == leftAni)
			this.mainAni = rightAni;

		if (this.isKeyRight() && this.x < maginRight)
			this.x += step;
		if (this.isKeyLeft() && this.x > maginLeft)
			this.x -= step;

		if ((isKeyUp() || isJumping()) && !isFalling()) {
			this.jumpTime++;
			this.y -= this.getJumpY(this.gForce, jumpTime);
			if (getFoot() >= floor) {
				y = floor - (size / 2);
				jumpTime = 0;
			}
		}
		if ((getFoot() <= floor || isFalling()) && !isJumping()) {
			this.fallTime++;
			this.y -= this.getJumpY(0, fallTime);
			if (getFoot() >= floor) {
				y = floor - (size / 2);
				fallTime = 0;
				jumpY = 0;
			}
		}
	}

	private int getFoot() {
		return y + (size / 2);
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getStep() {
		return step;
	}

	public boolean isFalling() {
		return fallTime > 0;
	}

	@Override
	public byte getBlock(Map map) {
		byte block = 0;
		short r1 = (short) ((y + map.getYInit() - this.height_2) / map
				.getScale());
		short r2 = (short) ((y + map.getYInit() + this.height_2) / map
				.getScale());
		short c1 = (short) ((x + map.getXInit() - this.width_2)
				/ map.getScale() + 1);
		short c2 = (short) ((x + map.getXInit() + this.width_2)
				/ map.getScale() + 1);
		for (short r = r1; r < r2 && block == 0; r++) {
			for (short c = c1; c < c2 && block == 0; c++) {
				block = map.get(r, c);
			}
		}

		return block;
	}
}
