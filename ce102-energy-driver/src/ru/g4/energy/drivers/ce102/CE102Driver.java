package ru.g4.energy.drivers.ce102;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.g4.config.IConfiguration;
import ru.g4.config.ex.EBuildConfigurationException;
import ru.g4.config.xml.XMLConfigBuilder;
import ru.g4.energy.drivers.ce102.exequtors.CurrentParameterExequtor;
import ru.g4.energy.drivers.util.EnergyDriver;
import ru.g4.energy.drivers.util.exc.EDriverException;
import ru.g4.energy.drivers.util.msg.IArchiveRequest;
import ru.g4.energy.drivers.util.msg.IArchiveResponse;
import ru.g4.energy.drivers.util.msg.IRegularRequest;
import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.energy.drivers.util.msg.IRequest;
import ru.g4.energy.drivers.util.msg.ISyncTimeRequest;
import ru.g4.energy.drivers.util.msg.IWriteRequest;
import ru.g4.energy.drivers.util.par.IParametersManager;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;
import ru.g4.protocols.ce102.facade.Ce102Factory;
import ru.g4.utils.log.LoggingUtils;

public class CE102Driver extends EnergyDriver
{

	@Override
	protected Map<Integer, IParametersManager> init()
	{
		IParametersManager parametersManager = initParameters();
		if (parametersManager == null)
		{
			// TODO
			// throw new
		}
		Map<Integer, IParametersManager> parametersMap =
				new HashMap<Integer, IParametersManager>();
		parametersMap.put(parametersManager.getDriverID(), parametersManager);
		return parametersMap;
	}

	private CeParameters initParameters()
	{
		XMLConfigBuilder builder = new XMLConfigBuilder();
		builder.addDescriptor(CeArchiveParameter.class);
		builder.addDescriptor(CeCurrentParameter.class);
		builder.addDescriptor(CeCurrentEnergy.class);

		InputStream resIn = CE102Driver.class.getResourceAsStream("config.xml");
		try
		{
			IConfiguration cfg = builder.buildConfiguration(resIn);
			CeParameters[] pm = cfg.getObjectsByType(CeParameters.class);
			if (pm.length > 0)
			{
				return pm[0];
			}
			else
			{
				System.err.println("Не найдена конфигурация для CE102");

			}
		}
		catch (EBuildConfigurationException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected String getDriverName()
	{
		return "ce-102";
	}

	@Override
	public long onArchive(IArchiveRequest request, IArchiveResponse response) throws EDriverException
	{
		
		return 0;
	}

	@Override
	public long onMeterTime(IRequest request) throws EDriverException
	{
		Ce102Factory ceFactory = new Ce102Factory(request.meterInfo().timeZone);
		Ce102 ceFacade =
				ceFactory.getCE102(request.connectionPort(),
						request.meterInfo().address,
						request.meterInfo().password);
		try
		{
			return ceFacade.getDateTime().getTime();
		}
		catch (IOException e)
		{
			log.warning("ошибка канального уровня! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка канального уровня!", e);
		}
		catch (AccessException e)
		{
			log.warning("ошибка доступа к устройству! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка доступа к устройству!", e);
		}
		catch (InterruptedException e)
		{
			log.warning("ошибка! \n" + LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка!", e);
		}
	}

	@Override
	public long onRegular(IRegularRequest request, IRegularResponse response) throws EDriverException
	{
		long result = onMeterTime(request);
		Ce102Factory ceFactory = new Ce102Factory(request.meterInfo().timeZone);
		Ce102 ceFacade =
				ceFactory.getCE102(request.connectionPort(),
						request.meterInfo().address,
						request.meterInfo().password);
		CeCurrentParameter[] requestParameters =
				filter(request.parameters(), CeCurrentParameter.class);
		for(CeCurrentParameter parameter:requestParameters)
		{
			CurrentParameterExequtor exequtor = parameter.getExequtor();
			exequtor.exequteRequest(ceFacade, response);
		}
		return result;
	}

	@Override
	public long onSyncTime(ISyncTimeRequest request) throws EDriverException
	{
		Ce102Factory ceFactory = new Ce102Factory(request.meterInfo().timeZone);
		Ce102 ceFacade =
				ceFactory.getCE102(request.connectionPort(),
						request.meterInfo().address,
						request.meterInfo().password);
		try
		{
			ceFacade.setDateTime(new Date(request.syncTime()));
		}
		catch (IOException e)
		{
			log.warning("ошибка канального уровня! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка канального уровня!", e);
		}
		catch (AccessException e)
		{
			log.warning("ошибка доступа к устройству! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка доступа к устройству!", e);
		}
		catch (InterruptedException e)
		{
			log.warning("ошибка! \n" + LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка!", e);
		}
		return onMeterTime(request);
	}

	@Override
	public long onWriteRequest(IWriteRequest request) throws EDriverException
	{
		
		return 0;
	}

}
