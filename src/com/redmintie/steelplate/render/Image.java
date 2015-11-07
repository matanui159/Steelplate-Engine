package com.redmintie.steelplate.render;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.core.Resource;
import com.redmintie.steelplate.util.Map;

public abstract class Image {
	private static Map<String, Image> images = new Map<String, Image>();
	public static Image loadImage(String name) throws IOException {
		Image image = images.get(name);
		if (image == null) {
			image = Game.getGameInstance().getWindow().loadImage(
					ImageIO.read(Resource.getResourceAsStream(name)));
			images.set(name, image);
		}
		return image;
	}
	public static Image createImage(int width, int height) throws IOException {
		return Game.getGameInstance().getWindow().createImage(width, height);
	}
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract Canvas getCanvas();
	public abstract void destroy();
}