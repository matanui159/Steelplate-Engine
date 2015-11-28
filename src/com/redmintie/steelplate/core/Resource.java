package com.redmintie.steelplate.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.redmintie.steelplate.device.Device;

public class Resource {	
	public static InputStream getResourceAsStream(String name) throws IOException {
		InputStream stream = Resource.class.getResourceAsStream("/" + name);
		if (stream == null) {
			stream = new FileInputStream(name);
		}
		return stream;
	}
	public static Device loadDevice(String list) throws DeviceException {
		Scanner scanner = null;
		try {
			scanner = new Scanner(getResourceAsStream(list));
			while (scanner.hasNextLine()) {
				try {
					Device device = (Device)Class.forName(scanner.nextLine()).getConstructor()
							.newInstance();
					return device;
				} catch (ClassNotFoundException ex) {
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			throw new DeviceException("No supported devices.");
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}
}