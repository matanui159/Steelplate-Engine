package com.redmintie.steelplate.node;

import java.util.ArrayList;

import com.redmintie.steelplate.math.Point;
import com.redmintie.steelplate.render.Canvas;

public class Node {
	public Point position = new Point();
	public double angle;
	public int width;
	public int height;
	private ArrayList<Node> children = new ArrayList<Node>();
	public boolean testOverlap(Node other) {
		return position.getX() - width / 2 < other.position.getX() + other.width / 2
				|| position.getX() + width / 2 > other.position.getX() - other.width / 2
				|| position.getY() - height / 2 < other.position.getY() + other.height / 2
				|| position.getY() + height / 2 > other.position.getY() - other.height / 2;
	}
	public void update(double delta) {
		for (Node child : children) {
			child.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		canvas.translate(position.getX(), position.getY());
		canvas.rotate(angle);
		canvas.translate(-width / 2, -height / 2);
		for (Node child : children) {
			canvas.pushMatrix();
			child.draw(canvas);
			canvas.popMatrix();
		}
	}
	public void end() {
		for (Node child : children) {
			child.end();
		}
	}
	public void addChild(Node node) {
		children.add(node);
	}
	public int getLength() {
		return children.size();
	}
	public Node getChild(int i) {
		return children.get(i);
	}
	public void removeChild(Node node) {
		children.remove(node);
	}
	public void clearChildren() {
		children.clear();
	}
}