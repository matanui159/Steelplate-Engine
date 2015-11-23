package com.redmintie.steelplate.sound;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.core.DeviceException;
import com.redmintie.steelplate.core.Resource;
import com.redmintie.steelplate.device.Device;
import com.redmintie.steelplate.util.Map;

public abstract class Sound implements Device {
	private static final int RIFF = 0x52494646;
	private static final int WAVE = 0x57415645;
	
	private static Map<String, Sound> sounds = new Map<String, Sound>();
	public static Sound loadSound(String name) throws DeviceException, IOException {
		Sound sound = sounds.get(name);
		if (sound == null) {
			try {
				DataInputStream data = new DataInputStream(new BufferedInputStream(
						Resource.getResourceAsStream(name)));
				data.mark(12);
				boolean wav = false;
				if (data.readInt() == RIFF) {
					data.skip(4);
					if (data.readInt() == WAVE) {
						wav = true;
					}
				}
				data.reset();
				System.out.println("IS WAV: " + wav);
				
				sound = (Sound)Resource.loadDevice("com/redmintie/steelplate/res/devices/soundDevices.list");
				sound.loadData(AudioSystem.getAudioInputStream(data));
				sounds.set(name, sound);
			} catch (UnsupportedAudioFileException ex) {
				throw new IOException(ex);
			}
		}
		return sound;
	}
	private double volume;
	private boolean loop;
	protected abstract void loadData(AudioInputStream data) throws IOException;
	public abstract void play();
	public abstract void pause();
	public abstract void resume();
	public abstract void stop();
	public abstract boolean isPlaying();
	public void setVolume(double volume) {
		this.volume = Math.min(Math.max(volume, 0), 1);
	}
	public double getVolume() {
		return volume;
	}
	public void setLoopEnabled(boolean loop) {
		this.loop = loop;
	}
	public boolean isLoopEnabled() {
		return loop;
	}
}