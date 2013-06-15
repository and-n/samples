package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.util.par.RegularParameter;

public abstract class AbstractCurrentParameter extends RegularParameter
{
	public abstract AbstractCurrentParameterExequtor getExequtor();
}
