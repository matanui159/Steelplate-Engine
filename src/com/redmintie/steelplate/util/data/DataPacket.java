package com.redmintie.steelplate.util.data;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.IOException;

public class DataPacket {
	private static byte[] data = new byte[65536];
	private static int point;
	
	private java.io.DataInputStream in;
	private int offset;
	private long header;
	private int size;
	public DataPacket(long header, DataInput in) throws IOException {
		this.header = header >>> 16;
		size = (int)(header & 0xFFFF);
		
		synchronized (data) {
			offset = point;
			point += size;
			if (point > data.length) {
				offset = 0;
				point = size;
			}
		}
		
		in.readFully(data, offset, size);
		this.in = new java.io.DataInputStream(new ByteArrayInputStream(data, offset, size));
	}
	public <T extends DataObject> T getObject(T result) throws IOException {
		if (header != result.getHeader()) {
			throw new IOException("Incorrect header.");
		}
		if (size < result.getMinSize()) {
			throw new IOException("Not enough data.");
		}
		in.reset();
		result.readData(in, size);
		return result;
	}
}