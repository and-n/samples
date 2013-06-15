package ru.g4.energy.drivers.ce102.exequtors;

import ru.g4.energy.drivers.util.par.DriverParameter;

public abstract class AbstractTarifParameterExequtor extends AbstractCurrentParameterExequtor
{
	protected int tarif;

	protected int deep;

	public AbstractTarifParameterExequtor(DriverParameter parameter, int tarif, int deep)
	{
		super(parameter);
		this.tarif = tarif;
		this.deep = deep;
	}

}
