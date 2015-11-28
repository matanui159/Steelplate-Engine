package com.redmintie.steelplate.util.data;

import java.io.DataInput;
import java.io.IOException;

public class DataPackage implements AutoCloseable {
	private DataInput in;
	private boolean read;
	public DataPackage(DataInput in) {
		this.in = in;
	}
	public <T extends DataObject> T getData(T result) throws IOException {
		if (read) {
			throw new IOException("Data can only be read once.");
		}
		read = true;
		if (in.readLong() != result.getHeader()) {
			in.skipBytes(in.readUnsignedShort());
			throw new IOException("Incorrect header.");
		}
		result.readData(in, in.readUnsignedShort());
		return result;
	}
	@Override
	public void close() throws IOException {
		if (!read) {
			in.skipBytes(8);
			in.skipBytes(in.readUnsignedShort());
		}
		read = true;
	}
}