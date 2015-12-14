package com.redmintie.steelplate.entity.ui;

import com.redmintie.steelplate.render.Canvas;

public abstract class Scene {
	View view;
	public View getView() {
		return view;
	}
	public abstract void init();
	public abstract void update(double delta);
	public abstract void draw(Canvas canvas);
	public abstract void end();
}