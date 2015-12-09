package com.redmintie.steelplate.entity.ui;

import com.redmintie.steelplate.render.Canvas;

public class View {
	Scene scene;
	private SceneTransition transition;
	public void setScene(Scene scene) {
		if (transition == null) {
			if (this.scene != null) {
				this.scene.end();
			}
			this.scene = scene;
			if (scene != null) {
				scene.init();
			}
		} else {
			transition.last = this.scene;
			transition.next = scene;
			this.scene = transition;
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
			scene.update(this, delta);
		}
	}
	public void draw(Canvas canvas) {
		if (scene != null) {
			scene.draw(canvas);
		}
	}
}