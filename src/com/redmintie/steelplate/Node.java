package com.redmintie.steelplate;

import java.util.ArrayList;

import com.redmintie.steelplate.render.Canvas;

public class Node {
	private ArrayList<Node> children = new ArrayList<Node>();
	public void update(double delta) {
		for (Node child : children) {
			child.update(delta);
		}
	}
	public void draw(Canvas canvas) {
		for (Node child : children) {
			child.draw(canvas);
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