package com.redmintie.steelplate.device.java;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.redmintie.steelplate.sound.Sound;

public class JavaSound extends Sound implements Runnable {
	private String name;
	private boolean stream;
	private AudioInputStream audio;
	private DataLine line;
	private Thread thread;
	@Override
	public void loadResource(String name, boolean stream) throws IOException {
		this.name = name;
		this.stream = stream;
		super.loadResource(name, stream);
		setVolume(volume);
	}
	@Override
	public void loadData(AudioInputStream audio, boolean stream) throws IOException {
		this.stream = stream;
		DataLine.Info info = new DataLine.Info(stream ? SourceDataLine.class : Clip.class, audio.getFormat());
		if (!AudioSystem.isLineSupported(info)) {
			throw new IOException("Line type not supported.");
		}
		
		try {
			line = (DataLine)AudioSystem.getLine(info);
			if (stream) {
				((SourceDataLine)line).open(audio.getFormat());
				this.audio = audio;
				if (thread == null) {
					thread = new Thread(this, "Audio Thread");
					thread.start();
				}
			} else {
				((Clip)line).open(audio);
				audio.close();
			}
		} catch (LineUnavailableException ex) {
			throw new IOException(ex);
		}
	}
	@Override
	public void run() {
		try {
			byte[] buffer = new byte[65536];
			int length;
			while (thread != null) {
				while (thread != null && (length = audio.read(buffer)) != -1) {
					((SourceDataLine)line).write(buffer, 0, length);
				}
				line.drain();
				if (line.isRunning()) {
					stop();
					if (loop) {
						line.start();
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	@Override
	public void play() {
		stop();
		line.start();
	}
	@Override
	public void pause() {
		line.stop();
	}
	@Override
	public void resume() {
		line.start();
	}
	@Override
	public void stop() {
		if (line.isRunning()) {
			line.stop();
		}
		line.flush();
		if (stream) {
			try {
				AudioInputStream old = audio;
				loadResource(name, stream);
				old.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			((Clip)line).setFramePosition(0);
		}
	}
	@Override
	public boolean isPlaying() {
		return line.isRunning();
	}
	@Override
	public void setVolume(double volume) {
		super.setVolume(volume);
		((FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN))
				.setValue(20 * (float)Math.log10(Math.min(Math.max(volume, 0.0001), 1)));
	}
	@Override
	public void setLoopEnabled(boolean loop) {
		super.setLoopEnabled(loop);
		if (!stream) {
			((Clip)line).loop(loop ? Clip.LOOP_CONTINUOUSLY : 0);
		}
	}
	@Override
	public void destroy() throws IOException {
		stop();
		if (stream) {
			audio.close();
			thread = null;
		}
		line.close();
	}
}