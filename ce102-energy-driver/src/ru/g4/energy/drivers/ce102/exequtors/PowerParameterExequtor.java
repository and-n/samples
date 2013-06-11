package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;

import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;

public class PowerParameterExequtor extends CurrentParameterExequtor
{

	@Override
	protected void exequte(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException
	{
		// TODO Auto-generated method stub

	}

}
