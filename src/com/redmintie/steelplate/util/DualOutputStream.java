package com.redmintie.steelplate.util;

import java.io.IOException;
import java.io.OutputStream;

public class DualOutputStream extends OutputStream {
	private OutputStream a;
	private OutputStream b;
	@Override
	public void write(int b) throws IOException {
		a.write(b);
		this.b.write(b);
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		a.write(b, off, len);
		this.b.write(b, off, len);
	}
	@Override
	public void flush() throws IOException {
		a.flush();
		b.flush();
	}
	@Override
	public void close() throws IOException {
		a.close();
		b.close();
	}
}