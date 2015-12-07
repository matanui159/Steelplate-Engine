package com.redmintie.steelplate.util.data;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;

public class DataInputStream implements AutoCloseable {
	private java.io.DataInputStream stream;
	public DataInputStream(InputStream stream) {
		this.stream = new java.io.DataInputStream(stream);
	}
	public DataInput getDataInput() {
		return stream;
	}
	public DataPacket readDataPacket() throws IOException {
		return new DataPacket(stream.readLong(), stream);
	}
	public <T extends DataObject> T readDataObject(T result) throws IOException {
		long header = stream.readLong();
		int size = (int)(header & 0xFFFF);
		if (header >>> 16 != result.getHeader()) {
			stream.skipBytes(size);
			throw new IOException("Incorrect header.");
		}
		if (size < result.getMinSize()) {
			throw new IOException("Not enough data.");
		}
		result.readData(stream, size);
		int s = result.getSize();
		if (s < size) {
			stream.skipBytes(size - s);
		}
		return result;
	}
	@Override
	public void close() throws IOException {
		stream.close();
	}
}