package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

public class CurrentTarifEnergy extends AbstractCurrentParameter {
	
	private int tarifNumber;

	public int getTarifNumber() {
		return tarifNumber;
	}

	public void setTarifNumber(int tarifNumber) {
		this.tarifNumber = tarifNumber;
	}

	@Override
	public AbstractCurrentParameterExequtor getExequtor()
	{
		return new EnergyParameterExequtor(this, tarifNumber,0);
	}


}
