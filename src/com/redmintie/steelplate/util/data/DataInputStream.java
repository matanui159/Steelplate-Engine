package com.redmintie.steelplate.util.data;

import java.io.IOException;
import java.io.InputStream;

public class DataInputStream implements AutoCloseable {
	private java.io.DataInputStream stream;
	public DataInputStream(InputStream stream) {
		this.stream = new java.io.DataInputStream(stream);
	}
	public DataPacket readDataPacket() throws IOException {
		return new DataPacket(stream);
	}
	public <T extends DataObject> T readDataObject(T result) throws IOException {
		long header = stream.readLong();
		if (header >>> 16 != result.getHeader()) {
			stream.skipBytes((int)(header & 0xFFFF));
			throw new IOException("Incorrect header.");
		}
		result.readData(stream, (int)(header & 0xFFFF));
		return result;
	}
	@Override
	public void close() throws IOException {
		stream.close();
	}
}