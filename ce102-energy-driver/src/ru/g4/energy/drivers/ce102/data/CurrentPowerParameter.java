package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.PowerParameterExequtor;

/**
 * Класс описатель для текущего параметра текущая мощность.
 */
public class CurrentPowerParameter extends AbstractCurrentParameter
{

	@Override
	public AbstractCurrentParameterExequtor getExequtor()
	{
		return new PowerParameterExequtor(this);
	}

	@Override
	public String toString()
	{
		return "CurrentPowerParameter [getExequtor()=" + getExequtor().getClass()
				+ ", isWriteable()=" + isWriteable() + ", getNumber()="
				+ getNumber() + ", getInner()=" + getInner() + "]";
	}

}
