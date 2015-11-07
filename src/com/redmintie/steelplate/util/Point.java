package com.redmintie.steelplate.util;

public class Point {
	private Point result;
	public double x;
	public double y;
	public Point(double x, double y) {
		set(x, y);
	}
	public Point(Point other) {
		set(other);
	}
	public Point() {}
	private Point getResult() {
		if (result == null) {
			result = new Point();
		}
		return result;
	}
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
	@Override
	public int hashCode() {
		return (int)x ^ (int)y;
	}
	@Override
	public boolean equals(Object other) {
		if (other instanceof Point) {
			Point point = (Point)other;
			return x == point.x && y == point.y;
		}
		return false;
	}
	public Point set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Point set(Point other) {
		return set(other.x, other.y);
	}
	public Point add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}
	public Point add(double value) {
		return add(value, value);
	}
	public Point add(Point value) {
		return add(value.x, value.y);
	}
	public Point sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	public Point sub(double value) {
		return sub(value, value);
	}
	public Point sub(Point value) {
		return sub(value.x, value.y);
	}
	public Point mul(double x, double y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	public Point mul(double value) {
		return mul(value, value);
	}
	public Point mul(Point value) {
		return mul(value.x, value.y);
	}
	public Point div(double x, double y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	public Point div(double value) {
		return div(value, value);
	}
	public Point div(Point value) {
		return div(value.x, value.y);
	}
	public Point rotate(double angle) {
		angle = Math.toRadians(angle);
		double _x = x * Math.cos(angle) - y * Math.sin(angle);
		y = x * Math.sin(angle) + y * Math.cos(angle);
		x = _x;
		return this;
	}
	public double getMagnitude() {
		return Math.sqrt(x * x + y * y);
	}
	public Point getDirection(Point result) {
		return result.set(this).div(getMagnitude());
	}
	public Point getDirection() {
		return getDirection(getResult());
	}
	public double getAngle() {
		return Math.toDegrees(Math.atan2(y, x));
	}
	public double getDistanceTo(double x, double y) {
		return Math.sqrt((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y));
	}
	public double getDistanceTo(Point other) {
		return getDistanceTo(other.x, other.y);
	}
	public Point getDirectionTo(double x, double y, Point result) {
		return result.set(x, y).sub(this).div(result.getMagnitude());
	}
	public Point getDirectionTo(double x, double y) {
		return getDirectionTo(x, y, getResult());
	}
	public Point getDirectionTo(Point other, Point result) {
		return getDirectionTo(other.x, other.y, result);
	}
	public Point getDirectionTo(Point other) {
		return getDirectionTo(other.x, other.y, getResult());
	}
	public double getAngleTo(double x, double y) {
		return Math.toDegrees(Math.atan2(y - this.y, x - this.x));
	}
	public double getAngleTo(Point other) {
		return getAngleTo(other.x, other.y);
	}
	public Point moveAtAngle(double x, double y, double angle) {
		angle = Math.toRadians(angle);
		return add(
				x * Math.cos(angle) - y * Math.sin(angle),
				x * Math.sin(angle) + y * Math.cos(angle)
		);
	}
	public Point moveAtAngle(Point speed, double angle) {
		return moveAtAngle(speed.x, speed.y, angle);
	}
	public Point moveTowards(double x, double y, double speed) {
		if (getDistanceTo(x, y) <= speed) {
			return set(x, y);
		} else {
			x -= this.x;
			y -= this.y;
			double mag = Math.sqrt(x * x + y * y);
			return add(
					x / mag * speed,
					y / mag * speed
			);
		}
	}
	public Point moveTowards(Point other, double speed) {
		return moveTowards(other.x, other.y, speed);
	}
//	public Point() {
//		super(2);
//	}
//	public Point setX(double x) {
//		values[0] = x;
//		return this;
//	}
//	public double getX() {
//		return values[0];
//	}
//	public Point setY(double y) {
//		values[1] = y;
//		return this;
//	}
//	public double getY() {
//		return values[1];
//	}
//	public double getAngle() {
//		return Math.toDegrees(Math.atan2(values[1], values[0]));
//	}
//	public double getAngleTo(double... other) {
//		checkSize(other, false);
//		return Math.toDegrees(Math.atan2(
//				other[1] - values[1],
//				other[0] - values[0]
//		));
//	}
//	public double getAngleTo(Vector other) {
//		return getAngleTo(other.values);
//	}
//	public Point rotate(double angle) {
//		double rad = Math.toRadians(angle);
//		double x = values[0] * Math.cos(rad) - values[1] * Math.sin(rad);
//		double y = values[0] * Math.sin(rad) + values[1] * Math.cos(rad);
//		values[0] = x;
//		values[1] = y;
//		return this;
//	}
//	public Point moveAtAngle(double x, double y, double angle) {
//		double rad = Math.toRadians(angle);
//		values[0] += x * Math.cos(rad) - y * Math.sin(rad);
//		values[1] += x * Math.sin(rad) + y * Math.cos(rad);
//		return this;
//	}
//	public Point moveAtAngle(Point speed, double angle) {
//		return moveAtAngle(speed.values[0], speed.values[1], angle);
//	}
//	public Point moveTowards(double x, double y, double speed) {
//		if (getDistanceTo(x, y) < speed) {
//			values[0] = x;
//			values[1] = y;
//		} else {
//			double[] dir = getDirectionTo(x, y);
//			values[0] += dir[0] * speed;
//			values[1] += dir[1] * speed;
//		}
//		return this;
//	}
}