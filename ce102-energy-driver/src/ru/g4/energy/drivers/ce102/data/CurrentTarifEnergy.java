package ru.g4.energy.drivers.ce102.data;

import ru.g4.energy.drivers.ce102.exequtors.AbstractCurrentParameterExequtor;
import ru.g4.energy.drivers.ce102.exequtors.EnergyParameterExequtor;

/**
 * Класс описатель для текущего параметра расход энергии для тарифа.
 */
public class CurrentTarifEnergy extends AbstractCurrentParameter {
	
	/**
	 * Номер тарифа, для которого запрашивается энергия.
	 */
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

	@Override
	public String toString()
	{
		return "CurrentTarifEnergy [tarifNumber=" + tarifNumber
				+ ", getExequtor()=" + getExequtor().getClass() + ", isWriteable()="
				+ isWriteable() + ", getNumber()=" + getNumber()
				+ ", getInner()=" + getInner() + "]";
	}


}
