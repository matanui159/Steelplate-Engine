package com.redmintie.steelplate.device.java;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.render.Color;
import com.redmintie.steelplate.render.Font;
import com.redmintie.steelplate.render.Image;
import com.redmintie.steelplate.util.Array;
import com.redmintie.steelplate.util.Map;

public class JavaCanvas extends Canvas {
	private Graphics2D g;
	private AffineTransform reset = new AffineTransform();
	private Array<AffineTransform> stack = new Array<AffineTransform>();
	private int current = -1;
	private Map<Color, java.awt.Color> colors = new Map<Color, java.awt.Color>();
	private int ascent;
	public void setGraphics(Graphics g) {
		this.g = (Graphics2D)g;
		this.g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		setColor(color);
		setFont(font);
	}
	@Override
	public void setColor(Color color) {
		super.setColor(color);
		java.awt.Color c = colors.get(color);
		if (c == null) {
			c = new java.awt.Color(
					color.getRed(),
					color.getGreen(),
					color.getBlue(),
					color.getAlpha());
			colors.set(color, c);
		}
		g.setColor(c);
		g.setBackground(c);
	}
	@Override
	public void setAlpha(int alpha) {
		super.setAlpha(alpha);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
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
	public void drawImage(Image image, double sx, double sy, double sw, double sh,
			double dx, double dy, double dw, double dh) {
		g.drawImage(((JavaImage)image).getImage(),
				(int)dx, (int)dy, (int)(dx + dw), (int)(dy + dh),
				(int)sx, (int)sy, (int)(sx + sw), (int)(sy + sh), null);
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
		g.setTransform(reset);
	}
	@Override
	public void pushMatrix() {
		current++;
		if (current == stack.size()) {
			stack.add(new AffineTransform());
		}
		stack.get(current).setTransform(g.getTransform());
	}
	@Override
	public void popMatrix() {
		if (current >= 0) {
			g.setTransform(stack.get(current--));
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