package com.redmintie.steelplate.core;

/**
 * The exception thrown when a device can't be loaded.
 * 
 * @see com.redmintie.steelplate.device.Device
 */
public class DeviceException extends Exception {
	private static final long serialVersionUID = 0;
	
	/**
	 * Creates a new Device Exception.
	 */
	public DeviceException() {
	}
	
	/**
	 * Creates a new Device Exception.
	 * 
	 * @param msg The message to display.
	 */
	public DeviceException(String msg) {
		super(msg);
	}
	
	/**
	 * Creates a new Device Exception.
	 * 
	 * @param ex The throwable that caused this.
	 */
	public DeviceException(Throwable ex) {
		super(ex);
	}
	
	/**
	 * Creates a new Device Exception.
	 * 
	 * @param msg The message to display.
	 * @param ex The throwable that caused this.
	 */
	public DeviceException(String msg, Throwable ex) {
		super(msg, ex);
	}
}