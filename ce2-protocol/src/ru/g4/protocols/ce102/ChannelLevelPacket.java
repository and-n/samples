package ru.g4.protocols.ce102;

import java.util.Arrays;

public class ChannelLevelPacket {
	
	private int addressD;

	private int addressS;
	
	private int opt;
	
	private byte[] packetData;

	public ChannelLevelPacket(int addressD, int addressS,int opt, byte[] packetData) {
		super();
		this.addressD = addressD;
		this.addressS = addressS;
		this.packetData = packetData;
		this.opt=opt;
	}

	public int getAddressD() {
		return addressD;
	}

	public int getAddressS() {
		return addressS;
	}

	public byte[] getPacketData() {
		return packetData;
	}
	
	public int getOpt()
	{
		return opt;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + addressD;
		result = prime * result + addressS;
		result = prime * result + opt;
		result = prime * result + Arrays.hashCode(packetData);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelLevelPacket other = (ChannelLevelPacket) obj;
		if (addressD != other.addressD)
			return false;
		if (addressS != other.addressS)
			return false;
		if (opt != other.opt)
			return false;
		if (!Arrays.equals(packetData, other.packetData))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ChannelLevelPacket [addressD=" + addressD + ", addressS="
				+ addressS +", opt="+ opt + ", packetData=" + Arrays.toString(packetData)
				+ "]";
	}
}
