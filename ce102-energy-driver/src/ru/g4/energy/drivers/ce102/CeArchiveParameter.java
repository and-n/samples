package ru.g4.energy.drivers.ce102;

import ru.g4.energy.drivers.util.par.ArchiveParameter;

public class CeArchiveParameter extends ArchiveParameter 
{
	int tarif;

	public int getTarif() {
		return tarif;
	}

	public void setTarif(int tarif) {
		this.tarif = tarif;
	}
}
