package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

/**
 * ����� ��������� ��� �������� ��������� ����� ������� ������� �� ������� 
 * �� ��������� ���� ������.
 */
public class EnergyForPaymentDateParameter extends AbstractCurrentParameter
{
	/**
	 * ����� ������, ��� �������� ������������� �������.
	 */
	private int tarifNumber;

	

	public int getTarifNumber()
	{
		return tarifNumber;
	}

	public void setTarifNumber(int tarifNumber)
	{
		this.tarifNumber = tarifNumber;
	}

	@Override
	public AbstractCurrentParameterExequtor getExequtor()
	{
		return new EnergyParameterExequtor(this,tarifNumber, 1);
	}

	@Override
	public String toString()
	{
		return "EnergyForPaymentDateParameter [tarif=" + tarifNumber
				+ ", getExequtor()=" + getExequtor().getClass() + ", isWriteable()="
				+ isWriteable() + ", getNumber()=" + getNumber()
				+ ", getInner()=" + getInner() + "]";
	}
	
}
