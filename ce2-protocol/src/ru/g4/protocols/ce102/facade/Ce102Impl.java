package ru.g4.protocols.ce102.facade;

import java.io.IOException;
import java.util.Date;

import ru.g4.protocols.ce102.AccessException;

public class Ce102Impl implements Ce102 {

	
	@Override
	public void init() throws IOException, AccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public long getConfig() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDateTime(Date date) throws IOException, AccessException {
		// TODO Auto-generated method stub

	}

	@Override
	public double getPower() throws IOException, AccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTariffValue(int tariff, int depth) throws IOException,
			AccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTariffSumm(int depth) throws IOException, AccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getIntervalValue(Date date) throws IOException,
			AccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPowerLimit() throws IOException, AccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPowerLimit() throws IOException, AccessException {
		// TODO Auto-generated method stub

	}

}
