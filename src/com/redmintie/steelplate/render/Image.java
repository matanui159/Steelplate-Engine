package com.redmintie.steelplate.render;

import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.redmintie.steelplate.core.Core;
import com.redmintie.steelplate.core.Game;

public abstract class Image {
	private static HashMap<String, Image> images = new HashMap<String, Image>();
	public static Image loadImage(String name) throws IOException {
		if (!images.containsKey(name)) {
			images.put(name, Game.getGameInstance().getWindow().loadImage(
					ImageIO.read(Core.getResourceAsStream(name))));
		}
		return images.get(name);
	}
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract Canvas getCanvas();
}