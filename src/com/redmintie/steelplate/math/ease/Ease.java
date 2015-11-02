package com.redmintie.steelplate.math.ease;

import com.redmintie.steelplate.math.Point;

public abstract class Ease {
	private Point start;
	private Point diff;
	private double time;
	private double t;
	public Ease(Point start, Point end, double time) {
		this.start = start;
		this.diff = new Point(end);
		diff.sub(start);
		this.time = time;
	}
	public Point update(double delta, Point point) {
		t = Math.min(t + delta / time, 1);
		point.setAll(
				start.getX() + diff.getX() * ease(t),
				start.getY() + diff.getY() * ease(t)
		);
		return point;
	}
	public abstract double ease(double t);
}