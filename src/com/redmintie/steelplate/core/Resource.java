package com.redmintie.steelplate.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.redmintie.steelplate.device.Device;
import com.redmintie.steelplate.util.Map;

public class Resource {
	private static Map<String, String> stringData;
	private static Map<String, Double> numberData;
	public static InputStream getResourceAsStream(String name) throws IOException {
		InputStream stream = Resource.class.getResourceAsStream("/" + name);
		if (stream == null) {
			stream = new FileInputStream(name);
		}
		return stream;
	}
	private static void loadData() {
		if (stringData == null) {
			try {
				stringData = new Map<String, String>();
				numberData = new Map<String, Double>();
			
				DataInputStream stream = new DataInputStream(new FileInputStream("data.dat"));
				while (stream.available() > 0) {
					String name = stream.readUTF();
					if (stream.readBoolean()) {
						numberData.set(name, stream.readDouble());
					} else {
						stringData.set(name, stream.readUTF());
					}
				}
				stream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void saveData() throws IOException {
		loadData();
		DataOutputStream stream = new DataOutputStream(new FileOutputStream("data.dat"));
		for (String name : stringData) {
			stream.writeUTF(name);
			stream.writeBoolean(false);
			stream.writeUTF(stringData.get(name));
		}
		for (String name : numberData) {
			stream.writeUTF(name);
			stream.writeBoolean(true);
			stream.writeDouble(numberData.get(name));
		}
		stream.close();
	}
	public synchronized static String loadString(String name, String def) {
		loadData();
		String value = stringData.get(name);
		return value == null ? def : value;
	}
	public synchronized static void saveString(String name, String value) {
		stringData.set(name, value);
	}
	public synchronized static double loadNumber(String name, double def) {
		loadData();
		Double value = numberData.get(name);
		return value == null ? def : value;
	}
	public synchronized static void saveNumber(String name, double value) {
		numberData.set(name, value);
	}
	public static Device loadDevice(String list) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(getResourceAsStream(list));
			while (scanner.hasNextLine()) {
				try {
					Device device = (Device)Class.forName(scanner.nextLine()).getConstructor()
							.newInstance();
					return device;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
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