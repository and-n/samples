package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;

import ru.g4.energy.drivers.util.exc.EParametersException;
import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.energy.drivers.util.msg.IWriteRequest;
import ru.g4.energy.drivers.util.par.DriverParameter;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;

/**
 * Исполнитель запроса расхода энергии по тарифам.
 *
 */
public class EnergyParameterExequtor extends AbstractTarifParameterExequtor
{
	
	
	public EnergyParameterExequtor(DriverParameter parameter, int tarif,
			int deep) {
		super(parameter, tarif, deep);
	}

	@Override
	protected void exequteRead(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException, EParametersException
	{
		log.debug("Запрашиваем энергию по тарифу "+tarif);
		double value = facade.getTariffValue(tarif, deep);
		int quality = value==ERROR_CODE ? 0:192;
		log.debug("отправляем значение "+parameter+" value="+value+" quality="+quality);
		response.sendValue(parameter, value, facade.getDateTime().getTime(), quality);
	}

	@Override
	protected void exequteWrite(Ce102 facade, IWriteRequest request)
			throws IOException, AccessException, InterruptedException,
			EParametersException 		
	{
		throw new UnsupportedOperationException();	
	}

	@Override
	public String toString()
	{
		return "EnergyParameterExequtor [tarif=" + tarif + ", deep=" + deep
				+ ", parameter=" + parameter + ", getClass()=" + getClass()
				+ "]";
	}
}
