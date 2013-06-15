package ru.g4.energy.drivers.ce102.exequtors;

import ru.g4.energy.drivers.ce102.data.AbstractArchiveParameter;
import ru.g4.energy.drivers.util.exc.EDriverException;
import ru.g4.energy.drivers.util.msg.IArchiveRequest;
import ru.g4.energy.drivers.util.msg.IArchiveResponse;
import ru.g4.protocols.ce102.facade.Ce102;

public abstract class AbstractArchiveParameterExequtor
{
	protected AbstractArchiveParameter param;
	
	public AbstractArchiveParameterExequtor(AbstractArchiveParameter param) {
		super();
		this.param = param;
	}

	public abstract void exequte(IArchiveRequest request,
			IArchiveResponse response,
			Ce102 facadem) throws EDriverException;
}
