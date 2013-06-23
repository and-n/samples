package ru.g4.protocols.ce201;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.g4.phch2.TCPClientChannel;
import ru.g4.utils.HEXUtils;

public class StupidSerialChannelFilterTest {

	StupidSerialChannelFilter filter;

	@Before
	public void setUp() throws Exception {
		filter = new StupidSerialChannelFilter(new TCPClientChannel("localhost", 8888));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteByteArrayIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWriteByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckParity() throws ParityCheckException {
		System.out.println(filter.calcParity(0x11));
	}

	@Test
	public void testCalcParity() {
		for (int i = 0; i < 256; i++)
			System.out.println(HEXUtils.toString(i, 1) + " : "
					+ HEXUtils.toString(filter.calcParity(i), 1));
	}

}
