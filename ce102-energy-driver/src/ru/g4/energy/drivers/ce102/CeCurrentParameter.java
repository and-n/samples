package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.exequtors.CurrentParameterExequtor;
import ru.g4.energy.drivers.util.par.RegularParameter;

public abstract class CeCurrentParameter extends RegularParameter
{
	public abstract CurrentParameterExequtor getExequtor();
}
