package ru.g4.protocols.ce102;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class PacketStreamsTest {

	@Test
	public void testAll() throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try (ChannelLevelOutputStream out = new ChannelLevelOutputStream(
				byteOut);) {
			ChannelLevelPacket packet = new ChannelLevelPacket(0xC0, 261, 0x48,
					new byte[] { (byte) 0xDB, (byte) 0xC1 });
			out.writePacket(packet);
			try (ChannelLevelInputStream in = new ChannelLevelInputStream(
					new ByteArrayInputStream(byteOut.toByteArray()));) {
				ChannelLevelPacket readedPacket = in.readPacket();
				Assert.assertEquals(packet, readedPacket);
			}
		}
	}

}
