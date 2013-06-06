package ru.g4.protocols.ce102;

import java.io.ByteArrayOutputStream;

import ru.g4.utils.HEXUtils;

public class CalckCrcStream extends ByteArrayOutputStream {
	public int getCrc() {
		
		int crc=0;
		byte[] packetBytes = toByteArray();
		System.out.println("считаем на основе "+HEXUtils.toHexString(packetBytes));
		for (int i = 0; i < packetBytes.length; i++) {
			crc=crc ^ packetBytes[i]; 
		}
		return crc;
	}
}
