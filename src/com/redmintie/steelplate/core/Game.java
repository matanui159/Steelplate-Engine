package com.redmintie.steelplate.core;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.redmintie.steelplate.device.Window;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Image;
import com.redmintie.steelplate.sound.Sound;

/**
 * The core class for Steelplate Engine. Override this to create your game.
 * There can only be one Game instance at a time.
 */
public abstract class Game {
	/**
	 * Defines that the platform is unknown.
	 */
	public static final int TYPE_OTHER = 0;
	
	/**
	 * Defines that the platform is on the desktop.
	 * In other words, it's a stand alone program being run within the OS.
	 */
	public static final int TYPE_DESKTOP = 1;
	
	/**
	 * Defines that the platform is on a mobile device.
	 */
	public static final int TYPE_MOBILE = 2;
	
	/**
	 * Defines that the platform is in the web.
	 */
	public static final int TYPE_WEB = 3;
	
	private static Game instance;
	
	/**
	 * Gets the current game instance.
	 * 
	 * @return The current game instance.
	 * @throws RuntimeException If there is no current game instance.
	 */
	public static Game getGameInstance() {
		if (instance == null) {
			throw new RuntimeException("There is no current game instance.");
		}
		return instance;
	}
	
	private Window window;
	private String title;
	private boolean fullscreen;
	private boolean keep;
	
	/**
	 * Creates a new game.
	 * 
	 * @throws RuntimeException If there is already a game instance.
	 */
	public Game() throws DeviceException {
		if (instance != null) {
			throw new RuntimeException("There can only be one game instance.");
		}
		instance = this;
		
		window = (Window)Resource.loadDevice("com/redmintie/steelplate/res/devices/windowDevices.list");
		window.setTitle("Steelplate Game");
		window.setSize(1024, 512);
	}
	
	/**
	 * Gets the window of the game instance.
	 * The 'Window' is the main graphical device in charge of controlling how the window acts
	 * and providing the canvas along with loading images and sounds.
	 * 
	 * @return The window.
	 */
	public Window getWindow() {
		return window;
	}
	
	/**
	 * Gets the platform type.
	 * Can be either {@link #TYPE_OTHER}, {@link #TYPE_DESKTOP}, {@link #TYPE_MOBILE} or {@link #TYPE_WEB}.
	 * 
	 * @return The platform type.
	 */
	public int getType() {
		return window.getType();
	}
	
	/**
	 * Makes the window visible and starts the update/render loop.
	 */
	public void start() {
		window.start();
		long start = System.nanoTime();
		init();
		long time = (System.nanoTime() - start) / (int)1e6;
		if (time < 2000) {
			try {
				Thread.sleep(2000 - time);
			} catch (InterruptedException ex) {}
		}
		window.loop();
	}
	
	/**
	 * This abstract method is called when the window is first shown.
	 */
	public abstract void init();
	
	/**
	 * This abstract method is called every frame for updating.
	 * 
	 * @param delta The amount of time since the last update call, in seconds.
	 */
	public abstract void update(double delta);
	
	/**
	 * This abstract method is called every frame for rendering.
	 * 
	 * @param canvas The canvas to draw on.
	 */
	public abstract void draw(Canvas canvas);
	
	/**
	 * This abstract method is called when the close button on the window is pressed.
	 * Usually, this just calls {@link #end()}.
	 */
	public abstract void close();
	
	/**
	 * Ends the game, closing the window and stopping the loop.
	 * After ending a game, another game can be made to become the new game instance.
	 */
	public void end() {
		Image.destroyAll();
		try {
			Sound.destroyAll();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		window.end();
		instance = null;
	}
	
	/**
	 * Sets the title of the window.
	 * 
	 * @param title The title of the window.
	 */
	public void setTitle(String title) {
		window.setTitle(this.title = title);
	}
	
	/**
	 * Gets the title of the window.
	 * 
	 * @return The title of the window.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the icon of the window.
	 * This uses {@link Resource#getResourceAsStream(String)} so it will first look inside the jar, then outside.
	 * 
	 * @param icon The path to the icon.
	 * @throws IOException If the file can not be read.
	 */
	public void setIcon(String icon) throws IOException {
		window.setIcon(ImageIO.read(Resource.getResourceAsStream(icon)));
	}
	
	/**
	 * Sets the size of the window.
	 * 
	 * @param width The width of the window.
	 * @param height The height of the window.
	 */
	public void setSize(int width, int height) {
		window.setSize(width, height);
	}
	
	/**
	 * Gets the width of the window.
	 * The width can change since the last {@link #setSize(int, int)} call,
	 * due to how the user can resize the window.
	 * 
	 * @return The width of the window.
	 */
	public int getWidth() {
		return window.getWidth();
	}
	
	/**
	 * Gets the height of the window.
	 * The width can change since the last {@link #setSize(int, int)} call,
	 * due to how the user can resize the window.
	 * 
	 * @return The height of the window.
	 */
	public int getHeight() {
		return window.getHeight();
	}
	
	public void setMaximized(boolean max) {
		window.setMaximized(max);
	}
	
	public boolean isMaximized() {
		return window.isMaximized();
	}
	
	public void setFullscreen(boolean fullscreen) {
		window.setFullscreen(this.fullscreen = fullscreen);
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	/**
	 * Sets whether or not to keep the background.
	 * When the background is kept, draws from the previous frame are kept,
	 * allowing you to draw over them.
	 * 
	 * @param keep whether or not to keep the background.
	 */
	public void keepBackground(boolean keep) {
		window.keepBackground(this.keep = keep);
	}
	
	/**
	 * Gets whether or not the background is kept.
	 * When the background is kept, draws from the previous frame are kept,
	 * allowing you to draw over them.
	 * 
	 * @return Whether or not the background is kept.
	 */
	public boolean isBackgroundKept() {
		return keep;
	}
}