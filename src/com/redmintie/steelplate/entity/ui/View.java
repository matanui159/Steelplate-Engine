package com.redmintie.steelplate.entity.ui;

import com.redmintie.steelplate.render.Canvas;

public class View {
	Scene scene;
	private SceneTransition transition;
	public void setScene(Scene scene) {
		if (transition == null) {
			setRawScene(scene, true);
		} else {
			transition.last = this.scene;
			transition.next = scene;
			setRawScene(transition, false);
		}
	}
	void setRawScene(Scene scene, boolean end) {
		if (end && this.scene != null) {
			this.scene.end();
			this.scene.view = null;
		}
		this.scene = scene;
		if (scene != null) {
			scene.view = this;
			scene.init();
		}
	}
	public Scene getScene() {
		if (scene instanceof SceneTransition) {
			return ((SceneTransition)scene).next;
		} else {
			return scene;
		}
	}
	public void setTransition(SceneTransition transition) {
		if (this.transition != null) {
			this.transition.view = null;
		}
		this.transition = transition;
		if (transition != null) {
			transition.view = this;
		}
	}
	public SceneTransition getTransition() {
		return transition;
	}
	public void update(double delta) {
		if (scene != null) {
			scene.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		if (scene != null) {
			scene.draw(canvas);
		}
	}
}