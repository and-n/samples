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
 * ������� ������ ����������� ��� ������� ������� ����������.
 *
 */
public abstract class AbstractCurrentParameterExequtor
{
	/**
	 * ��� ������ �������� ����������.
	 */
	protected static final int ERROR_CODE = 0xFF;
	
	/**
	 * ������������� ��������.
	 */
	protected DriverParameter parameter;
	
	public AbstractCurrentParameterExequtor(DriverParameter parameter)
	{
		this.parameter = parameter;
	}
	
	/**
	 * ����������� ������� �� ������ �������� ���������.
	 * @param facade ������ �� ����� ���������.
	 * @param request ��������� � ����������� �������.
	 * @throws EDriverException ������ ���������� �������.
	 */
	public void exequteWriteRequest(Ce102 facade, IWriteRequest request) throws EDriverException
	{
		try
		{
			exequteWrite(facade, request);
		}
		catch (IOException e)
		{
			throw new EDriverException("������ ���������� ������!", e);
		}
		catch (AccessException e)
		{
			throw new EDriverException("������ ������� � ����������", e);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new EDriverException("���� ���� ��������",e);
		}
	}

	/**
	 * ����������� ������� ������ �������� ���������.
	 * @param facade ������ �� ����� ���������.
	 * @param response ���������� ������.
	 * @throws EDriverException ������ ���������� �������.
	 */
	public void exequteRequest(Ce102 facade, IRegularResponse response) throws EDriverException
	{
		try
		{
			exequteRead(facade, response);
		}
		catch (IOException e)
		{
			throw new EDriverException("������ ���������� ������!", e);
		}
		catch (AccessException e)
		{
			throw new EDriverException("������ ������� � ����������", e);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new EDriverException("���� ���� ��������",e);
		}
	}

	/**
	 * ����������� ����� ���������� ������� �� ������.
	 * @param facade ������ �� ����� ���������.
	 * @param response ���������� ������ ����������.
	 * @throws IOException ������ ���������� ������.
	 * @throws AccessException ������ ������� � ����������
	 * @throws InterruptedException ������ ������������ ����������.
	 * @throws EParametersException ������ �������� ���������� �������.
	 */
	protected abstract void exequteRead(Ce102 facade, IRegularResponse response) throws IOException,
			AccessException,
			InterruptedException,EParametersException;
	
	/**
	 * ����������� ����� ���������� ������� �� ������.
	 * @param facade ������ �� ����� ���������.
	 * @param request ��������� � ����������� �������.
	 * @throws IOException ������ ���������� ������.
	 * @throws AccessException ������ ������� � ����������
	 * @throws InterruptedException ������ ������������ ����������.
	 * @throws EParametersException ������ �������� ���������� �������.
	 */
	protected abstract void exequteWrite(Ce102 facade, IWriteRequest request) throws IOException,
			AccessException,
			InterruptedException,EParametersException;
}
