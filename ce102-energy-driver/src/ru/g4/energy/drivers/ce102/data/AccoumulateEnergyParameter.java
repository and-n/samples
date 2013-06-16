package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractArchiveParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.AccomulateEnergyExequtor;

/**
 * Класс описатель для архивного параметра накопленной энергии за период.
 */
public class AccoumulateEnergyParameter extends AbstractArchiveParameter {

	@Override
	public AbstractArchiveParameterExequtor getExequtor() {
		return new AccomulateEnergyExequtor(this);
	}

}
