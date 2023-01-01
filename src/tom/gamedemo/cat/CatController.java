package tom.gamedemo.cat;

import tom.game.GamePanel;
import tom.game.GameScreen;
import tom.game.State;
import tom.game.StateController;

public class CatController extends StateController {

    public static void main(final String[] args) {

        GamePanel game = new GamePanel(30, new CatController());
        game.run("Cat");
    }

    public CatController() {
        super("Cat2", 600, 350);
    }

    @Override
    public void changeState() {
        super.changeState();
        this.getCurrentState().setStatus(State.PLAYING);
    }

    @Override
    public State createState(String name) {
        if (name.equals("Cat1")) {
            return new CatState(this.getWidth(), this.getHeight(), name);
        } else {
            return new RollingState(this.getWidth(), this.getHeight(), name);
        }
    }

}
