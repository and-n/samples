package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.ce102.exequtors.CurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

public class CeCurrentEnergy extends CeCurrentParameter {
	
	private int tarifNumber;

	public int getTarifNumber() {
		return tarifNumber;
	}

	public void setTarifNumber(int tarifNumber) {
		this.tarifNumber = tarifNumber;
	}

	@Override
	public CurrentParameterExequtor getExequtor()
	{
		return new EnergyParameterExequtor(tarifNumber,0);
	}


}
