package com.redmintie.steelplate.device.java;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redmintie.steelplate.render.Font;

public class JavaFont extends Font {
	private java.awt.Font font;
	private FontMetrics metrics;
	public JavaFont(java.awt.Font font) {
		this.font = font;
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.OPAQUE);
		Graphics g = image.getGraphics();
		g.setFont(font);
		metrics = g.getFontMetrics();
		g.dispose();
	}
	public java.awt.Font getFont() {
		return font;
	}
	public int getAscent() {
		return metrics.getAscent();
	}
	@Override
	public String getFamily() {
		return font.getFamily();
	}
	@Override
	public int getStyle() {
		return font.getStyle();
	}
	@Override
	public int getSize() {
		return font.getSize();
	}
	@Override
	public int getLineWidth(String line) {
		return metrics.stringWidth(line);
	}
	@Override
	public int getLineHeight() {
		return metrics.getHeight();
	}
}