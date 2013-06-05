package ru.g4.protocols.ce102;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import junit.framework.Assert;

import org.junit.Test;

import ru.g4.utils.HEXUtils;

public class PALInputStreamTest {

	@Test
	public void testReadRequest() throws IOException {
		byte[] input = new byte[] {0x31, 0x32, 0x33, 0x34, (byte)0xD3, 0x01, 0x20, (byte)0xFF, (byte)0xFF, (byte)0xFF};
		ByteArrayInputStream bin = new ByteArrayInputStream(input);
		PALInputStream pin = new PALInputStream(bin);
		PALRequest expected = new PALRequest(Passw.fromString("1234"),
				ClassAccessEnum.Request, CommandEnum.ReadDateTime,
				ByteBuffer.wrap(new byte[] {-1, -1, -1}));
		PALRequest actual = pin.readRequest();
		System.out.println(HEXUtils.toString(input)+" => "+actual);
		Assert.assertEquals(expected, actual);
		pin.close();
	}

	@Test
	public void testReadResponse() throws IOException {
		byte[] input = new byte[] { (byte) 0x53, 0x01, 0x20, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF };
		ByteArrayInputStream bin = new ByteArrayInputStream(input);
		PALInputStream pin = new PALInputStream(bin);
		PALResponse expected = new PALResponse(ClassAccessEnum.Request,
				CommandEnum.ReadDateTime, ByteBuffer.wrap(new byte[] { -1, -1,
						-1 }));
		PALResponse actual = pin.readResponse();
		System.out.println(HEXUtils.toString(input)+" => "+actual);
		Assert.assertEquals(expected, actual);
		pin.close();
	}

	@Test
	public void testReadAddr() throws IOException {
		CommandEnum expected = CommandEnum.ReadLimPwrN;
		
		byte[] input = new byte[] {expected.getAddrH(), expected.getAddrL()};
		ByteArrayInputStream bin = new ByteArrayInputStream(input);
		PALInputStream pin = new PALInputStream(bin);
		
		CommandEnum actual = CommandEnum.byAddr(pin.readAddr());
		System.out.println(HEXUtils.toString(input)+" => "+actual);
		Assert.assertEquals(expected, actual);
		pin.close();
	}

	@Test
	public void testReadPassw() throws IOException {
		byte[] input = new byte[] {(byte)'1',(byte)'2',(byte)'3',(byte)'4'};
		ByteArrayInputStream bin = new ByteArrayInputStream(input);
		PALInputStream pin = new PALInputStream(bin);
		Passw expected = Passw.fromString("1234");
		Passw actual = pin.readPassw();
		System.out.println(HEXUtils.toString(input)+" => "+actual);
		Assert.assertEquals(expected, actual);
		pin.close();
	}

	@Test
	public void testReadServ() throws IOException {
		byte[] input = new byte[] {(byte)0xDF};
		ByteArrayInputStream bin = new ByteArrayInputStream(input);
		PALInputStream pin = new PALInputStream(bin);
		Serv expected = new Serv(Serv.DIRECTION_REQ, ClassAccessEnum.Request, 15);
		Serv actual = pin.readServ();
		System.out.println(HEXUtils.toString(input)+" => "+actual);
		Assert.assertEquals(expected, actual);
		pin.close();
	}

}
