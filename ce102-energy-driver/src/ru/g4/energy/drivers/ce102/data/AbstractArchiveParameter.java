package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractArchiveParameterExequtor;
import ru.g4.energy.drivers.util.par.ArchiveParameter;

/**
 * ������� ����� �������� ���������� ��102.
 *
 */
public abstract class AbstractArchiveParameter extends ArchiveParameter 
{	
	/**
	 * ����� ������� � ������-����������� ������� ��������� � ����������.
	 * @return ������-����������� �������.
	 */
	public abstract AbstractArchiveParameterExequtor getExequtor();
}
