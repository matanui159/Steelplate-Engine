package com.redmintie.steelplate.entity.ui;

public abstract class SceneTransition extends Scene {
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
		last.view = null;
		view.setRawScene(next, true);
	}
}