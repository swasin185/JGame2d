package tom.gamedemo.ball;

import tom.game.GamePanel;
import tom.game.State;
import tom.game.StateController;

public class BallController extends StateController {

    public static void main(final String[] args) {
        GamePanel game = new GamePanel(25, new BallController(600, 600));
        game.run("Ball");
    }

    public BallController(int width, int height) {
        super("State1", width, height);
    }

    @Override
    public State createState(String name) {
        State state = null;
        if (name.equals("State1")) {
            state = new BallState(this.getWidth(), this.getHeight(), name);
        } else if (name.equals("State2")) {
            state = new SpaceshipState(this.getWidth(), this.getHeight(), name);
        }
        return state;
    }
}
