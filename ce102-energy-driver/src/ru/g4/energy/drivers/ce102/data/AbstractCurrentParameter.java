package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.util.par.RegularParameter;

/**
 * Базовый класс для текущих(не архивных) параметров се102.
 */
public abstract class AbstractCurrentParameter extends RegularParameter
{
	/**
	 * Метод доступа к обекту-исполнителю запроса/записи параметра с устройства.
	 * @return объект-исполнитель запроса.
	 */
	public abstract AbstractCurrentParameterExequtor getExequtor();
}
