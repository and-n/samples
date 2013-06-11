package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.exequtors.CurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.TarifSumParamExequtor;

public class CeCurrentTarifSumParameter extends CeCurrentParameter
{
	@Override
	public CurrentParameterExequtor getExequtor()
	{
		return new TarifSumParamExequtor();
	}
}
