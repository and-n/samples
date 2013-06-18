package ru.g4.energy.drivers.ce102;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.g4.config.IConfiguration;
import ru.g4.config.ex.EBuildConfigurationException;
import ru.g4.config.xml.XMLConfigBuilder;
import ru.g4.energy.drivers.ce102.data.AbstractArchiveParameter;
import ru.g4.energy.drivers.ce102.data.AccoumulateEnergyParameter;
import ru.g4.energy.drivers.ce102.data.CurrentPowerLimit;
import ru.g4.energy.drivers.ce102.data.CurrentPowerParameter;
import ru.g4.energy.drivers.ce102.data.CurrentTarifEnergy;
import ru.g4.energy.drivers.ce102.data.AbstractCurrentParameter;
import ru.g4.energy.drivers.ce102.data.CurrentTarifSumParameter;
import ru.g4.energy.drivers.ce102.data.EnergyForPaymentDateParameter;
import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
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

/**
 * Драйвер для работы с однофазным электросчётчиком СЕ102.
 * <p>
 * <b> Date</b>: 17.06.2013 10:12:42
 * </p>
 */
public class CE102Driver extends EnergyDriver
{

	@Override
	protected Map<Integer, IParametersManager> init()
	{
		IParametersManager parametersManager = initParameters();
		if (parametersManager == null)
		{
			log.info("parametersManager==null");
			// TODO
			throw new Error("Не удалось инициализировать parametersManager");
		}
		Map<Integer, IParametersManager> parametersMap =
				new HashMap<Integer, IParametersManager>();
		parametersMap.put(parametersManager.getDriverID(), parametersManager);
		return parametersMap;
	}

	/**
	 * Разбирает конфиг с параметрами устройства, создаёт объект-менеджера для
	 * доступа к сигналам.
	 * 
	 * @return менеджер для доступа к параметрам прибора.
	 */
	private CeParametersManager initParameters()
	{
		XMLConfigBuilder builder = new XMLConfigBuilder();
		builder.addDescriptor(CurrentPowerLimit.class);
		builder.addDescriptor(CurrentPowerParameter.class);
		builder.addDescriptor(CurrentTarifEnergy.class);
		builder.addDescriptor(CurrentTarifSumParameter.class);
		builder.addDescriptor(EnergyForPaymentDateParameter.class);
		builder.addDescriptor(AccoumulateEnergyParameter.class);
		builder.addDescriptor(CeParametersManager.class);

		InputStream resIn = CE102Driver.class.getResourceAsStream("config.xml");
		try
		{
			IConfiguration cfg = builder.buildConfiguration(resIn);
			CeParametersManager[] pm =
					cfg.getObjectsByType(CeParametersManager.class);
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
			System.err.println(LoggingUtils.dumpThrowable(e));
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
		long onMeterTime = onMeterTime(request);
		Ce102Factory ceFactory = new Ce102Factory(request.meterInfo().timeZone);
		Ce102 ceFacade =
				ceFactory.getCE102(request.connectionPort(),
						request.meterInfo().address,
						Long.parseLong(request.meterInfo().password));
		long period = ceFacade.getIntervalLength();
		if (period == -1)
		{
			throw new EDriverException("Неизвестный тип периода архивов");
		}
		AbstractArchiveParameter[] params =
				filter(request.parameters(), AbstractArchiveParameter.class);
		log.debug("запрос архивных параметров "+params);
		if (params != null)
		{
			log.debug("запрос архивных параметров в количестве "+params.length);
			for (AbstractArchiveParameter archiveParam : params)
			{
				log.debug("Запрашиваем параметр "+archiveParam);
				archiveParam.getExequtor().exequte(request, response, ceFacade);
			}
		}
		return onMeterTime;
	}

	@Override
	public long onMeterTime(IRequest request) throws EDriverException
	{
		Ce102Factory ceFactory = new Ce102Factory(request.meterInfo().timeZone);
		Ce102 ceFacade =
				ceFactory.getCE102(request.connectionPort(),
						request.meterInfo().address,
						request.meterInfo().password.getBytes());
		try
		{
			return ceFacade.getDateTime().getTime();
		}
		catch (IOException e)
		{
			log.warn("ошибка канального уровня! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка канального уровня!", e);
		}
		catch (AccessException e)
		{
			log.warn("ошибка доступа к устройству! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка доступа к устройству!", e);
		}
		catch (InterruptedException e)
		{
			log.warn("ошибка! \n" + LoggingUtils.dumpThrowable(e));
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
						request.meterInfo().password.getBytes());
		AbstractCurrentParameter[] requestParameters =
				filter(request.parameters(), AbstractCurrentParameter.class);
		for (AbstractCurrentParameter parameter : requestParameters)
		{
			AbstractCurrentParameterExequtor exequtor = parameter.getExequtor();
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
						request.meterInfo().password.getBytes());
		try
		{
			ceFacade.setDateTime(new Date(request.syncTime()));
		}
		catch (IOException e)
		{
			log.warn("ошибка канального уровня! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка канального уровня!", e);
		}
		catch (AccessException e)
		{
			log.warn("ошибка доступа к устройству! \n"
					+ LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка доступа к устройству!", e);
		}
		catch (InterruptedException e)
		{
			log.warn("ошибка! \n" + LoggingUtils.dumpThrowable(e));
			throw new EDriverException("ошибка!", e);
		}
		return onMeterTime(request);
	}

	@Override
	public long onWriteRequest(IWriteRequest request) throws EDriverException
	{
		Ce102Factory ceFactory = new Ce102Factory(request.meterInfo().timeZone);
		Ce102 ceFacade =
				ceFactory.getCE102(request.connectionPort(),
						request.meterInfo().address,
						request.meterInfo().password.getBytes());

		if (request.parameter() instanceof AbstractCurrentParameter)
		{
			AbstractCurrentParameter parameter =
					(AbstractCurrentParameter) request.parameter();
			AbstractCurrentParameterExequtor exequtor = parameter.getExequtor();
			exequtor.exequteWriteRequest(ceFacade, request);
		}
		return onMeterTime(request);
	}

}
