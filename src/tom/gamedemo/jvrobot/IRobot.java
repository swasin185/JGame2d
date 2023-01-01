package tom.gamedemo.jvrobot;

public interface IRobot {

	public static final byte NORTH = 0;
	public static final byte EAST = 1;
	public static final byte SOUTH = 2;
	public static final byte WEST = 3;

	public void forward();

	public void forward(int move);

	public void left();

	public void right();

	public void setEnergy(double energy);

	public double getEnergy();

	public void setSpeed(double speed);

	public double getSpeed();

	public void moveTo(int row, int column);
}
