package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.TarifSumParamExequtor;

public class CurrentTarifSumParameter extends AbstractCurrentParameter
{
	@Override
	public AbstractCurrentParameterExequtor getExequtor()
	{
		return new TarifSumParamExequtor(this);
	}
}
