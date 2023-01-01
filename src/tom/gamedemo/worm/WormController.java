package tom.gamedemo.worm;

import tom.game.GamePanel;
import tom.game.GameScreen;
import tom.game.State;
import tom.game.StateController;

public class WormController extends StateController {

    public static void main(final String[] args) {
        // SplashScreen scr = new SplashScreen(WormGame.class
        // .getResource("MoonDance.gif"), 350, 250);
        GamePanel game = new GamePanel(30, new WormController());
        game.run("Worm");
        // scr.dispose();

    }

    public WormController() {
        super("State1", 800, 600);
    }

    public State createState(String name) {
        WormState state = new WormState(this.getWidth(), this.getHeight(), name);
        if (name.equals("State1")) {
            state.setLimitedTime(60);
            state.setFoods(1);
        } else if (name.equals("State2")) {
            state.setLimitedTime(60);
            state.setFoods(3);
        } else if (name.equals("State3")) {
            state.setLimitedTime(120);
            state.setFoods(3);
            state.setNumberOfEnemy(1);
        }
        stateMap.put(name, state);
        return state;
    }

    @Override
    public void changeState() {
        State state = this.getCurrentState();
        if (state.getName().equals("State1") && state.isCleared()) {
            setState("State2");
        } else if (state.getName().equals("State2") && state.isCleared()) {
            setState("State3");
        } else {
            state.setStatus(State.PLAYING);
        }
    }
}
