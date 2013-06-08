package ru.g4.protocols.ce102.facade;
import java.io.IOException;
import java.util.Date;

import ru.g4.protocols.ce102.AccessException;


public interface Ce102 {
	public long init() throws IOException, AccessException, InterruptedException;
	public long getConfig();
	public Date getDateTime() throws IOException, AccessException, InterruptedException;
	public void setDateTime(Date date) throws IOException, AccessException, InterruptedException;
	public double getPower() throws IOException, AccessException;
	public double getTariffValue(int tariff, int depth) throws IOException, AccessException;
	public double getTariffSumm(int depth) throws IOException, AccessException;
	public double getIntervalValue(Date date) throws IOException, AccessException;
	public double getPowerLimit() throws IOException, AccessException;
	public void setPowerLimit() throws IOException, AccessException;
}
