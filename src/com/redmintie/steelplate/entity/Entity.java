package com.redmintie.steelplate.entity;

import java.util.Iterator;

import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Array;
import com.redmintie.steelplate.util.Point;

public class Entity implements Iterable<Entity> {
	public Point position = new Point();
	public double angle;
	public int width;
	public int height;
	private boolean relative;
	private Entity parent;
	private Array<Entity> children = new Array<Entity>();
	public boolean testOverlap(Entity other) {
		double x = getTrueX();
		double y = getTrueY();
		double ox = other.getTrueX();
		double oy = other.getTrueY();
		return x - width / 2 < ox + other.width / 2
				&& x + width / 2 > ox - other.width / 2
				&& y - height / 2 < oy + other.height / 2
				&& y + height / 2 > oy - other.height / 2;
	}
	public void setRelative(boolean relative) {
		this.relative = relative;
	}
	public boolean isRelative() {
		return relative;
	}
	public double getTrueX() {
		double x = position.x;
		if (relative && parent != null) {
			x += parent.getTrueX();
		}
		return x;
	}
	public double getTrueY() {
		double y = position.y;
		if (relative && parent != null) {
			y += parent.getTrueY();
		}
		return y;
	}
	public double getTrueAngle() {
		double angle = this.angle;
		if (relative && parent != null) {
			angle += parent.getTrueX();
		}
		return angle;
	}
	public void update(double delta) {
		for (Entity child : children) {
			child.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		if (!relative) {
			canvas.resetMatrix();
		}
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
	public int addChild(Entity child) {
		child.parent = this;
		return children.add(child);
	}
	public int getChildCount() {
		return children.size();
	}
	public Entity getChild(int id) {
		return children.get(id);
	}
	public Entity removeChild(int id) {
		Entity child = children.get(id);
		if (child != null) {
			removeChild(child);
		}
		return child;
	}
	public void removeChild(Entity child) {
		child.parent = null;
		children.remove(child);
	}
	public void clearChildren() {
		children.clear();
	}
	@Override
	public Iterator<Entity> iterator() {
		return children.iterator();
	}
}