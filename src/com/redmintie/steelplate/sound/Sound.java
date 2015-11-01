package com.redmintie.steelplate.sound;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.core.Resource;

public abstract class Sound {
	private static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	public static Sound loadSound(String name) throws IOException, UnsupportedAudioFileException {
		if (!sounds.containsKey(name)) {
			sounds.put(name, Game.getGameInstance().getWindow().loadSound(
					AudioSystem.getAudioInputStream(new BufferedInputStream(Resource.getResourceAsStream(name)))));
		}
		return sounds.get(name);
	}
	private double volume;
	private boolean loop;
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