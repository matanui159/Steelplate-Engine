package com.redmintie.steelplate.util.math;

public class Point extends Vector {
	public Point(double x, double y) {
		super(2);
		setAll(x, y);
	}
	public Point(Point other) {
		super(other);
	}
	public Point() {
		super(2);
	}
	public Point setX(double x) {
		values[0] = x;
		return this;
	}
	public double getX() {
		return values[0];
	}
	public Point setY(double y) {
		values[1] = y;
		return this;
	}
	public double getY() {
		return values[1];
	}
	public double getAngle() {
		return Math.toDegrees(Math.atan2(values[1], values[0]));
	}
	public double getAngleTo(double... other) {
		checkSize(other, false);
		return Math.toDegrees(Math.atan2(
				other[1] - values[1],
				other[0] - values[0]
		));
	}
	public double getAngleTo(Vector other) {
		return getAngleTo(other.values);
	}
	public Point rotate(double angle) {
		double rad = Math.toRadians(angle);
		double x = values[0] * Math.cos(rad) - values[1] * Math.sin(rad);
		double y = values[0] * Math.sin(rad) + values[1] * Math.cos(rad);
		values[0] = x;
		values[1] = y;
		return this;
	}
	public Point moveAtAngle(double x, double y, double angle) {
		double rad = Math.toRadians(angle);
		values[0] += x * Math.cos(rad) - y * Math.sin(rad);
		values[1] += x * Math.sin(rad) + y * Math.cos(rad);
		return this;
	}
	public Point moveAtAngle(Point speed, double angle) {
		return moveAtAngle(speed.values[0], speed.values[1], angle);
	}
	public Point moveTowards(double x, double y, double speed) {
		if (getDistanceTo(x, y) < speed) {
			values[0] = x;
			values[1] = y;
		} else {
			Vector dir = getDirectionVector();
			values[0] += dir.values[0] * speed;
			values[1] += dir.values[1] * speed;
		}
		return this;
	}
}