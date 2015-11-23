package com.redmintie.steelplate.sound;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.core.Resource;
import com.redmintie.steelplate.device.Device;
import com.redmintie.steelplate.util.Map;

public abstract class Sound implements Device {
	private static Map<String, Sound> sounds = new Map<String, Sound>();
	public static Sound loadSound(String name) throws IOException {
		Sound sound = sounds.get(name);
		if (sound == null) {
			try {
				sound = (Sound)Resource.loadDevice("com/redmintie/steelplate/res/devices/soundDevices.list");
				sound.loadData(AudioSystem.getAudioInputStream(new BufferedInputStream(
						Resource.getResourceAsStream(name))));
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