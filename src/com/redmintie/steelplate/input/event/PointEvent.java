package com.redmintie.steelplate.input.event;

public class PointEvent extends Event {
	private int x;
	private int y;
	public PointEvent(int x, int y, int mods) {
		super(mods);
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}