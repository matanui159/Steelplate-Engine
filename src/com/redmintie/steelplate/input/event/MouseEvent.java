package com.redmintie.steelplate.input.event;

public class MouseEvent extends PointEvent {
	private int button;
	private boolean down;
	public MouseEvent(int button, boolean down, int x, int y, int mods) {
		super(x, y, mods);
		this.button = button;
		this.down = down;
	}
	public int getMouseButton() {
		return button;
	}
	public boolean isButtonDown() {
		return down;
	}
	public boolean isButtonDown(int button) {
		return down && button == this.button;
	}
}