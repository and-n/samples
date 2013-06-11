package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;

import ru.g4.energy.drivers.util.exc.EDriverException;
import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;

public abstract class CurrentParameterExequtor
{
	public void exequteRequest(Ce102 facade, IRegularResponse response) throws EDriverException
	{
		try
		{
			exequte(facade, response);
		}
		catch (IOException e)
		{
			throw new EDriverException("Ошибка канального уровня!", e);
		}
		catch (AccessException e)
		{
			throw new EDriverException("Ошибка доступа к устройству", e);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new EDriverException("Нить была прервана",e);
		}
	}

	protected abstract void exequte(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException;
}
