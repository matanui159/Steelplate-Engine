package com.redmintie.steelplate.core;

public class DeviceException extends Exception {
	private static final long serialVersionUID = 0;
	public DeviceException() {
	}
	public DeviceException(String msg) {
		super(msg);
	}
	public DeviceException(Throwable ex) {
		super(ex);
	}
	public DeviceException(String msg, Throwable ex) {
		super(msg, ex);
	}
}