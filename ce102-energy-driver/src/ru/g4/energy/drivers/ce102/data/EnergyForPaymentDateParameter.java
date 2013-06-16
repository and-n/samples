package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

/**
 * Класс описатель для текущего параметра сумма расхода энергии по тарифам 
 * на расчётный день месяца.
 */
public class EnergyForPaymentDateParameter extends AbstractCurrentParameter
{
	/**
	 * Номер тарифа, для которого запрашивается энергия.
	 */
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
