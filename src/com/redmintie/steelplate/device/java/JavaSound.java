package com.redmintie.steelplate.device.java;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

import com.redmintie.steelplate.sound.Sound;

public class JavaSound extends Sound {
	private Clip clip;
	@Override
	public void loadData(AudioInputStream data) throws IOException {
		DataLine.Info info = new DataLine.Info(Clip.class, data.getFormat());
		if (!AudioSystem.isLineSupported(info)) {
			throw new IOException("Line type not supported.");
		}
		try {
			clip = (Clip)AudioSystem.getLine(info);
			clip.open(data);
		} catch (LineUnavailableException ex) {
			throw new IOException(ex);
		}
	}
	@Override
	public void play() {
		stop();
		clip.start();
	}
	@Override
	public void pause() {
		clip.stop();
	}
	@Override
	public void resume() {
		clip.start();
	}
	@Override
	public void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
		clip.flush();
		clip.setFramePosition(0);
	}
	@Override
	public boolean isPlaying() {
		return clip.isRunning();
	}
	@Override
	public void setVolume(double volume) {
		super.setVolume(volume);
		((FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN))
				.setValue(20 * (float)Math.log10(Math.min(Math.max(volume, 0.0001), 1)));
	}
	@Override
	public void setLoopEnabled(boolean loop) {
		super.setLoopEnabled(loop);
		clip.loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
	}
}