package ru.g4.protocols.ce102;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.g4.utils.HEXUtils;

public class PacketStreamsTest {

	public PacketStreamsTest() {
		// TODO Auto-generated constructor stub
	}
	
	private ChannelLevelInputStream channelIn=null;
	
	private ChannelLevelOutputStream channelOut=null;
	
	private ByteArrayOutputStream out;
	
	@Before
	public void init()
	{
		byte[] packetAsByte=new byte[]{(byte) 0xC0, /* opt */ 0x48,(byte) /* addreD */ 0xDB,(byte) 0xDC,0, /*addrS*/ 5,1, (byte) /* pall  */ 0xDB,(byte) 0xDD,(byte) 0xC1, (byte) /*crc*/ 0x96,(byte) 0xC0};
		System.out.println("шлём="+HEXUtils.toString(packetAsByte));
		ByteArrayInputStream in= new ByteArrayInputStream(packetAsByte);
		channelIn=new ChannelLevelInputStream(in);
		out = new ByteArrayOutputStream();
		channelOut = new ChannelLevelOutputStream(out);
	}
	
	@Test
	public void testReadStream() throws CrcException, IOException
	{
		ChannelLevelPacket packet = new ChannelLevelPacket(0xC0, 261, 0x48, new byte[]{(byte) 0xDB,(byte) 0xC1});
		ChannelLevelPacket packetFromStream= channelIn.readPacket();
		Assert.assertEquals(packetFromStream, packet);
	}
	
	@Test
	public void testWriteStream() throws IOException
	{
		byte[] packetAsByte=new byte[]{(byte) 0xC0, /* opt */ 0x48,(byte) /* addreD */ 0xDB,(byte) 0xDC,0, /*addrS*/ 5,1, (byte) /* pall  */ 0xDB,(byte) 0xDD,(byte) 0xC1, (byte) /*crc*/ 0x96,(byte) 0xC0};
		ChannelLevelPacket packet = new ChannelLevelPacket(0xC0, 261, 0x48, new byte[]{(byte) 0xDB,(byte) 0xC1});
		channelOut.writePacket(packet);
		System.out.println("out= "+ HEXUtils.toString( out.toByteArray()));
		System.out.println("true="+ HEXUtils.toString( packetAsByte));;
		Assert.assertArrayEquals(packetAsByte,out.toByteArray());
	}
	
	@Test
	public void testAll() throws IOException
	{
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ChannelLevelOutputStream out = new ChannelLevelOutputStream(byteOut);
		ChannelLevelPacket packet = new ChannelLevelPacket(0xC0, 261, 0x48, new byte[]{(byte) 0xDB,(byte) 0xC1});
		out.writePacket(packet);
		ChannelLevelInputStream in = new ChannelLevelInputStream(new ByteArrayInputStream(byteOut.toByteArray()));
		ChannelLevelPacket readedPacket= in.readPacket();
		Assert.assertEquals(packet, readedPacket);
	}

}
