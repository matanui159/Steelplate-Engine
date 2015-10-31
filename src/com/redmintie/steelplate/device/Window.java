package com.redmintie.steelplate.device;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;

import com.redmintie.steelplate.core.Sound;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Font;
import com.redmintie.steelplate.render.Image;

public abstract class Window implements Device {
	private long time = -1;
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
	public double getDelta() {
		if (time == -1) {
			time = System.currentTimeMillis();
		}
		double delta =  (System.currentTimeMillis() - time) / 1000.0;
		time = System.currentTimeMillis();
		return delta;
	}
	public abstract int getType();
	public abstract void begin();
	public abstract void loop();
	public abstract void end();
	public abstract void setTitle(String title);
	public abstract void setIcon(BufferedImage icon);
	public abstract void setSize(int width, int height);
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract Keyboard getKeyboard();
	public abstract Image loadImage(BufferedImage image);
	public abstract Font loadFont(java.awt.Font font);
	public abstract Sound loadSound(AudioInputStream input) throws IOException;
}