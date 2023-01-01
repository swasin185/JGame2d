package tom.gamedemo.ball;

import java.awt.Color;

import tom.game.EffectManager;
import tom.game.Sprite;
import tom.game.SpriteGroup;

public class BallGroup extends SpriteGroup {

    private EffectManager hitSound;
    private EffectManager catchSound;

    public BallGroup(BallState state) {
        super(state);
        hitSound = ((BallState) state).getHitSound();
        catchSound = ((BallState) state).getCatchSound();
        list = new Sprite[10];
        for (int i = 0; i < list.length; i++) {
            Ball ball = new Ball(0, 0, 20 + i / 2, Color.red, (BallState) state);
            ball.setSpeed(2 + i);
            ball.randomColor();
            ball.randomDirection();
            ball.randomXY();
            list[i] = ball;
        }
        this.actionAll();
    }

    public void reflect(Ball ball) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isActioned()) {
                if (ball.isCollided(list[i])) {
                    ball.reflect(ball.getCollideAngle((Ball) list[i]));
                    hitSound.play((list[i].getX() + ball.getX()) >> 1, (list[i]
                            .getY() + ball.getY()) >> 1, ball);
                    hitSound.addIgnore(list[i]);
                }
            }
        }
    }

    public void cacth(int x, int y) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isActioned()) {
                if (list[i].isCollided(x, y)) {
                    list[i].setAction(false);
                    catchSound.play(x, y);
                }
            }
        }
    }

    public void hitSpaceShip(UFO ufo) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isActioned()) {
                if (list[i].isCollided(ufo) && !ufo.isImmotal()) {
                    // list[i].setAction(false);
                    ufo.setAction(false);
                    catchSound.play(ufo.getX(), ufo.getY());
                }
            }
        }

    }
}
