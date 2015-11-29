package com.redmintie.steelplate.util.data;

public class DataUtil {
	private static long getByte(String group, String name, int i) {
		return (byte)group.charAt(i) ^ (byte)name.charAt(i) & 255;
	}
	public static long generateHeader(String group, String name) {
		return getByte(group, name, 0) << 40 | getByte(group, name, 1) << 32
				| getByte(group, name, 2) << 24 | getByte(group, name, 3) << 16
				| getByte(group, name, 4) << 8 | getByte(group, name, 5);
	}
	public static int getUTFSize(String str) {
		int size = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 0x0001 && c <= 0x007F) {
				size++;
			} else if (c > 0x07FF) {
				size += 3;
			} else {
				size += 2;
			}
		}
		return size + 2;
	}
}