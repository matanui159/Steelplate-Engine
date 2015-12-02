package demo.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.redmintie.steelplate.entity.Entity;
import com.redmintie.steelplate.util.data.DataUtil;

public class PlayerPosition extends Entity {
	private static final long HEADER = DataUtil.generateHeader("XDEMOX", "PLAYER");
	private static final int SIZE = 25;
	public int id;
	@Override
	public long getHeader() {
		return HEADER;
	}
	@Override
	public int getMinSize() {
		return SIZE;
	}
	@Override
	public int getSize() {
		return SIZE;
	}
	@Override
	public void writeData(DataOutput out) throws IOException {
		super.writeData(out);
		out.writeByte(id);
	}
	@Override
	public void readData(DataInput in, int size) throws IOException {
		super.readData(in, super.getSize());
		id = in.readByte();
	}
}