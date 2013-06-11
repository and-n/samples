package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.exequtors.CurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.PowerParameterExequtor;

public class CurrentPowerParameter extends CeCurrentParameter
{

	@Override
	public CurrentParameterExequtor getExequtor()
	{
		return new PowerParameterExequtor();
	}

}
