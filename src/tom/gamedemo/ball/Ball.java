package tom.gamedemo.ball;

import java.awt.Color;

import tom.game.EffectManager;
import tom.game.Sprite;

public class Ball extends Sprite {

    private int speed = 4;

    private double direction;

    private int radius;

    private EffectManager hitSound;

    public Ball(int x, int y, int size, Color color, BallState state) {
        super(x, y, size, color, state);
        hitSound = state.getHitSound();
        radius = size / 2;
    }

    public void randomDirection() {
        this.direction = Math.random() * Math.PI * 2;
    }

    public void reflect(double pAngle) {
        this.direction = 2 * pAngle - this.direction;
    }

    public double getCollideAngle(Ball ball) {
        int dx = this.x - ball.x;
        int dy = this.y - ball.y;
        double angle = Math.PI / 2;
        if (dx != 0) {
            angle = Math.atan((double) dy / dx);
        }
        return angle - (Math.PI / 2);
    }

    public void update() {
        final int dx = (int) Math.round(Math.cos(direction) * getSpeed());
        final int dy = (int) Math.round(Math.sin(direction) * getSpeed());

        this.x += dx;
        this.y += dy;

        if (y <= radius || y + radius >= state.getHeight()) {
            if (y < radius) {
                y = radius;
            }
            if (y > state.getHeight() - radius) {
                y = state.getHeight() - radius;
            }
            this.reflect(0);
            if (y <= radius) {
                y = radius + 1;
            } else if (y + radius >= state.getHeight()) {
                y = state.getHeight() - radius - 1;
            }
            hitSound.play(x, y);
        }
        if (x <= radius || x + radius >= state.getWidth()) {
            if (x < radius) {
                x = radius;
            }
            if (x > state.getWidth() - radius) {
                x = state.getWidth() - radius;
            }
            this.reflect(Math.PI / 2);
            if (x <= radius) {
                x = radius + 1;
            } else if (x + radius >= state.getWidth()) {
                x = state.getWidth() - radius - 1;
            }
            hitSound.play(x, y);
        }

        ((BallState) state).getBallGroup().reflect(this);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

}
