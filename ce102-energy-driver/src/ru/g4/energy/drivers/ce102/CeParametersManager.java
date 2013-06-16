package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.data.AbstractArchiveParameter;
import ru.g4.energy.drivers.ce102.data.AbstractCurrentParameter;
import ru.g4.energy.drivers.util.exc.EParametersException;
import ru.g4.energy.drivers.util.par.ArchiveParameter;
import ru.g4.energy.drivers.util.par.ParametersManager;
import ru.g4.energy.drivers.util.par.RegularParameter;

/**
 * Менеджер параметров устройства се102.
 * Предоставляет методы для доступа к параметрам устройства.
 */
public class CeParametersManager extends ParametersManager 
{
	
	public void add(RegularParameter parameter) throws EParametersException
	{
		if(parameter instanceof AbstractCurrentParameter)
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
		if(parameter instanceof AbstractArchiveParameter){
			super.add(parameter);
		}
		else
		{
			throw new EParametersException(
					"Поддерживаются только параметры СЕ протокола.");
		}
	}
}
