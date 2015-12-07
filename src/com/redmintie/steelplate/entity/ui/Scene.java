package com.redmintie.steelplate.entity.ui;

import com.redmintie.steelplate.render.Canvas;

public interface Scene {
	public void start();
	public void update(View view, double delta);
	public void draw(Canvas canvas);
	public void end();
}