package ru.g4.protocols.ce102;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ru.g4.utils.HEXUtils;

public class PALOutputStreamTest {

	ByteArrayOutputStream bout;
	PALOutputStream pout;

	@Before
	public void setUp() {
		bout = new ByteArrayOutputStream();
		pout = new PALOutputStream(bout);
	}

	@Test
	public void testWritePALRequest() throws IOException {
		PALRequest req = new PALRequest(257,
				ClassAccessEnum.Request, CommandEnum.ReadDateTime,
				ByteBuffer.wrap(new byte[] {-1, -1, -1}));
		pout.writePALRequest(req);
		System.out.println(req+" => "+HEXUtils.toString(bout.toByteArray()));
		byte[] expect = new byte[] {0x1, 0x1, 0x0, 0x0, (byte)0xD3, 0x01, 0x20, (byte)0xFF, (byte)0xFF, (byte)0xFF};
		Assert.assertTrue(Arrays.equals(expect, bout.toByteArray()));
	}

	@Test
	public void testWritePALResponse() throws IOException {
		PALResponse res = new PALResponse(
				ClassAccessEnum.Request, CommandEnum.ReadDateTime,
				ByteBuffer.wrap(new byte[] {-1, -1, -1}));
		pout.writePALResponse(res);
		System.out.println(res+" => "+HEXUtils.toString(bout.toByteArray()));
		byte[] expect = new byte[] {(byte)0x53, 0x01, 0x20, (byte)0xFF, (byte)0xFF, (byte)0xFF};
		Assert.assertTrue(Arrays.equals(expect, bout.toByteArray()));
	}
	
	@Test
	public void testWriteServ() throws IOException
	{
		Serv s = new Serv(Serv.DIRECTION_REQ, ClassAccessEnum.Request, 15);
		pout.writeServ(s);
		System.out.println(s+" => "+HEXUtils.toString(bout.toByteArray()));
		Assert.assertTrue(Arrays.equals(bout.toByteArray(), new byte[] {(byte)0xDF}));
	}
	
	@Test
	public void testWritePassw() throws IOException
	{
		int pasw = 257;
		pout.writePassw(257);
		System.out.println(pasw+" => "+HEXUtils.toString(bout.toByteArray()));
		Assert.assertTrue(Arrays.equals(bout.toByteArray(), new byte[] {1, 1, 0, 0}));
	}
	
	@Test
	public void testWriteAddr() throws IOException
	{
		CommandEnum c = CommandEnum.ReadLimPwrN;
		pout.writeAddr(c);
		System.out.println(c+"("+HEXUtils.toString(c.getAddr(), 2)+") => "+HEXUtils.toString(bout.toByteArray()));
		Assert.assertTrue(Arrays.equals(bout.toByteArray(), new byte[] {c.getAddrH(), c.getAddrL()}));
	}
}
