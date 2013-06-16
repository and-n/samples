package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.data.AbstractArchiveParameter;
import ru.g4.energy.drivers.ce102.data.AbstractCurrentParameter;
import ru.g4.energy.drivers.util.exc.EParametersException;
import ru.g4.energy.drivers.util.par.ArchiveParameter;
import ru.g4.energy.drivers.util.par.ParametersManager;
import ru.g4.energy.drivers.util.par.RegularParameter;

/**
 * �������� ���������� ���������� ��102.
 * ������������� ������ ��� ������� � ���������� ����������.
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
			throw new EParametersException("�������������� ������ ��������� �� ���������.");
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
					"�������������� ������ ��������� �� ���������.");
		}
	}
}
