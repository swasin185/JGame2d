package tom.game.test;

import java.net.URL;

import javax.swing.JOptionPane;

import tom.game.SoundClip;
import tom.game.SoundManager;

public class SoundTest {
	public static void main(String[] args) {
		URL url = null;
		url = ClassLoader.getSystemResource("tom/gamedemo/worm/alla.mid");
		SoundClip sound1 = SoundManager.loadSound(url);
		url = ClassLoader.getSystemResource("tom/gamedemo/worm/1up.mid");
		SoundClip sound2 = SoundManager.loadSound(url);
		sound1.loop();
		JOptionPane.showMessageDialog(null, "Play Midi");
		sound2.play();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sound1.close();
		sound2.close();
	}
}
