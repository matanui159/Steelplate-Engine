package com.redmintie.steelplate.util.math.ease;

public class EaseOut extends Ease {
	private double power;
	public EaseOut(double start, double end, double time, double power) {
		super(start, end, time);
		this.power = power;
	}
	@Override
	public double ease(double t) {
		return 1 - Math.pow(1 - t, power);
	}
}