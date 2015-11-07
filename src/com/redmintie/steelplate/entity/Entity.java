package com.redmintie.steelplate.entity;

import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Array;
import com.redmintie.steelplate.util.Point;

public class Entity {
	public Point position = new Point();
	public double angle;
	public int width;
	public int height;
	private Array<Entity> children = new Array<Entity>();
	public boolean testOverlap(Entity other) {
		return position.x - width / 2 < other.position.x + other.width / 2
				&& position.x + width / 2 > other.position.x - other.width / 2
				&& position.y - height / 2 < other.position.y + other.height / 2
				&& position.y + height / 2 > other.position.y - other.height / 2;
	}
	public void update(double delta) {
		for (Entity child : children) {
			child.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		canvas.translate(position.x, position.y);
		canvas.rotate(angle);
		canvas.translate(-width / 2, -height / 2);
		for (Entity child : children) {
			canvas.pushMatrix();
			child.draw(canvas);
			canvas.popMatrix();
		}
	}
	public void end() {
		for (Entity child : children) {
			child.end();
		}
	}
	public void addChild(Entity node) {
		children.add(node);
	}
	public int getChildCount() {
		return children.size();
	}
	public Entity getChild(int i) {
		return children.get(i);
	}
	public void removeChild(Entity node) {
		children.remove(node);
	}
	public void clearChildren() {
		children.clear();
	}
}