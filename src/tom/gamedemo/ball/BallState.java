package tom.gamedemo.ball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import tom.game.EffectManager;
import tom.game.ImageLoader;
import tom.game.State;

public class BallState extends State {

    protected BallGroup ballGroup;
    protected EffectManager hitSound;
    protected EffectManager catchSound;

    public BallState(int width, int height, String name) {
        super(width, height, name);
        this.setBackground(Color.BLACK);
    }

    @Override
    public void loadResource() {
        BufferedImage[] imgs = ImageLoader.loadImagesRes("tom/gamedemo/ball/smoke.gif", 6);
        URL url = ClassLoader.getSystemResource("tom/gamedemo/ball/ding.au");
        hitSound = new EffectManager(imgs, url, 10);
        imgs = ImageLoader.loadImagesRes("tom/gamedemo/ball/kaboom.gif", 6);
        url = ClassLoader.getSystemResource("tom/gamedemo/ball/bomb.wav");
        catchSound = new EffectManager(imgs, url, 10, 2);
    }

    @Override
    public void init() {
        super.init();
        ballGroup = new BallGroup(this);
    }

    @Override
    public void update() {
        super.update();
        hitSound.resetIgnore();
        ballGroup.update();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.white);

        super.showStatus(g);
        if (ballGroup != null) {
            ballGroup.paint(g);
        }

    }

    public BallGroup getBallGroup() {
        return ballGroup;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (this.getStatus()) {
            case State.PLAYING:
                ballGroup.cacth(e.getX(), e.getY());
                break;
            case State.STOP:
            case State.PAUSE:
                this.setStatus(State.PLAYING);
                break;
            case State.CLEAR:
                this.setNextState("State2");
                break;
        }

    }

    @Override
    public boolean isCleared() {
        return ballGroup.getActionTotal() == 0;
    }

    public EffectManager getHitSound() {
        return hitSound;
    }

    public EffectManager getCatchSound() {
        return catchSound;
    }

    @Override
    public void paintMenu(Graphics g) {
        if (this.getStatus() == State.STOP || this.getStatus() == State.PAUSE) {
            g.setColor(Color.WHITE);
            g.fillRect(200, 200, 200, 150);
            g.setColor(Color.BLUE);
            g.drawString("Click on the ball !", 240, 240);
            g.drawString("To clear all balls.", 240, 270);
            g.drawString("click to continue.", 240, 300);
        }
        if (this.getStatus() == State.OVER) {
            g.setColor(Color.RED);
            g.fillRect(230, 250, 150, 50);
            g.setColor(Color.WHITE);
            g.drawString("Game Over!", 260, 280);
        }
    }

    @Override
    public void paintEffect(Graphics g) {
        hitSound.paint(g);
        catchSound.paint(g);
    }
}
