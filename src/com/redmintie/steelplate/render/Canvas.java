package com.redmintie.steelplate.render;

public abstract class Canvas {
	protected Color color = new Color(255, 255, 255, 255);
	protected int alpha;
	protected Font font = Font.loadFont("Arial", Font.PLAIN, 16);
	public void setColor(Color color) {
		this.color = color;
	}
	public void setColor(int red, int green, int blue, int alpha) {
		setColor(new Color(red, green, blue, alpha));
	}
	public void setColor(int red, int green, int blue) {
		setColor(new Color(red, green, blue));
	}
	public Color getColor() {
		return color;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int getAlpha() {
		return alpha;
	}
	public abstract void clear();
	public abstract void drawImage(Image image, double sx, double sy, double sw, double sh,
			double dx, double dy, double dw, double dh);
	public void drawImage(Image image, double x, double y, double width, double height) {
		drawImage(image, 0, 0, image.getWidth(), image.getHeight(), x, y, width, height);
	}
	public void drawImage(Image image, double x, double y) {
		drawImage(image, 0, 0, image.getWidth(), image.getHeight(), x, y, image.getWidth(), image.getHeight());
	}
	public void drawImage(Image image) {
		drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, 0, image.getWidth(), image.getHeight());
	}
	public void setFont(Font font) {
		this.font = font;
	}
	public Font getFont() {
		return font;
	}
	protected abstract void drawLine(String line, double x, double y);
	public void drawText(String text, double x, double y) {
		String[] lines = text.replace("\t", "    ").split("\n");
		for (String line : lines) {
			drawLine(line, x, y);
			y += font.getLineHeight();
		}
	}
	public abstract void resetMatrix();
	public abstract void pushMatrix();
	public abstract void popMatrix();
	public abstract void translate(double x, double y);
	public abstract void rotate(double angle);
	public abstract void scale(double x, double y);
	public abstract void destroy();
}