package com.redmintie.steelplate.device.java;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.input.event.KeyListener;

public class JavaKeyboard extends Keyboard {
	private boolean[] keys = new boolean[65535];
	public JavaKeyboard(JFrame frame) {
		frame.addKeyListener(new java.awt.event.KeyListener() {
			private KeyEvent createEvent(java.awt.event.KeyEvent e) {
				return new KeyEvent(e.getKeyCode(), e.getKeyChar(),
						e.getID() == java.awt.event.KeyEvent.KEY_PRESSED,
						JavaUtil.getEventMods(e));
			}
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (!keys[e.getKeyCode()]) {
					keys[e.getKeyCode()] = true;
					KeyEvent event = createEvent(e);
					for (KeyListener listener : listeners) {
						listener.keyPressed(event);
					}
				}
			}
			@Override
			public void keyReleased(java.awt.event.KeyEvent e) {
				JavaKeyboard.this.keyReleased(createEvent(e));
			}
			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				KeyEvent event = createEvent(e);
				for (KeyListener listener : listeners) {
					listener.keyTyped(event);
				}
			}
		});
		
		frame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowLostFocus(WindowEvent e) {
				for (int i = 0; i < keys.length; i++) {
					if (keys[i]) {
						keyReleased(new KeyEvent(i, KeyEvent.CHAR_UNDEFINED, false, 0));
					}
				}
			}
		});
	}
	private void keyReleased(KeyEvent event) {
		keys[event.getKey()] = false;
		for (KeyListener listener : listeners) {
			listener.keyReleased(event);
		}
	}
	@Override
	public boolean isKeyDown(int key) {
		return keys[key];
	}
}