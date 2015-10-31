package com.redmintie.steelplate.device.java;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Image;

public class JavaImage extends Image {
	private BufferedImage image;
	public JavaImage(BufferedImage image) {
		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration();
		if (config.getColorModel().equals(image.getColorModel())) {
			this.image = image;
		} else {
			this.image = config.createCompatibleImage(image.getWidth(), image.getHeight(),
					Transparency.TRANSLUCENT);
			Graphics g = this.image.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.dispose();
		}
		this.image.setAccelerationPriority(0.9f);
	}
	public BufferedImage getImage() {
		return image;
	}
	@Override
	public int getWidth() {
		return image.getWidth();
	}
	@Override
	public int getHeight() {
		return image.getHeight();
	}
	@Override
	public Canvas getCanvas() {
		return new JavaCanvas(image.getGraphics());
	}
	@Override
	public void destroy() {
	}
}