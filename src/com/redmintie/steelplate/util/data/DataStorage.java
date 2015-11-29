package com.redmintie.steelplate.util.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.redmintie.steelplate.util.multithread.MultiThreadAction;
import com.redmintie.steelplate.util.multithread.MultiThreadListener;

public class DataStorage {
	private static File getFile(String name) {
		File file = new File("data/" + name.replace('.', '/') + ".dat");
		if (!file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}
	public synchronized static <T extends DataObject> T readObject(String name, T result) throws IOException {
		DataInputStream in = new DataInputStream(new FileInputStream(getFile(name)));
		in.readDataObject(result);
		in.close();
		return result;
	}
	public synchronized static void writeObject(String name, DataObject object) throws IOException {
		DataOutputStream out = new DataOutputStream(new FileOutputStream(getFile(name)));
		out.writeDataObject(object);
		out.close();
	}
	public static void writeObjectLater(String name, DataObject object, MultiThreadListener listener) {
		new MultiThreadAction(name, listener) {
			@Override
			public void doAction() throws IOException {
				writeObject(name, object);
			}
		}.start();
	}
	public synchronized static void deleteObject(String name) {
		getFile(name).delete();
	}
}