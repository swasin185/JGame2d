package tom.game;

import java.net.URL;

import javax.sound.sampled.*;

public class AudioClip extends SoundClip {
	private Clip clip;

	public AudioClip(URL url) {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);

			AudioFormat format = stream.getFormat();
			if ((format.getEncoding() == AudioFormat.Encoding.ULAW)
					|| (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
				AudioFormat newFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						format.getSampleSizeInBits() * 2, format.getChannels(),
						format.getFrameSize() * 2, format.getFrameRate(), true);
				stream = AudioSystem.getAudioInputStream(newFormat, stream);
				// System.out.println("Converted Audio format: " + newFormat);
				format = newFormat;
			}

			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			stream.close();
		} catch (Exception e) {

		}
	}

	public AudioClip(String fileName) throws Exception {
		this(new URL("file:" + fileName));
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void play() {
		clip.start();
	}

	public void stop() {
		clip.stop();
		clip.setFramePosition(0);
	}

	public void pause() {
		clip.stop();
	}

	@Override
	public void close() {
		this.stop();
		clip.close();
	}

}
