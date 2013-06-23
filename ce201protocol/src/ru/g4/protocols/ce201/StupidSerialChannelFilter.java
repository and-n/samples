package ru.g4.protocols.ce201;

import java.io.IOException;
import java.util.Arrays;

import ru.g4.phch2.FilterChannel;
import ru.g4.phch2.IPhysicalChannel;
import ru.g4.utils.HEXUtils;

public class StupidSerialChannelFilter extends FilterChannel {

	private static final int[] parities = new int[256];
	static{
		for (int i = 0; i < 256; i++)
			parities[i] = i % 2;
	}
	
	public StupidSerialChannelFilter(IPhysicalChannel underlying) {
		super(underlying);
	}
	
	protected int checkParity(int b) throws ParityCheckException {
		int parity = parities[b & 0x7f];
		if (parity != ((b & 0x80) >> 7))
			throw new ParityCheckException(HEXUtils.toString(b, 1));
		return parity;
	}
	
	protected int calcParity(int b)
	{
		b = b & 0x07F;
		int parity = parities[b];
		return (parity << 7) | b;
	}

	@Override
	protected int read() throws IOException {
		int b = super.read();
		return checkParity(b); 
	}

	@Override
	protected int read(byte[] b, int off, int len) throws IOException {
		int count = super.read(b, off, len);
		for (int i = off; i < count+off; i++)
			b[i] = (byte)checkParity(b[i]);
		return count;
	}

	@Override
	protected int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	protected void write(int b) throws IOException {
		super.write(calcParity(b));
	}

	@Override
	protected void write(byte[] b) throws IOException {
		byte[] buf = new byte[b.length]; 
		for (int i = 0; i < b.length; i++)
			buf[i] = (byte)calcParity(b[i]);
		super.write(buf);
	}

	@Override
	protected void write(byte[] b, int off, int len) throws IOException {
		byte[] buf = Arrays.copyOfRange(b, off, off+len);
		write(buf, 0, buf.length);
	}
}
