package ru.g4.protocols.ce102.facade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ru.g4.phch2.IPhysicalChannel;
import ru.g4.phch2.error.ENotConnectedException;
import ru.g4.phch2.factory.ChannelParameters;
import ru.g4.phch2.factory.PhysicalChannelManager;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.ChannelLevelInputStream;
import ru.g4.protocols.ce102.ChannelLevelOutputStream;
import ru.g4.protocols.ce102.ChannelLevelPacket;
import ru.g4.protocols.ce102.ClassAccessEnum;
import ru.g4.protocols.ce102.CommandEnum;
import ru.g4.protocols.ce102.DataTypesInputStream;
import ru.g4.protocols.ce102.DataTypesOutputStream;
import ru.g4.protocols.ce102.PALInputStream;
import ru.g4.protocols.ce102.PALOutputStream;
import ru.g4.protocols.ce102.PALRequest;
import ru.g4.protocols.ce102.PALResponse;
import ru.g4.protocols.ce102.Passw;
import ru.g4.protocols.ce102.Serv;
import ru.g4.utils.HEXUtils;

public class Ce102Impl implements Ce102 {

	private final ChannelParameters<?> params;
	private final int address;
	private long timeout = 1000;
	private final String password;
	private final TimeZone tz;
	
	private long config = -1;
	
	public Ce102Impl(ChannelParameters<?> params, int address, String passw, TimeZone tz)
	{
		this.params = params;
		this.address = address;
		this.password = passw;
		this.tz = tz;
	}
	
	public Ce102Impl(ChannelParameters<?> params, int address, String passw, TimeZone tz, long timeout)
	{
		if (params == null)
			throw new NullPointerException("channel params is null");
		this.params = params;
		this.address = address;
		this.timeout = timeout;
		this.password = passw;
		this.tz = tz;
	}
	
	public ChannelParameters<?> getChannelParameters()
	{
		return params;
	}
	
	public int getAddress()
	{
		return address;
	}
	
	public long getTimeout()
	{
		return timeout;
	}
	
	public TimeZone getTimeZone()
	{
		return tz;
	}
	
	@Override
	public long init() throws IOException, AccessException, InterruptedException {
		byte[] buf = requestPal(CommandEnum.ReadÐConfig, new byte[] {});
		buf = Arrays.copyOf(buf, 8);
		this.config = HEXUtils.longFromBytesLH(buf);
		return this.config;
	}
	
	@Override
	public long getConfig() {
		return config;
	}
	
	@Override
	public long getIntervalLength() {
	    switch ((int)(getConfig()>>8) & 0x03) {
	    case 0:
		return 60*60*1000;
	    case 1:
		return 30*60*1000;
	    case 2:
		return 15*60*1000;
	    default:
		return -1;
	    }
	}

	@SuppressWarnings("resource")
	@Override
	public Date getDateTime() throws IOException, AccessException, InterruptedException {
		byte[] buf = requestPal(CommandEnum.ReadDateTime, new byte[] {});
		DataTypesInputStream din = new DataTypesInputStream(new ByteArrayInputStream(buf));
		Calendar c = Calendar.getInstance(getTimeZone());
		c.set(Calendar.SECOND, din.readBCD());
		c.set(Calendar.MINUTE, din.readBCD());
		c.set(Calendar.HOUR_OF_DAY, din.readBCD());
		c.set(Calendar.DAY_OF_WEEK, din.readBCD());
		c.set(Calendar.DAY_OF_MONTH, din.readBCD());
		c.set(Calendar.MONTH, din.readBCD());
		c.set(Calendar.YEAR, din.readBCD()+2000);
		return c.getTime();
	}

	@SuppressWarnings("resource")
	@Override
	public void setDateTime(Date date) throws IOException, AccessException, InterruptedException {
		Calendar c = Calendar.getInstance(getTimeZone());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataTypesOutputStream dout = new DataTypesOutputStream(bout);
		dout.writeBCD(c.get(Calendar.SECOND));
		dout.writeBCD(c.get(Calendar.MINUTE));
		dout.writeBCD(c.get(Calendar.HOUR_OF_DAY));
		dout.writeBCD(c.get(Calendar.DAY_OF_WEEK));
		dout.writeBCD(c.get(Calendar.DAY_OF_MONTH));
		dout.writeBCD(c.get(Calendar.MONTH));
		dout.writeBCD(c.get(Calendar.YEAR)-2000);
		requestPal(CommandEnum.WriteDateTime, bout.toByteArray());
	}
	
	public double getFloatPointKoef()
	{
		return new BigDecimal(1).scaleByPowerOfTen(-1*((int)getConfig() & 0x03)).doubleValue();
	}

	@SuppressWarnings("resource")
	@Override
	public double getPower() throws IOException, AccessException, InterruptedException {
		byte[] buf = requestPal(CommandEnum.ReadPower, new byte[] {});
		DataTypesInputStream din = new DataTypesInputStream(new ByteArrayInputStream(buf));
		return din.readData3()*getFloatPointKoef();
	}

	@SuppressWarnings("resource")
	@Override
	public double getTariffValue(int tariff, int depth) throws IOException,
			AccessException, InterruptedException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataTypesOutputStream dout = new DataTypesOutputStream(bout);
		dout.writeUINT8(tariff);
		dout.writeUINT8(depth);
		byte[] buf = requestPal(CommandEnum.ReadTariffValue, bout.toByteArray());
		ByteArrayInputStream bin = new ByteArrayInputStream(buf);
		DataTypesInputStream din = new DataTypesInputStream(bin);
		return din.readData4()*getFloatPointKoef();
	}

	@SuppressWarnings("resource")
	@Override
	public double getTariffSumm(int depth) throws IOException, AccessException, InterruptedException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataTypesOutputStream dout = new DataTypesOutputStream(bout);
		dout.writeUINT8(depth);
		byte[] buf = requestPal(CommandEnum.ReadTariffSum, bout.toByteArray());
		ByteArrayInputStream bin = new ByteArrayInputStream(buf);
		DataTypesInputStream din = new DataTypesInputStream(bin);
		return din.readData4()*getFloatPointKoef();
	}

	@SuppressWarnings("resource")
	@Override
	public double[] getIntervalValue(Date date, int num, int count) throws IOException,
			AccessException, InterruptedException {
		if (count < 1 || count > 4)
				throw new IllegalArgumentException("count("+count+") must be in [1, 4]");
		Calendar c = Calendar.getInstance(getTimeZone());
		c.setTime(date);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataTypesOutputStream dout = new DataTypesOutputStream(bout);
		dout.writeBCD(c.get(Calendar.DAY_OF_MONTH));
		dout.writeBCD(c.get(Calendar.MONTH));
		dout.writeBCD(c.get(Calendar.YEAR)-2000);
		dout.writeBCD(num);
		dout.writeBCD(count);
		byte[] buf = requestPal(CommandEnum.ReadIntervalEnergy, bout.toByteArray());
		ByteArrayInputStream bin = new ByteArrayInputStream(buf);
		DataTypesInputStream din = new DataTypesInputStream(bin);
		double[] doubles = new double[count];
		for (int i = 0; i < count; i++)
			doubles[i] = din.readData3()*getFloatPointKoef();
		return doubles;
	}

	@SuppressWarnings("resource")
	@Override
	public double getPowerLimit() throws IOException, AccessException, InterruptedException {
		byte[] buf = requestPal(CommandEnum.ReadLimPwrN, new byte[0]);
		ByteArrayInputStream bin = new ByteArrayInputStream(buf);
		DataTypesInputStream din = new DataTypesInputStream(bin);
		return din.readData3()*getFloatPointKoef();
	}

	@SuppressWarnings("resource")
	@Override
	public void setPowerLimit(double limit) throws IOException, AccessException, InterruptedException {
		int val= new Double(limit/getFloatPointKoef()).intValue();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataTypesOutputStream dout = new DataTypesOutputStream(bout);
		dout.writeData3(val);
		requestPal(CommandEnum.WriteLimPwrN, bout.toByteArray());
	}

	protected byte[] requestPal(CommandEnum cmd, byte[] data) throws IOException, AccessException, InterruptedException
	{
		IPhysicalChannel ch = PhysicalChannelManager.getInstance().captureChannel(getChannelParameters());
		try
		{
			ch.open();
			if (!ch.waitConnection(getTimeout()))
					throw new ENotConnectedException();
			PALRequest req = new PALRequest(Passw.fromString(password), 
					ClassAccessEnum.Request, cmd, ByteBuffer.wrap(data));
			PALResponse resp = sendAndWait(req, ch);
			throwAccess(resp.getServ());
			return resp.getData().array();
		}
		finally
		{
			PhysicalChannelManager.getInstance().releaseChannel(ch);
		}
	}

	private void throwAccess(Serv serv) throws AccessException {
		if (serv.getAccess() == ClassAccessEnum.AccessError)
		{
			throw new AccessException();
		}
	}

	@SuppressWarnings("resource")
	private PALResponse sendAndWait(PALRequest req, IPhysicalChannel ch) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		PALOutputStream pout = new PALOutputStream(bout);
		pout.writePALRequest(req);
		pout.close();
		ChannelLevelOutputStream cout = new ChannelLevelOutputStream(ch.out());
		ChannelLevelInputStream cin = new ChannelLevelInputStream(ch.in());
		cout.writePacket(new ChannelLevelPacket(getAddress(), 0, 0x48, bout.toByteArray()));
		cout.close();
		ChannelLevelPacket chPack = cin.readPacket();
		if (chPack.getAddressD() != 0 || chPack.getAddressS() != getAddress())
			throw new IOException("addressD <> addressS");
		PALInputStream pin = new PALInputStream(new ByteArrayInputStream(chPack.getPacketData()));
		PALResponse resp = pin.readResponse();
		return resp;
	}
}
