package tom.gamedemo.ball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tom.game.AnimateSprite;
import tom.game.ImageLoader;
import tom.game.State;

public class UFO extends AnimateSprite {

    private int live;
    private boolean moveUp;
    private boolean moveDown;
    private boolean moveLeft;
    private boolean moveRight;

    private int speed = 4;

    private static BufferedImage img;

    static {
        img = ImageLoader.loadImage(ClassLoader
                .getSystemResource("tom/gamedemo/ball/ufo.gif"));
    }

    public UFO(State state) {
        super(state.getWidth() / 2, state.getHeight() / 2, img, state);
        live = 0;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    @Override
    public void update() {
        super.update();
        live++;
        if (moveUp) {
            y -= speed;
        }
        if (moveDown) {
            y += speed;
        }
        if (moveLeft) {
            x -= speed;
        }
        if (moveRight) {
            x += speed;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isActioned() {
        return super.actioned;
    }

    public boolean isImmotal() {
        return live < 50;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isImmotal()) {
            int radius = size >> 1;
            g.setColor(Color.red);
            g.drawOval(x - radius, y - radius, size, size);
        }

    }
}
