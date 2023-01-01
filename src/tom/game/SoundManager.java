package tom.game;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class SoundManager {
	public static SoundClip loadSound(String file) {
		URL url = null;
		try {
			url = new URL("file:" + file);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return SoundManager.loadSound(url);
	}

	public static SoundClip loadSound(URL url) {
		SoundClip clip = null;
		int extIndex = url.getFile().lastIndexOf(".");
		String ext = url.getFile().substring(extIndex);
		try {
			if (ext.equals(".mid")) {
				clip = new MidiClip(url);
			} else if (ext.equals(".au")) {
				clip = new AudioClip(url);
			} else if (ext.equals(".wav")) {
				clip = new AudioClip(url);
			} else if (ext.equals(".aiff")) {
				clip = new AudioClip(url);
			} else if (ext.equals(".mp3")) {
				clip = new Mp3Clip(url);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	private SoundClip[] clips;

	protected int current;

	public SoundManager(URL url, int count) {
		ignoreSprites = new LinkedList<Sprite>();
		clips = new SoundClip[count];
		for (int i = 0; i < clips.length; i++)
			clips[i] = SoundManager.loadSound(url);
		current = -1;
	}

	public void play() {
		current = (current + 1) % clips.length;
		clips[current].stop();
		clips[current].play();
	}

	public void play(Sprite sprite) {
		if (ignoreSprites.contains(sprite)) {
			ignoreSprites.remove(sprite);
		} else {
			this.play();
		}
	}

	protected LinkedList<Sprite> ignoreSprites;

	public void resetIgnore() {
		ignoreSprites.clear();
	}

	public void addIgnore(Sprite sprite) {
		ignoreSprites.add(sprite);
	}
}
