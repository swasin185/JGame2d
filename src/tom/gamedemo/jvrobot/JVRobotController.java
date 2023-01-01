package tom.gamedemo.jvrobot;

import tom.game.GamePanel;
import tom.game.State;
import tom.game.StateController;

public class JVRobotController extends StateController {

	public static void main(final String[] args) {
		GamePanel game = new GamePanel(20, new JVRobotController());
		game.run("JVRobot");
	}

	public JVRobotController() {
		super("Field", 640, 640);
	}

	@Override
	public void changeState() {

	}

	@Override
	public State createState(String name) {
		return new FieldState(this.getWidth(), this.getHeight(), name);
	}

}
