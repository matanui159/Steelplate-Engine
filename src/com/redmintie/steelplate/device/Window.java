package com.redmintie.steelplate.device;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.Mouse;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Font;
import com.redmintie.steelplate.render.Image;

/**
 * A window is a device for managing the window and creating canvas's, fonts and images
 * and accessing the keyboard and mouse.
 * This will not be documented as it is part of the internal library
 * and is not designed for public use.
 */
public abstract class Window implements Device {
	private long time = -1;
	private Image background;
	public void drawLogo(Canvas canvas) {
		canvas.setColor(236, 240, 241);
		canvas.clear();
		
		try {
			Image logo = Image.loadImage("com/redmintie/steelplate/res/logo/logo.png");
			Image credit = Image.loadImage("com/redmintie/steelplate/res/logo/credit.png");
			canvas.drawImage(logo,
					getWidth() / 2 - logo.getWidth() / 2,
					getHeight() / 2 - logo.getHeight() / 2);
			canvas.drawImage(credit, 10, getHeight() - credit.getHeight() - 10);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	public abstract int getType();
	public abstract void begin();
	public abstract void loop();
	public double getDelta() {
		if (time == -1) {
			time = System.nanoTime();
		}
		double delta = (System.nanoTime() - time) / 1e9;
		time = System.nanoTime();
		return delta;
	}
	public void draw(Canvas canvas) {
		if (background == null) {
			Game.getGameInstance().draw(canvas);
		} else {
			Game.getGameInstance().draw(background.getCanvas());
			canvas.drawImage(background);
		}
	}
	public abstract void end();
	public abstract void setTitle(String title);
	public abstract void setIcon(BufferedImage icon);
	public abstract void setSize(int width, int height);
	public abstract int getWidth();
	public abstract int getHeight();
	public void keepBackground(boolean keep) {
		if (background != null) {
			background.destroy();
		}
		background = keep ? createImage(getWidth(), getHeight()) : null;
	}
	public abstract Keyboard getKeyboard();
	public abstract Mouse getMouse();
	public abstract Image loadImage(BufferedImage image);
	public abstract Image createImage(int width, int height);
	public abstract Font loadFont(java.awt.Font font);
}