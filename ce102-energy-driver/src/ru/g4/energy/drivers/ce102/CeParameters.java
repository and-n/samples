package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.util.exc.EParametersException;
import ru.g4.energy.drivers.util.par.ArchiveParameter;
import ru.g4.energy.drivers.util.par.ParametersManager;
import ru.g4.energy.drivers.util.par.RegularParameter;

public class CeParameters extends ParametersManager 
{
	public void add(RegularParameter parameter) throws EParametersException
	{
		if(parameter instanceof CeCurrentParameter)
		{
			super.add(parameter);
		}
		else
		{
			throw new EParametersException("Поддерживаются только параметры СЕ протокола.");
		}
	}
	
	public void add(ArchiveParameter parameter) throws EParametersException
	{
		if(parameter instanceof CeArchiveParameter){
			super.add(parameter);
		}
		else
		{
			throw new EParametersException(
					"Поддерживаются только параметры СЕ протокола.");
		}
	}
}
