package ru.g4.energy.drivers.ce102.exequtors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import ru.g4.energy.drivers.ce102.data.AbstractArchiveParameter;
import ru.g4.energy.drivers.util.exc.EDriverException;
import ru.g4.energy.drivers.util.msg.IArchiveRequest;
import ru.g4.energy.drivers.util.msg.IArchiveResponse;
import ru.g4.protocols.ce102.AccessException;
import ru.g4.protocols.ce102.facade.Ce102;
import ru.g4.utils.log.LoggingUtils;

/**
 * »сполнитель запроса архивного значени€ накопленной энергии.
 */
public class AccomulateEnergyExequtor extends AbstractArchiveParameterExequtor
{

	public AccomulateEnergyExequtor(AbstractArchiveParameter param)
	{
		super(param);
	}

	/**
	 *  од ошибочного значени€, полученного с прибора.
	 */
	private static final int ERROR_CODE = 0xFF;

	@Override
	public void exequte(IArchiveRequest request,
			IArchiveResponse response,
			Ce102 facade) throws EDriverException
	{
		long period = facade.getIntervalLength();
		if (period == -1)
		{
			throw new EDriverException("Ќеизвестный тип периода архивов");
		}
		log.debug("ѕреобразуем параметры запроса архива");
		List<RequestParameters> list =
				getRequestParams(request, facade, period);
		log.debug("—оздали список параметров запроса в терминах се102 \n"
				+ list);
		for (RequestParameters requestParams : list)
		{
			try
			{
				double[] values =
						facade.getIntervalValue(requestParams.getDate(),
								requestParams.getNum(),
								requestParams.getCount());
				for (int i = 0; i < values.length; i++)
				{
					int quality = values[i] == ERROR_CODE ? 0 : 192;
					long timeStamp =
							requestParams.getDate().getTime() + period * i;
					log.info("отправл€ем параметр param=" + param + " val="
							+ values[i]);
					response.sendArchive(param, values[i], timeStamp, quality);
				}
			}
			catch (IOException e)
			{
				throw new EDriverException("ќшибка канального уровн€!", e);
			}
			catch (AccessException e)
			{
				throw new EDriverException("ќшибка доступа к устройству", e);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new EDriverException("Ќить была прервана", e);
			}
		}
	}

	/**
	 * ѕреобразует параметры запроса в терминах energy-node в параметры в
	 * терминах протокола.
	 * 
	 * @param request контейнер с параметрами запроса архива.
	 * @param facade ссылка на фасад протокола.
	 * @param period период хранени€ данных в устройстве.
	 * @return список параметров запроса в терминах устройства.
	 * @throws EDriverException
	 */
	private List<RequestParameters> getRequestParams(IArchiveRequest request,
			Ce102 facade,
			long period) throws EDriverException
	{
		List<RequestParameters> result = new ArrayList<>();
		Calendar predDate = null;
		Enumeration<Long> archiveDates =
				request.byPeriod(period, request.meterInfo().timeZone);
		Date requestDate = null;
		int count = 0;
		for (Long date = archiveDates.nextElement(); archiveDates
				.hasMoreElements(); date = archiveDates.nextElement())
		{
			Calendar curDate = Calendar.getInstance();
			curDate.setTimeInMillis(date);
			if (predDate == null)
			{
				requestDate = curDate.getTime();
				predDate = Calendar.getInstance();
				predDate.setTimeInMillis(date);
				continue;
			}
			if (predDate.get(Calendar.DAY_OF_MONTH) != curDate
					.get(Calendar.DAY_OF_MONTH) || count == 4)
			{
				result.add(createParamsObject(requestDate, count, period));
				requestDate = curDate.getTime();
				count = 0;
			}
			else
			{
				count++;
			}
			predDate = Calendar.getInstance();
			predDate.setTimeInMillis(date);
		}
		return result;
	}

	/**
	 * —оздаЄт контейнер с параметрами дл€ одного запроса к устройству.
	 * 
	 * @param date дата за которую запршивают параметры.
	 * @param count количество запрашиваемых записей.
	 * @param period период хранени€ данных в устройстве.
	 * @return параметры запроса архива к устройству
	 */
	private RequestParameters createParamsObject(Date date,
			int count,
			long period)
	{
		Calendar dayStart = Calendar.getInstance();
		dayStart.setTime(date);
		dayStart.set(Calendar.HOUR_OF_DAY, 0);
		dayStart.set(Calendar.MINUTE, 0);
		dayStart.set(Calendar.SECOND, 0);
		dayStart.set(Calendar.MILLISECOND, 0);
		int num =
				(int) ((date.getTime() - dayStart.getTimeInMillis()) / period);
		log.debug("создаЄм RequestParameters date="
				+ LoggingUtils.getNewStandartFormatter().format(date) + " num="
				+ num + " count=" + count);
		return new RequestParameters(date, num, count);
	}

	/**
	 *  ласс кнтейнер параметров одного запроса к устройству.
	 */
	private class RequestParameters
	{
		/**
		 * ƒата за которую запрашиваетс€ значение.
		 */
		Date date;

		/**
		 *  оличество периодов от начала суток. (Ќапример номер получасовки от
		 * начала суток)
		 */
		int num;

		/**
		 *  оличество считываемых подр€д измерений. Ќе больше 4.
		 */
		int count;

		public Date getDate()
		{
			return date;
		}

		public int getNum()
		{
			return num;
		}

		public int getCount()
		{
			return count;
		}

		public RequestParameters(Date date, int num, int count)
		{
			super();
			this.date = date;
			this.num = num;
			this.count = count;
		}

	}

	@Override
	public String toString()
	{
		return "AccomulateEnergyExequtor [param=" + param + ", getClass()="
				+ getClass() + "]";
	}

}
