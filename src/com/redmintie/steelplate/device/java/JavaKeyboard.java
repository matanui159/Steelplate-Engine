package com.redmintie.steelplate.device.java;

import javax.swing.JFrame;

import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.Event;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.input.event.KeyListener;

public class JavaKeyboard extends Keyboard {
	private boolean[] keys = new boolean[65535];
	public JavaKeyboard(JFrame frame) {
		frame.addKeyListener(new java.awt.event.KeyListener() {
			private KeyEvent createEvent(java.awt.event.KeyEvent e) {
				int mods = 0;
				if (e.isControlDown()) {
					mods |= Event.CTRL_MASK;
				}
				if (e.isAltDown()) {
					mods |= Event.ALT_MASK;
				}
				if (e.isShiftDown()) {
					mods |= Event.SHIFT_MASK;
				}
				return new KeyEvent(e.getKeyCode(), e.getKeyChar(),
						e.getID() == java.awt.event.KeyEvent.KEY_PRESSED, mods);
			}
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (!keys[e.getKeyCode()]) {
					keys[e.getKeyCode()] = true;
					for (KeyListener listener : listeners) {
						listener.keyPressed(createEvent(e));
					}
				}
			}
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				keys[e.getKeyCode()] = false;
				for (KeyListener listener : listeners) {
					listener.keyReleased(createEvent(e));
				}
			}
			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				for (KeyListener listener : listeners) {
					listener.keyTyped(createEvent(e));
				}
			}
		});
	}
	@Override
	public boolean isKeyDown(int key) {
		return keys[key];
	}
}