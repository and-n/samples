package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractArchiveParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.AccomulateEnergyExequtor;

public class AccoumulateEnergyParameter extends AbstractArchiveParameter {

	@Override
	public AbstractArchiveParameterExequtor getExequtor() {
		return new AccomulateEnergyExequtor(this);
	}

}
