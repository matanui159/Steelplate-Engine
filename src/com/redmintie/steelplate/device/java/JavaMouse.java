package com.redmintie.steelplate.device.java;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

import com.redmintie.steelplate.input.Mouse;
import com.redmintie.steelplate.input.event.MouseEvent;
import com.redmintie.steelplate.input.event.MouseListener;
import com.redmintie.steelplate.input.event.MouseWheelEvent;

public class JavaMouse extends Mouse {
	private boolean[] buttons = new boolean[3];
	private int x;
	private int y;
	private double scroll;
	public JavaMouse(JPanel panel) {
		panel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				buttons[e.getButton() - 1] = true;
				MouseEvent event = createEvent(e, true);
				for (MouseListener listener : listeners) {
					listener.mouseButtonPressed(event);
				}
			}
			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				buttons[e.getButton() - 1] = false;
				MouseEvent event = createEvent(e, false);
				for (MouseListener listener : listeners) {
					listener.mouseButtonReleased(event);
				}
			}
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				MouseEvent event = createEvent(e, false);
				for (MouseListener listener : listeners) {
					listener.mouseButtonClicked(event);
				}
			}
		});
		panel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(java.awt.event.MouseEvent e) {
				x = e.getX();
				y = e.getY();
				MouseEvent event = createEvent(e, false);
				for (MouseListener listener : listeners) {
					listener.mouseMoved(event);
				}
			}
			@Override
			public void mouseDragged(java.awt.event.MouseEvent e) {
				x = e.getX();
				y = e.getY();
				MouseEvent event = createEvent(e, true);
				for (MouseListener listener : listeners) {
					listener.mouseDragged(event);
				}
			}
		});
		panel.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
				scroll += e.getPreciseWheelRotation();
				MouseWheelEvent event = new MouseWheelEvent(e.getPreciseWheelRotation(),
						e.getX(), e.getY(), JavaUtil.getEventMods(e));
				for (MouseListener listener : listeners) {
					listener.mouseScrolled(event);
				}
			}
		});
	}
	private MouseEvent createEvent(java.awt.event.MouseEvent e, boolean down) {
		return new MouseEvent(e.getButton(), down, e.getX(), e.getY(),
				JavaUtil.getEventMods(e));
	}
	@Override
	public boolean isButtonDown(int button) {
		return buttons[button - 1];
	}
	@Override
	public int getX() {
		return x;
	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public double getScrollAmount() {
		return scroll;
	}
	@Override
	public void resetScrollAmount() {
		scroll = 0;
	}
}