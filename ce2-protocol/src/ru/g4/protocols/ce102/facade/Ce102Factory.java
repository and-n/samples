package ru.g4.protocols.ce102.facade;

import java.util.TimeZone;

import ru.g4.phch2.factory.ChannelParameters;

public class Ce102Factory {
	
	private long timeout = 1000;
	private TimeZone tz = TimeZone.getDefault();
	
	public Ce102Factory(long timeout, TimeZone tz) {
		super();
		this.timeout = timeout;
		this.tz = tz;
	}
	
	public Ce102Factory(TimeZone tz) {
		super();
		this.tz = tz;
	}
	
	public Ce102Factory(long timeout) {
		super();
		this.timeout = timeout;
	}

	public Ce102 getCE102(final ChannelParameters<?> params, final int address, final long password) 
	{
		return new Ce102Impl(params, address, password, tz, timeout);
	}
}
