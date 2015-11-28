package com.redmintie.steelplate.util.data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface DataObject {
	public long getHeader();
	public int getSize();
	public void writeData(DataOutput out) throws IOException;
	public void readData(DataInput in, int size) throws IOException;
}