package com.redmintie.steelplate.entity.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Keyboard;
import com.redmintie.steelplate.input.event.KeyAdapter;
import com.redmintie.steelplate.input.event.KeyEvent;
import com.redmintie.steelplate.input.event.KeyListener;

public abstract class TextField extends Entity {
	private String text = "";
	private KeyListener listener;
	public TextField() {
		Keyboard.getKeyboard().addKeyListener(listener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKey() == Keyboard.KEY_V && e.isKeyDown(Keyboard.KEY_CONTROL)) {
					try {
						text += (String)Toolkit.getDefaultToolkit().getSystemClipboard()
								.getData(DataFlavor.stringFlavor);
					} catch (Exception ex) {}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getChar() == '\b') {
					if (!text.isEmpty()) {
						text = text.substring(0, text.length() - 1);
					}
				} else if (!Character.isIdentifierIgnorable(e.getChar())) {
					if (e.getChar() != '\n' && e.getChar() != '\r') {
						text += e.getChar();
					}
				}
			}
		});
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
	public void removeListener() {
		Keyboard.getKeyboard().removeKeyListener(listener);
	}
	public abstract void textEntered(String text);
}