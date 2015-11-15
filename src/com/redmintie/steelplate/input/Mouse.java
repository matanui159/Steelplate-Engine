package com.redmintie.steelplate.input;

import com.redmintie.steelplate.core.Game;
import com.redmintie.steelplate.input.event.MouseListener;
import com.redmintie.steelplate.util.array.MappedArray;

public abstract class Mouse {
	public static final int BUTTON1 = 1;
	public static final int BUTTON2 = 2;
	public static final int BUTTON3 = 3;
	
	private static Mouse mouse;
	public static Mouse getMouse() {
		if (mouse == null) {
			mouse = Game.getGameInstance().getWindow().getMouse();
		}
		return mouse;
	}
	protected MappedArray<MouseListener> listeners = new MappedArray<MouseListener>();
	public abstract boolean isButtonDown(int button);
	public abstract int getX();
	public abstract int getY();
	public abstract double getScrollAmount();
	public abstract void resetScrollAmount();
	public void addMouseListener(MouseListener listener) {
		listeners.add(listener);
	}
	public void removeMouseListener(MouseListener listener) {
		listeners.remove(listener);
	}
	public void clearMouseListeners() {
		listeners.clear();
	}
}