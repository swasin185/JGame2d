package tom.game;

import java.net.URL;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MidiClip extends SoundClip {

	private Sequencer sequencer;

	public MidiClip(URL url) {
		Sequence seq;
		try {
			seq = MidiSystem.getSequence(url);
			sequencer = MidiSystem.getSequencer();
			if (!sequencer.isOpen())
				sequencer.open();
			sequencer.setSequence(seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MidiClip(String fileName) throws Exception {
		this(new URL("file:" + fileName));
	}

	public void loop() {
		sequencer.setLoopCount(-1);
		sequencer.start();
	}

	public void play() {
		sequencer.setTickPosition(0);
		sequencer.start();
	}

	public void stop() {
		sequencer.setLoopCount(0);
		sequencer.stop();
		sequencer.setTickPosition(0);

	}

	public void pause() {
		sequencer.stop();
	}

	@Override
	public void close() {
		this.stop();
		this.sequencer.close();
	}

}
