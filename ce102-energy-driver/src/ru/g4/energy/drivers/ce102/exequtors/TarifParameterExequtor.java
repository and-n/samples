package ru.g4.energy.drivers.ce102.exequtors;

public abstract class TarifParameterExequtor extends CurrentParameterExequtor
{
	protected int tarif;

	protected int deep;

	public TarifParameterExequtor(int tarif, int deep)
	{
		super();
		this.tarif = tarif;
		this.deep = deep;
	}

}
