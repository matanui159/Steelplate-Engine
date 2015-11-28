package com.redmintie.steelplate.util.data;

import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStream implements AutoCloseable {
	private java.io.DataOutputStream stream;
	public DataOutputStream(OutputStream stream) {
		this.stream = new java.io.DataOutputStream(stream);
	}
	public void writeDataObject(DataObject object) throws IOException {
		stream.writeLong(object.getHeader());
		stream.writeShort(object.getSize());
		object.writeData(stream);
	}
	@Override
	public void close() throws IOException {
		stream.close();
	}
}