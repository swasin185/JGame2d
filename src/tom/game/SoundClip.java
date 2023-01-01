package tom.game;

public abstract class SoundClip {

	public abstract void play();

	public abstract void stop();

	public abstract void loop();

	public abstract void pause();

	public abstract void close();
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.close();
	}

}
