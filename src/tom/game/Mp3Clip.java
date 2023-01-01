package tom.game;

import java.net.URL;

import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;

public class Mp3Clip extends SoundClip implements Runnable {

	private Player player;

	private Player loop;

	private int duration;

	private boolean played;

	public Mp3Clip(URL url) throws Exception {
		player = Manager.createRealizedPlayer(url);
		player.prefetch();
		loop = Manager.createRealizedPlayer(url);
		this.duration = (int) player.getDuration().getSeconds();
	}

	public Mp3Clip(String fileName) throws Exception {
		this(new URL("file:" + fileName));
	}

	public void loop() {
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	public void play() {
		played = true;
		player.start();
	}

	public void stop() {
		played = false;
		player.stop();
		player.setMediaTime(new Time(0));
		player.prefetch();
	}

	public void run() {
		int i = 0;
		this.play();
		while (played) {
			i = (int) player.getMediaTime().getSeconds();
			if (i >= duration) {
				Player temp = player;
				player = loop;
				loop = temp;
				player.start();

			} else if (i > duration - 10) {
				loop.stop();
				loop.setMediaTime(new Time(0));
				loop.prefetch();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void pause() {
		played = false;
		player.stop();
	}

	@Override
	public void close() {
		this.stop();
		player.close();
	}

}
