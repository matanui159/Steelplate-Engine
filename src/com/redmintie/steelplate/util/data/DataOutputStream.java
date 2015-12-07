package com.redmintie.steelplate.util.data;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStream implements AutoCloseable {
	private java.io.DataOutputStream stream;
	public DataOutputStream(OutputStream stream) {
		this.stream = new java.io.DataOutputStream(stream);
	}
	public DataOutput getDataOutput() {
		return stream;
	}
	public void writeDataObject(DataObject object) throws IOException {
		stream.writeLong(object.getHeader() << 16 | object.getSize());
		object.writeData(stream);
	}
	@Override
	public void close() throws IOException {
		stream.close();
	}
}