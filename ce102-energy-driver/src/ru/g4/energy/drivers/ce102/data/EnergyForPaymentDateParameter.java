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
