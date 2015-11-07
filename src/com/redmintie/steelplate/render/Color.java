package com.redmintie.steelplate.render;

public class Color {
	// GENERATED CODE BEGINS HERE
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color LIGHT_GRAY = new Color(192, 192, 192);
	public static final Color GRAY = new Color(128, 128, 128);
	public static final Color DARK_GRAY = new Color(64, 64, 64);
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color PINK = new Color(255, 175, 175);
	public static final Color ORANGE = new Color(255, 200, 0);
	public static final Color YELLOW = new Color(255, 255, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color MAGENTA = new Color(255, 0, 255);
	public static final Color CYAN = new Color(0, 255, 255);
	public static final Color BLUE = new Color(0, 0, 255);
	// GENERATED CODE ENDS HERE
	
	private int red;
	private int green;
	private int blue;
	private int alpha;
	public Color(int red, int green, int blue, int alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = 255;
	}
	@Override
	public String toString() {
		return "RGBA(" + getRed() + ", " + getGreen() + ", " + getBlue() + ", " + getAlpha() + ")";
	}
	@Override
	public int hashCode() {
		return red << 24 | green << 16 | blue << 8 | alpha;
	}
	@Override
	public boolean equals(Object other) {
		if (other instanceof Color) {
			return hashCode() == other.hashCode();
		}
		return false;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getRed() {
		return red;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getGreen() {
		return green;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
	public int getBlue() {
		return blue;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int getAlpha() {
		return alpha;
	}
}