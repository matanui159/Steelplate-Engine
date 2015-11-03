package com.redmintie.steelplate.util.math.ease;

import com.redmintie.steelplate.util.math.Point;

public class EaseOut extends Ease {
	private double power;
	public EaseOut(Point start, Point end, double time, double power) {
		super(start, end, time);
		this.power = power;
	}
	@Override
	public double ease(double t) {
		return 1 - Math.pow(1 - t, power);
	}
}