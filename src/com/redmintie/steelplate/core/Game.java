package com.redmintie.steelplate.core;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.redmintie.steelplate.device.Window;
import com.redmintie.steelplate.render.Canvas;

public abstract class Game {
	public static final int TYPE_OTHER = 0;
	public static final int TYPE_DESKTOP = 1;
	public static final int TYPE_MOBILE = 2;
	public static final int TYPE_WEB = 3;
	private static Game instance;
	public static Game getGameInstance() {
		if (instance == null) {
			throw new RuntimeException("There is no current game instance.");
		}
		return instance;
	}
	private Window window;
	private String title;
	public Game() {
		if (instance != null) {
			throw new RuntimeException("There can only be one game instance.");
		}
		
		window = (Window)Core.loadDevice(this, "com/redmintie/steelplate/res/devices/windowDevices.list");
		window.setTitle("Steelplate Game");
		window.setSize(1024, 512);
		instance = this;
	}
	public Window getWindow() {
		return window;
	}
	public int getType() {
		return window.getType();
	}
	public void begin() {
		window.begin();
		init();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ex) {}
		window.loop();
	}
	public abstract void init();
	public abstract void update(double delta);
	public abstract void draw(Canvas canvas);
	public abstract void close();
	public void end() {
		window.end();
		instance = null;
	}
	public void setTitle(String title) {
		window.setTitle(this.title = title);
	}
	public String getTitle() {
		return title;
	}
	public void setIcon(String icon) throws IOException {
		window.setIcon(ImageIO.read(Core.getResourceAsStream(icon)));
	}
	public void setSize(int width, int height) {
		window.setSize(width, height);
	}
	public int getWidth() {
		return window.getWidth();
	}
	public int getHeight() {
		return window.getHeight();
	}
}