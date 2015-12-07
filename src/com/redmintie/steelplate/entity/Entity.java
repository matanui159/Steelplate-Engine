package com.redmintie.steelplate.entity;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;

import com.redmintie.steelplate.render.Canvas;
import com.redmintie.steelplate.util.Point;
import com.redmintie.steelplate.util.data.DataObject;
import com.redmintie.steelplate.util.data.DataUtil;

public class Entity implements Iterable<Entity>, DataObject {
	private static final long HEADER = DataUtil.generateHeader("STLPLT", "ENTITY");
	private static final int SIZE = 24;
	
	public Point position = new Point();
	public double angle;
	public int width;
	public int height;
	public boolean relative;
	
	private Entity parent;
	private Entity children;
	private Entity last;
	private int count;
	private Entity prev;
	private Entity next;
	private EntityIterator iterator;
	
	@Override
	public long getHeader() {
		return HEADER;
	}
	@Override
	public int getMinSize() {
		return SIZE;
	}
	@Override
	public int getSize() {
		return SIZE;
	}
	@Override
	public void writeData(DataOutput out) throws IOException {
		position.writeData(out);
		out.writeDouble(angle);
	}
	@Override
	public void readData(DataInput in, int size) throws IOException {
		position.readData(in, 16);
		angle = in.readDouble();
	}
	public boolean testPoint(double x, double y) {
		double ox = getTrueX();
		double oy = getTrueY();
		return x < ox + width / 2 && x > ox - width / 2
				&& y < oy + height / 2 && y > oy - height / 2;
	}
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
		Entity child = children;
		while (child != null) {
			Entity c = child;
			child = child.next;
			c.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		if (!relative) {
			canvas.resetMatrix();
		}
		canvas.translate(position.x, position.y);
		canvas.rotate(angle);
		canvas.translate(-width / 2, -height / 2);
		Entity child = children;
		while (child != null) {
			Entity c = child;
			child = child.next;
			canvas.pushMatrix();
			c.draw(canvas);
			canvas.popMatrix();
		}
	}
	public void end() {
		clearChildren();
	}
	public Entity getParent() {
		return parent;
	}
	public void addChild(Entity child) {
		child.parent = this;
		if (last == null) {
			children = child;
		} else {
			last.next = child;
			child.prev = last;
		}
		last = child;
		count++;
	}
	public int getChildCount() {
		return count;
	}
	public void removeChild(Entity child) {
		if (child.parent == this) {
			if (child.prev == null) {
				children = child.next;
			} else {
				child.prev.next = child.next;
			}
			if (child.next == null) {
				last = child.prev;
			} else {
				child.next.prev = child.prev;
			}
			child.parent = child.prev = child.next = null;
			count--;
		}
	}
	public void clearChildren() {
		Entity child = children;
		while (child != null) {
			child.end();
			Entity next = child.next;
			child.parent = child.prev = child.next = null;
			child = next;
		}
		children = last = null;
		count = 0;
	}
	@Override
	public Iterator<Entity> iterator() {
		if (iterator == null) {
			iterator = new EntityIterator();
		}
		iterator.child = children;
		return iterator;
	}
	private class EntityIterator implements Iterator<Entity> {
		private Entity child;
		@Override
		public boolean hasNext() {
			return child != null;
		}
		@Override
		public Entity next() {
			if (hasNext()) {
				Entity c = child;
				child = child.next;
				return c;
			}
			return null;
		}
	}
}