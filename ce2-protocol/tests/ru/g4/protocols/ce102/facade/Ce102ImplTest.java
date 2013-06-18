package ru.g4.protocols.ce102.facade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.g4.phch2.parameters.TCPClientParameters;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.ChannelLevelInputStream;
import ru.g4.protocols.ce102.ChannelLevelOutputStream;
import ru.g4.protocols.ce102.ChannelLevelPacket;
import ru.g4.protocols.ce102.PALInputStream;
import ru.g4.protocols.ce102.PALOutputStream;
import ru.g4.protocols.ce102.PALRequest;
import ru.g4.protocols.ce102.PALResponse;
import ru.g4.utils.HEXUtils;

public class Ce102ImplTest {

    protected Ce102Impl ce;
    private Ce102Emulator emul;

    @BeforeClass
    public static void startEmul() {
    }

    public void stopEmul() {
	emul.interrupt();
    }

    @Before
    public void setUp() throws Exception {
	ce = new Ce102Impl(new TCPClientParameters("192.168.127.254", 4001),
		2575, 0, TimeZone.getDefault(), 100);
    }

    @Test
    public void testInit() throws IOException, AccessException,
	    InterruptedException {
	System.out.println(HEXUtils.toString(ce.init()));
    }

    @Test
    public void testGetConfig() throws IOException, AccessException, InterruptedException {
	ce.init();
	System.out.println(ce.getIntervalLength());
	System.out.println(ce.getFloatPointKoef());
    }

    @Test
    public void testGetDateTime() throws IOException, AccessException, InterruptedException {
	System.out.println(ce.getDateTime());
    }

    @Test
    public void testSetDateTime() throws IOException, AccessException, InterruptedException {
	ce.setDateTime(new Date());
    }

    @Test
    public void testGetPower() throws IOException, AccessException, InterruptedException {
	System.out.println(ce.getPower());
    }

    @Test
    public void testGetTariffValue() throws IOException, AccessException, InterruptedException {
	System.out.println(ce.getTariffValue(0, 0));
    }

    @Test
    public void testGetTariffSumm() throws IOException, AccessException, InterruptedException {
	System.out.println(ce.getTariffSumm(0));
    }

    @Test
    public void testGetIntervalValue() throws IOException, AccessException, InterruptedException {
	System.out.println(Arrays.toString(ce.getIntervalValue(new Date(new Date().getTime() -48*60*60*1000), 0, 1)));
    }

    @Test
    public void testGetPowerLimit() throws IOException, AccessException, InterruptedException {
	System.out.println(ce.getPowerLimit());
    }

    @Test
    public void testSetPowerLimit() throws IOException, AccessException, InterruptedException {
	double b4 = ce.getPowerLimit();
	ce.setPowerLimit(b4+1);
	Assert.assertEquals(b4+1, ce.getPowerLimit());
    }

    public static class Ce102Emulator extends Thread implements Ce102 {
	private final int port;

	public Ce102Emulator(int port) {
	    this.port = port;
	}

	@Override
	public void run() {
	    try {
		ServerSocket ss = new ServerSocket(port);
		while (!interrupted()) {
		    try (Socket s = ss.accept();) {
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			ChannelLevelInputStream cin = new ChannelLevelInputStream(
				in);
			ChannelLevelPacket clp = cin.readPacket();
			PALInputStream pin = new PALInputStream(
				new ByteArrayInputStream(clp.getPacketData()));
			PALRequest req = pin.readRequest();
			PALResponse resp = process(req);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			PALOutputStream pout = new PALOutputStream(bout);
			pout.writePALResponse(resp);
			ChannelLevelPacket packet = new ChannelLevelPacket(
				clp.getAddressS(), clp.getAddressD(), 0x48,
				bout.toByteArray());
			ChannelLevelOutputStream cout = new ChannelLevelOutputStream(
				out);
			cout.writePacket(packet);
			cout.close();
			cin.close();
			pout.close();
			pin.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		ss.close();
	    } catch (IOException e1) {
		e1.printStackTrace();
		return;
	    }
	}

	private PALResponse process(PALRequest req) {
	    return null;
	}

	@Override
	public long init() throws IOException, AccessException,
		InterruptedException {
	    return getConfig();
	}

	@Override
	public long getConfig() {
	    return 0x0f0f0f0f0f0fl;
	}

	@Override
	public Date getDateTime() throws IOException, AccessException,
		InterruptedException {
	    return new Date();
	}

	@Override
	public void setDateTime(Date date) throws IOException, AccessException,
		InterruptedException {
	}

	@Override
	public double getPower() throws IOException, AccessException,
		InterruptedException {
	    return 123.45;
	}

	@Override
	public double getTariffValue(int tariff, int depth) throws IOException,
		AccessException, InterruptedException {
	    return tariff + depth / 100.00;
	}

	@Override
	public double getTariffSumm(int depth) throws IOException,
		AccessException, InterruptedException {
	    return 1000 + depth;
	}

	@Override
	public double[] getIntervalValue(Date date, int num, int count)
		throws IOException, AccessException, InterruptedException {
	    Calendar c = Calendar.getInstance(TimeZone.getDefault());
	    c.setTime(date);
	    double[] res = new double[count];
	    for (int i = 0; i < count; i++)
		res[i] = c.get(Calendar.DAY_OF_MONTH) * 1000 + num + count
			/ 100;
	    return res;
	}

	@Override
	public double getPowerLimit() throws IOException, AccessException,
		InterruptedException {
	    return 666.66;
	}

	@Override
	public void setPowerLimit(double limit) throws IOException,
		AccessException, InterruptedException {
	}

	@Override
	public long getIntervalLength() {
	    // TODO Auto-generated method stub
	    return 0;
	}
    }
}
