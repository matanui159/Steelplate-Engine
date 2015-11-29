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
import com.redmintie.steelplate.util.multithread.MultiThreadService;

public class JavaSound extends Sound {
	private static final int EVENT_PLAY = 0;
	private static final int EVENT_STOP = 1;
	
	private String name;
	private boolean stream;
	private AudioInputStream audio;
	private DataLine line;
	private AudioService service;
	private boolean stopped = true;
	
	@Override
	public void loadResource(String name, boolean stream) throws IOException {
		this.name = name;
		this.stream = stream;
		super.loadResource(name, stream);
		setVolume(volume);
	}
	@Override
	public void loadData(AudioInputStream audio, boolean stream) throws IOException {
		DataLine.Info info = new DataLine.Info(stream ? SourceDataLine.class : Clip.class, audio.getFormat());
		if (!AudioSystem.isLineSupported(info)) {
			throw new IOException("Line type not supported.");
		}
		
		try {
			if (line == null) {
				line = (DataLine)AudioSystem.getLine(info);
			}
			if (stream) {
				((SourceDataLine)line).open(audio.getFormat());
				this.audio = audio;
				if (service == null) {
					service = new AudioService();
					service.start();
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
	public void play() {
		if (stream) {
			service.callEvent(EVENT_PLAY);
		} else {
			stop();
			resume();
		}
	}
	@Override
	public void pause() {
		line.stop();
	}
	@Override
	public void resume() {
		line.start();
		stopped = false;
	}
	@Override
	public void stop() {
		if (stream) {
			service.callEvent(EVENT_STOP);
		} else {
			if (!stopped) {
				line.stop();
				line.flush();
				((Clip)line).setFramePosition(0);
			}
		}
		stopped = true;
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
		if (stream) {
			service.end(true);
		} else {
			line.close();
		}
	}
	
	private class AudioService extends MultiThreadService {
		private byte[] buffer = new byte[65536];
		private int length;
		private int offset;
		public AudioService() {
			super("Audio Service", null);
		}
		@Override
		public void update() throws IOException {
			if (offset == length) {
				offset = 0;
				length = audio.read(buffer);
				if (length == -1) {
					length = 0;
					if (line.available() == line.getBufferSize()) {
						stopAudio();
						if (loop) {
							line.start();
						}
					}
					return;
				}
			}
			int len = Math.min(length - offset, line.available());
			((SourceDataLine)line).write(buffer, offset, len);
			offset += len;
			
		}
		@Override
		public void eventCalled(int event) throws IOException {
			switch (event) {
			case MultiThreadService.EVENT_END:
				line.close();
				audio.close();
				break;
			case EVENT_PLAY:
				stopAudio();
				JavaSound.this.resume();
				break;
			case EVENT_STOP:
				stopAudio();
				break;
			}
		}
		public void stopAudio() throws IOException {
			if (!stopped) {
				line.stop();
				line.flush();
				audio.close();
				loadResource(name, stream);
			}
		}
	}
}