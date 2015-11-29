package com.redmintie.steelplate.util.data;

import java.io.DataInput;
import java.io.IOException;

public class DataPacket implements AutoCloseable {
	private DataInput in;
	private boolean read;
	public DataPacket(DataInput in) {
		this.in = in;
	}
	public <T extends DataObject> T getObject(T result) throws IOException {
		if (read) {
			throw new IOException("Data can only be read once.");
		}
		read = true;
		long header = in.readLong();
		if (header >>> 16 != result.getHeader()) {
			in.skipBytes((int)(header & 0xFFFF));
			throw new IOException("Incorrect header.");
		}
		result.readData(in, (int)(header & 0xFFFF));
		return result;
	}
	@Override
	public void close() throws IOException {
		if (!read) {
			in.skipBytes(6);
			in.skipBytes(in.readUnsignedShort());
		}
		read = true;
	}
}