package com.redmintie.steelplate.device.java;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Font;
import com.redmintie.steelplate.render.Image;

public class JavaCanvas extends Canvas {
	private ArrayList<AffineTransform> stack = new ArrayList<AffineTransform>();
	private int current = 0;
	private Graphics2D g;
	private int ascent;
	public JavaCanvas(Graphics g) {
		this.g = (Graphics2D)g;
		this.g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		setFont(font);
		setColor(color);
		stack.add(this.g.getTransform());
	}
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		java.awt.Color c = new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		g.setColor(c);
		g.setBackground(c);
	}
	@Override
	public void clear() {
		Game game = Game.getGameInstance();
		if (game.isBackgroundKept()) {
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
		} else {
			g.clearRect(0, 0, game.getWidth(), game.getHeight());
		}
	}
	@Override
	public void drawImage(Image image, double x, double y, double width, double height) {
		g.drawImage(((JavaImage)image).getImage(), (int)x, (int)y, (int)width, (int)height, null);
	}
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		JavaFont jf = (JavaFont)font;
		g.setFont(jf.getFont());
		ascent = jf.getAscent();
	}
	@Override
	public void drawLine(String line, double x, double y) {
		g.drawString(line, (int)x, (int)y + ascent);
	}
	@Override
	public void resetMatrix() {
		stack.get(current).setToIdentity();
	}
	@Override
	public void pushMatrix() {
		if (current + 1 == stack.size()) {
			stack.add(new AffineTransform());
		}
		stack.get(current + 1).setTransform(stack.get(current));
		g.setTransform(stack.get(++current));
	}
	@Override
	public void popMatrix() {
		if (current > 0) {
			g.setTransform(stack.get(--current));
		}
	}
	@Override
	public void translate(double x, double y) {
		g.translate(x, y);
	}
	@Override
	public void rotate(double angle) {
		g.rotate(Math.toRadians(angle));
	}
	@Override
	public void scale(double x, double y) {
		g.scale(x, y);
	}
	@Override
	public void destroy() {
		g.dispose();
	}
}