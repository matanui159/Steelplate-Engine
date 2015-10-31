package com.redmintie.steelplate.math;

public class Vector {
	protected double[] values;
	private Vector result;
	public Vector(int size) {
		values = new double[size];
	}
	public Vector(Vector other) {
		this(other.values.length);
		setAll(other.values);
	}
	protected void checkSize(double[] values, boolean allowSingle) {
		if (allowSingle) {
			if (values.length != 1 && values.length != this.values.length) {
				throw new IllegalArgumentException("Values length must either be 1 or " + this.values.length + ".");
			}
		} else {
			if (values.length != this.values.length) {
				throw new IllegalArgumentException("Values length must be " + this.values.length + ".");
			}
		}
	}
	private Vector getResultVector() {
		if (result == null) {
			result = new Vector(values.length);
		}
		return result;
	}
	public int size() {
		return values.length;
	}
	public Vector set(int i, double value) {
		if (i >= 0 && i < values.length) {
			values[i] = value;
		}
		return this;
	}
	public Vector setAll(double... values) {
		checkSize(values, true);
		for (int i = 0; i < this.values.length; i++) {
			if (values.length == 1) {
				this.values[i] = values[1];
			} else {
				this.values[i] = values[i];
			}
		}
		return this;
	}
	public Vector setAll(Vector vector) {
		return setAll(vector.values);
	}
	public double get(int i) {
		if (i < 0 || i >= values.length) {
			throw new IndexOutOfBoundsException();
		}
		return values[i];
	}
	public double[] getAll(double... result) {
		checkSize(result, false);
		for (int i = 0; i < values.length; i++) {
			result[i] = values[i];
		}
		return result;
	}
	public double[] getAll() {
		return getAll(getResultVector().values);
	}
	public Vector add(double... values) {
		checkSize(values, true);
		for (int i = 0; i < this.values.length; i++) {
			if (values.length == 1) {
				this.values[i] += values[1];
			} else {
				this.values[i] += values[i];
			}
		}
		return this;
	}
	public Vector add(Vector vector) {
		return add(vector.values);
	}
	public Vector sub(double... values) {
		checkSize(values, true);
		for (int i = 0; i < this.values.length; i++) {
			if (values.length == 1) {
				this.values[i] -= values[1];
			} else {
				this.values[i] -= values[i];
			}
		}
		return this;
	}
	public Vector sub(Vector vector) {
		return sub(vector.values);
	}
	public Vector mul(double... values) {
		checkSize(values, true);
		for (int i = 0; i < this.values.length; i++) {
			if (values.length == 1) {
				this.values[i] *= values[1];
			} else {
				this.values[i] *= values[i];
			}
		}
		return this;
	}
	public Vector mul(Vector vector) {
		return mul(vector.values);
	}
	public Vector div(double... values) {
		checkSize(values, true);
		for (int i = 0; i < this.values.length; i++) {
			if (values.length == 1) {
				this.values[i] /= values[1];
			} else {
				this.values[i] /= values[i];
			}
		}
		return this;
	}
	public Vector div(Vector vector) {
		return div(vector.values);
	}
	public double getMagnitude() {
		double value = 0;
		for (int i = 0; i < values.length; i++) {
			value += values[i] * values[i];
		}
		return Math.sqrt(value);
	}
	public double[] getDirection(double[] result) {
		checkSize(result, false);
		double mag = getMagnitude();
		for (int i = 0; i < values.length; i++) {
			result[i] = values[i] / mag;
		}
		return result;
	}
	public double[] getDirection() {
		return getDirection(getResultVector().values);
	}
	public Vector getDirectionVector(Vector result) {
		getDirection(result.values);
		return result;
	}
	public Vector getDirectionVector() {
		return getDirectionVector(getResultVector());
	}
	public double getDistanceTo(double... other) {
		checkSize(other, false);
		double value = 0;
		for (int i = 0; i < values.length; i++) {
			double diff = other[i] - values[i];
			value += diff * diff;
		}
		return Math.sqrt(value);
	}
	public double getDistanceTo(Vector other) {
		return getDistanceTo(other.values);
	}
	public double[] getDirectionTo(double[] other, double[] result) {
		checkSize(other, false);
		checkSize(result, false);
		double mag = getDistanceTo(other);
		for (int i = 0; i < values.length; i++) {
			result[i] = (other[i] - values[i]) / mag;
		}
		return result;
	}
	public double[] getDirectionTo(double... other) {
		return getDirectionTo(other, getResultVector().values);
	}
	public Vector getDirectionTo(Vector other, Vector result) {
		getDirectionTo(other.values, result.values);
		return result;
	}
	public Vector getDirectionTo(Vector other) {
		return getDirectionTo(other, getResultVector());
	}
	public double getAngleBetween(double... other) {
		checkSize(other, false);
		double dot = 0, a = 0, b = 0;
		for (int i = 0; i < values.length; i++) {
			dot += other[i] * values[i];
			a += values[i] * values[i];
			b += other[i] * other[i];
		}
		return Math.toDegrees(Math.acos(dot / (Math.sqrt(a) * Math.sqrt(b))));
	}
	public double getAngleBetween(Vector other) {
		return getAngleBetween(other.values);
	}
}