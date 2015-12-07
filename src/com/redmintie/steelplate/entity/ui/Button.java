package com.redmintie.steelplate.entity.ui;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.input.Mouse;

public abstract class Button extends Entity {
	private boolean last;
	private boolean focus;
	private boolean state;
	@Override
	public void update(double delta) {
		Mouse mouse = Mouse.getMouse();
		boolean current = mouse.isButtonDown(Mouse.BUTTON1);
		boolean collide = testPoint(mouse.getX(), mouse.getY());
		
		if (!last && current) {
			focus = collide;
		}
		if (focus) {
			state = collide;
		}
		if (last && !current) {
			if (state) {
				buttonPressed();
			}
			state = focus = false;
		}
		last = current;
	}
	public boolean getButtonState() {
		return state;
	}
	public abstract void buttonPressed();
}