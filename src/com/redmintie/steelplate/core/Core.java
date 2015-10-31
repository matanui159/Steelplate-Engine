package com.redmintie.steelplate.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.redmintie.steelplate.device.Device;

public class Core {
	public static InputStream getResourceAsStream(String name) throws IOException {
		InputStream stream = Core.class.getResourceAsStream("/" + name);
		if (stream == null) {
			stream = new FileInputStream(name);
		}
		return stream;
	}
	public static Device loadDevice(Game game, String list) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(getResourceAsStream(list));
			while (scanner.hasNextLine()) {
				try {
					Device device = (Device)Class.forName(scanner.nextLine())
							.getConstructor(Game.class).newInstance(game);
					return device;
				} catch (Exception ex) {}
			}
			throw new RuntimeException("No supported devices.");
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