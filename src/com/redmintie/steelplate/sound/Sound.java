package com.redmintie.steelplate.sound;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.core.DeviceException;
import com.redmintie.steelplate.core.Resource;
import com.redmintie.steelplate.device.Device;
import com.redmintie.steelplate.util.Map;

public abstract class Sound implements Device {
	private static final int OGG = 0x4f676753;
	
	private static Map<String, Sound> sounds = new Map<String, Sound>();
	public static Sound loadSound(String name, boolean stream) throws DeviceException, IOException {
		Sound sound = sounds.get(name + stream);
		if (sound == null) {
			sound = (Sound)Resource.loadDevice("com/redmintie/steelplate/res/devices/soundDevices.list");
			sound.loadResource(name, stream);
			sounds.set(name + stream, sound);
		}
		return sound;
	}
	public static Sound loadSound(String name) throws DeviceException, IOException {
		return loadSound(name, false);
	}
	public static void destroyAll() throws IOException {
		for (String name : sounds) {
			sounds.get(name).destroy();
		}
	}
	protected double volume = 1;
	protected boolean loop;
	protected void loadResource(String name, boolean stream) throws IOException {
		try {
			DataInputStream data = new DataInputStream(new BufferedInputStream(
					Resource.getResourceAsStream(name)));
			data.mark(4);
			boolean ogg = data.readInt() == OGG;
			data.reset();
		
			AudioInputStream audio = AudioSystem.getAudioInputStream(data);
			if (ogg) {
				AudioFormat format = audio.getFormat();
				format = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						format.getSampleRate(),
						16,
						format.getChannels(),
						format.getChannels() * 2,
						format.getSampleRate(),
						false);
				audio = AudioSystem.getAudioInputStream(format, audio);
			}
			
			loadData(audio, stream);
		} catch (UnsupportedAudioFileException ex) {
			throw new IOException(ex);
		}
	}
	protected abstract void loadData(AudioInputStream audio, boolean stream) throws IOException;
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
	public abstract void destroy() throws IOException;
}