package com.redmintie.steelplate.input.event;

public class KeyEvent extends Event {
	private int key;
	private char c;
	private boolean down;
	public KeyEvent(int key, char c, boolean down, int mods) {
		super(mods);
		this.key = key;
		this.c = c;
		this.down = down;
	}
	public int getKey() {
		return key;
	}
	public char getChar() {
		return c;
	}
	@Override
	public boolean isKeyDown(int key) {
		if (down && key == this.key) {
			return true;
		} else {
			return super.isKeyDown(key);
		}
	}
}