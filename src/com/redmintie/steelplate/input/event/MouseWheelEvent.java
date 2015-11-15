package com.redmintie.steelplate.input.event;

public class MouseWheelEvent extends PointEvent {
	private double scroll;
	public MouseWheelEvent(double scroll, int x, int y, int mods) {
		super(x, y, mods);
		this.scroll = scroll;
	}
	public double getScrollAmount() {
		return scroll;
	}
}