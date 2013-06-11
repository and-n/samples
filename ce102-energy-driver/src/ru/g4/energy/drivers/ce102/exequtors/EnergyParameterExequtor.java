package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;

import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;

public class EnergyParameterExequtor extends TarifParameterExequtor
{

	public EnergyParameterExequtor(int tarif, int deep)
	{
		super(tarif, deep);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void exequte(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException
	{
		// TODO Auto-generated method stub
		
	}

}
