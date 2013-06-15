package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

public class EnergyForPaymentDateParameter extends AbstractCurrentParameter
{
	private int tarif;

	public int getTarif()
	{
		return tarif;
	}

	public void setTarif(int tarif)
	{
		this.tarif = tarif;
	}

	@Override
	public AbstractCurrentParameterExequtor getExequtor()
	{
		return new EnergyParameterExequtor(this,tarif, 1);
	}

}
