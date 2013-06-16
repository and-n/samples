package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.util.par.RegularParameter;

/**
 * ������� ����� ��� �������(�� ��������) ���������� ��102.
 */
public abstract class AbstractCurrentParameter extends RegularParameter
{
	/**
	 * ����� ������� � ������-����������� �������/������ ��������� � ����������.
	 * @return ������-����������� �������.
	 */
	public abstract AbstractCurrentParameterExequtor getExequtor();
}
