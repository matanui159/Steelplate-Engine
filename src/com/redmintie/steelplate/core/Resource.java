package com.redmintie.steelplate.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.redmintie.steelplate.device.Device;

/**
 * The Resource class handles loading resources and devices.
 */
public class Resource {
	/**
	 * Gets a resource from a file name.
	 * It first tries to load the resource from within the jar,
	 * then if that fails, it will try to load from outside the jar.
	 * If both methods fail, it will throw an IOException.
	 * 
	 * @param name The name of the file.
	 * @return The inputstream of that resource.
	 * @throws IOException If it can't get the resource.
	 */
	public static InputStream getResourceAsStream(String name) throws IOException {
		InputStream stream = Resource.class.getResourceAsStream("/" + name);
		if (stream == null) {
			stream = new FileInputStream(name);
		}
		return stream;
	}
	
	/**
	 * Loads a device from a *.list file.
	 * A *.list file is a simple text file with a new class each line.
	 * It is expected that each of these classes implement the
	 * {@link com.redmintie.steelplate.device.Device} interface.
	 * 
	 * @param list The *.list file that contains the devices.
	 * @return The first device that doesn't fail.
	 * @throws DeviceException If no device could be found.
	 */
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