package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractArchiveParameterExequtor;
import ru.g4.energy.drivers.util.par.ArchiveParameter;

/**
 * Базовый класс архивных параметров се102.
 *
 */
public abstract class AbstractArchiveParameter extends ArchiveParameter 
{	
	/**
	 * Метод доступа к обекту-исполнителю запроса параметра с устройства.
	 * @return объект-исполнитель запроса.
	 */
	public abstract AbstractArchiveParameterExequtor getExequtor();
}
