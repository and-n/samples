package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.PowerLimitExequtor;

/**
 * Класс описатель для текущего параметра лимит мощности.
 */
public class CurrentPowerLimit extends AbstractCurrentParameter {

	@Override
	public AbstractCurrentParameterExequtor getExequtor() {
		
		return new PowerLimitExequtor(this);
	}

}
