package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;

import ru.g4.energy.drivers.util.exc.EDriverException;
import ru.g4.energy.drivers.util.exc.EParametersException;
import ru.g4.energy.drivers.util.msg.IRegularResponse;
import ru.g4.energy.drivers.util.msg.IWriteRequest;
import ru.g4.energy.drivers.util.par.DriverParameter;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;

/**
 * Базовый объект исполнитель для запроса текущих параметров.
 *
 */
public abstract class AbstractCurrentParameterExequtor
{
	/**
	 * Код ошибки числовых параметров.
	 */
	protected static final int ERROR_CODE = 0xFF;
	
	/**
	 * Запрашиваемый параметр.
	 */
	protected DriverParameter parameter;
	
	public AbstractCurrentParameterExequtor(DriverParameter parameter)
	{
		this.parameter = parameter;
	}
	
	/**
	 * Исполнитель запроса на запись значения параметра.
	 * @param facade ссылка на фасад протокола.
	 * @param request контейнер с параметрами запроса.
	 * @throws EDriverException ошибка выполнения запроса.
	 */
	public void exequteWriteRequest(Ce102 facade, IWriteRequest request) throws EDriverException
	{
		try
		{
			exequteWrite(facade, request);
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

	/**
	 * Исполнитель запроса чтения значения параметра.
	 * @param facade ссылка на фасад протокола.
	 * @param response обработчик ответа.
	 * @throws EDriverException ошибка выполнения запроса.
	 */
	public void exequteRequest(Ce102 facade, IRegularResponse response) throws EDriverException
	{
		try
		{
			exequteRead(facade, response);
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

	/**
	 * Абстрактный метод реализации запроса на чтение.
	 * @param facade ссылка на фасад протокола.
	 * @param response обработчик ответа устройства.
	 * @throws IOException ошибка канального уровня.
	 * @throws AccessException ошибка доступа к устройству
	 * @throws InterruptedException ошибка неожиданного прерывания.
	 * @throws EParametersException ошибка отправки результата запроса.
	 */
	protected abstract void exequteRead(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException,EParametersException;
	
	/**
	 * Абстрактный метод реализации запроса на запись.
	 * @param facade ссылка на фасад протокола.
	 * @param request контейнер с параметрами запроса.
	 * @throws IOException ошибка канального уровня.
	 * @throws AccessException ошибка доступа к устройству
	 * @throws InterruptedException ошибка неожиданного прерывания.
	 * @throws EParametersException ошибка отправки результата запроса.
	 */
	protected abstract void exequteWrite(Ce102 facade, IWriteRequest request) throws IOException,
			AccessException,
			InterruptedException,EParametersException;
}
