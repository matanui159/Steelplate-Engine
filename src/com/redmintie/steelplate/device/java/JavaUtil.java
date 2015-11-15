package com.redmintie.steelplate.device.java;

import java.awt.event.InputEvent;

import com.redmintie.steelplate.input.event.Event;

public class JavaUtil {
	public static int getEventMods(InputEvent event) {
		int mods = 0;
		if (event.isControlDown()) {
			mods |= Event.CTRL_MASK;
		}
		if (event.isAltDown()) {
			mods |= Event.ALT_MASK;
		}
		if (event.isShiftDown()) {
			mods |= Event.SHIFT_MASK;
		}
		return mods;
	}
}