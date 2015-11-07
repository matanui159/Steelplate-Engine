package com.redmintie.steelplate.entity;

import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Array;
import com.redmintie.steelplate.util.math.Point;

public class Entity {
	public Point position = new Point();
	public double angle;
	public int width;
	public int height;
	private Array<Entity> children = new Array<Entity>();
	public boolean testOverlap(Entity other) {
		return position.getX() - width / 2 < other.position.getX() + other.width / 2
				&& position.getX() + width / 2 > other.position.getX() - other.width / 2
				&& position.getY() - height / 2 < other.position.getY() + other.height / 2
				&& position.getY() + height / 2 > other.position.getY() - other.height / 2;
	}
	public void update(double delta) {
		for (Entity child : children) {
			child.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		canvas.translate(position.getX(), position.getY());
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
	public int getLength() {
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