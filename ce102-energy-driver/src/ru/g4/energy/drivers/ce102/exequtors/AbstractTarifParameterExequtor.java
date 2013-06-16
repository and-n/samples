package ru.g4.energy.drivers.ce102.exequtors;

import ru.g4.energy.drivers.util.par.DriverParameter;

/**
 * Базовый объект исполнитель для запроса текущих параметров по тарифам.
 *
 */
public abstract class AbstractTarifParameterExequtor extends AbstractCurrentParameterExequtor
{
	/**
	 * Номер тарифа для которого выполняется запрос.
	 */
	protected int tarif;

	/**
	 * Глубина запроса. 0-текущие данные. 1-на расчётный день месяца.
	 */
	protected int deep;

	public AbstractTarifParameterExequtor(DriverParameter parameter, int tarif, int deep)
	{
		super(parameter);
		this.tarif = tarif;
		this.deep = deep;
	}

}
