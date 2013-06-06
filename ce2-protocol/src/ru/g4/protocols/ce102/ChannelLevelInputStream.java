package ru.g4.protocols.ce102;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChannelLevelInputStream extends FilterInputStream {

	private CalckCrcStream crcStream = new CalckCrcStream();

	private static final int END_SYMBOL = 0xC0;

	public ChannelLevelInputStream(InputStream in) {
		super(in);
	}

	private int readDoubleByteInteger(InputStream in) throws IOException {
		int lowByte = in.read();
		int highByte = in.read();
		return ((((0) << 24) | ((0) << 16) | ((highByte & 0xff) << 8) | (lowByte & 0xff)));
	}

	public ChannelLevelPacket readPacket() throws IOException, CrcException {

		try {
			waitStartPacket();
			byte[] packetAsByteArray = readPackegeByte();			
			crcStream.write(packetAsByteArray, 0, packetAsByteArray.length - 1);		
			if ((byte)crcStream.getCrc() != packetAsByteArray[packetAsByteArray.length - 1]) {
				throw new CrcException("Не сошлась контрольная сумма в пакете");
			}
			ByteArrayInputStream packteIn = new ByteArrayInputStream(
					packetAsByteArray, 0, packetAsByteArray.length - 1);
			int opt = packteIn.read();
			int addressD = readDoubleByteInteger(packteIn);
			int addressS = readDoubleByteInteger(packteIn);

			ByteArrayOutputStream packetData = new ByteArrayOutputStream();
			for (int val = packteIn.read(); val != -1; val = packteIn.read()) {
				packetData.write(val);
			}
			return new ChannelLevelPacket(addressD, addressS, opt,
					packetData.toByteArray());
		} finally {
			crcStream.reset();
		}
	}

	private void waitStartPacket() throws IOException {
		for (int val = read(); val != END_SYMBOL; val = read()) {
			// ждём начала пакета, проматываем мусор
		}
	}

	private byte[] readPackegeByte() throws IOException {
		ByteArrayOutputStream packet = new ByteArrayOutputStream();
		for (int val = readByte(); val != -1; val = readByte()) {
			packet.write(val);
		}
		return packet.toByteArray();
	}

	private int readByte() throws IOException {
		int val = read();
		if(val==END_SYMBOL)
		{
			return -1;
		}
		if (val == 0xDB) {
			int next = read();
			switch (next) {
			case 0xDC:
				val = 0xC0;
				break;
			case 0xDD:
				val = 0xDB;
				break;
			}
		}
		return val;
	}
}
