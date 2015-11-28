package com.redmintie.steelplate.core;

import java.io.DataInput;
import java.io.DataOutput;

public interface DataObject {
	public long getHeader();
	public int getSize();
	public void writeData(DataOutput out);
	public void readData(DataInput in, int size);
	public void cloneData(DataObject obj);
}