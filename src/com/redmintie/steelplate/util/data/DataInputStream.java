package com.redmintie.steelplate.util.data;

import java.io.IOException;
import java.io.InputStream;

public class DataInputStream implements AutoCloseable {
	private java.io.DataInputStream stream;
	private DataPackage pkg;
	public DataInputStream(InputStream stream) {
		this.stream = new java.io.DataInputStream(stream);
	}
	public DataPackage readDataPackage() throws IOException {
		if (pkg != null) {
			pkg.close();
		}
		return pkg = new DataPackage(stream);
	}
	public <T extends DataObject> T readDataObject(T result) throws IOException {
		if (stream.readLong() != result.getHeader()) {
			stream.skipBytes(stream.readUnsignedShort());
			throw new IOException("Incorrect header.");
		}
		result.readData(stream, stream.readUnsignedShort());
		return result;
	}
	@Override
	public void close() throws IOException {
		stream.close();
	}
}