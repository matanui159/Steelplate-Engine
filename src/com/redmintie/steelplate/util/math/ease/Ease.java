package com.redmintie.steelplate.util.math.ease;

public abstract class Ease {
	private double start;
	private double diff;
	private double time;
	private double t;
	public Ease(double start, double end, double time) {
		this.start = start;
		this.diff = end - start;
		this.time = time;
	}
	public double update(double delta) {
		t = Math.min(t + delta / time, 1);
		return start + diff * ease(t);
	}
	public abstract double ease(double t);
}