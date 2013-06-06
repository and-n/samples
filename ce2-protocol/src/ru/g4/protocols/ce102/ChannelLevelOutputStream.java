package ru.g4.protocols.ce102;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ChannelLevelOutputStream extends FilterOutputStream {

	private CalckCrcStream crcStream = new CalckCrcStream();

	public ChannelLevelOutputStream(OutputStream out) {
		super(out);
	}

	private void writeDoubleByteInteger(int value) throws IOException {
		int[] result = new int[2];
		result[1] = (0xff & (value >> 8));
		result[0] = (0xff & value);
		writePacketByte(result[0], false);
		writePacketByte(result[1], false);
	}

	public void writePacket(ChannelLevelPacket packet) throws IOException {
		try {
			writePacketByte(0xC0, true);
			writePacketByte(packet.getOpt(), false);
			writeDoubleByteInteger(packet.getAddressD());
			writeDoubleByteInteger(packet.getAddressS());
			for(int i=0; i<packet.getPacketData().length; i++)
			{
				writePacketByte((packet.getPacketData()[i])&0xFF, false);
			}
			writePacketByte(crcStream.getCrc(), false);
			writePacketByte(0xC0, true);
		} finally {
			crcStream.reset();
		}
	}

	private void writePacketByte(int value, boolean isSpecial)
			throws IOException {
		if (isSpecial) {
			write(value);
			return;
		}
		crcStream.write(value);
		switch (value) {
		case 0xC0:
			write(0xDB);
			write(0xDC);
			break;
		case 0xDB:
			write(0xDB);
			write(0xDD);
			break;
		default:
			write(value);
			break;
		}
	}
}
