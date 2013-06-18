package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;

import ru.g4.energy.drivers.util.exc.EParametersException;
import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.energy.drivers.util.msg.IWriteRequest;
import ru.g4.energy.drivers.util.par.DriverParameter;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;

/**
 * Исполнитель запросов параметра лимит мощности.
 */
public class PowerLimitExequtor extends AbstractCurrentParameterExequtor
{

	public PowerLimitExequtor(DriverParameter parameter)
	{
		super(parameter);
	}

	@Override
	protected void exequteRead(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException,
			EParametersException
	{
		log.debug("Запрашиваем лимит мощности");
		double value = facade.getPowerLimit();
		int quality = value == ERROR_CODE ? 0 : 192;
		log.debug("отправляем значение "+parameter+" value="+value+" quality="+quality);
		response.sendValue(parameter, value, facade.getDateTime().getTime(),
				quality);
	}

	@Override
	protected void exequteWrite(Ce102 facade, IWriteRequest request) throws IOException,
			AccessException,
			InterruptedException,
			EParametersException
	{
		log.debug("Устанавливаем лимит мощности request="+request);
		facade.setPowerLimit(((Number) request.value()).doubleValue());
	}

	@Override
	public String toString()
	{
		return "PowerLimitExequtor [parameter=" + parameter + ", getClass()="
				+ getClass() + "]";
	}

}
