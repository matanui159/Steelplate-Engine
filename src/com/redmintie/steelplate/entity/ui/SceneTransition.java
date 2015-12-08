package com.redmintie.steelplate.entity.ui;

public abstract class SceneTransition implements Scene {
	View view;
	Scene last;
	Scene next;
	public Scene getLastScene(Scene scene) {
		return last;
	}
	public Scene getNextScene(Scene scene) {
		return next;
	}
	public void finish() {
		last.end();
		view.scene = next;
		next.init();
	}
}