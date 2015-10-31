package com.redmintie.steelplate.input.event;

import com.redmintie.steelplate.input.Keyboard;

public class Event {
	public static final int CTRL_MASK = 1;
	public static final int ALT_MASK = 2;
	public static final int SHIFT_MASK = 3;
	private int mods;
	public Event(int mods) {
		this.mods = mods;
	}
	public int getModifiers() {
		return mods;
	}
	public boolean isKeyDown(int key) {
		switch (key) {
		case Keyboard.KEY_CONTROL:
			return (mods & CTRL_MASK) == CTRL_MASK;
		case Keyboard.KEY_ALT:
			return (mods & ALT_MASK) == ALT_MASK;
		case Keyboard.KEY_SHIFT:
			return (mods & SHIFT_MASK) == SHIFT_MASK;
		default:
			return false;
		}
	}
}