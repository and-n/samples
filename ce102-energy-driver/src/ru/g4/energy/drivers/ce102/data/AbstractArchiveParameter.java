package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractArchiveParameterExequtor;
import ru.g4.energy.drivers.util.par.ArchiveParameter;

public abstract class AbstractArchiveParameter extends ArchiveParameter 
{	
	public abstract AbstractArchiveParameterExequtor getExequtor();
}
