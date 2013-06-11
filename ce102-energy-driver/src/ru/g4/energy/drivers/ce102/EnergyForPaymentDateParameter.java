package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.exequtors.CurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

public class EnergyForPaymentDateParameter extends CeCurrentParameter
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
	public CurrentParameterExequtor getExequtor()
	{
		return new EnergyParameterExequtor(tarif, 1);
	}

}
