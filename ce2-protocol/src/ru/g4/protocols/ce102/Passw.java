package ru.g4.protocols.ce102;
import java.io.Serializable;
import java.util.Arrays;

import ru.g4.utils.HEXUtils;


public class Passw implements Serializable{
	private static final long serialVersionUID = -1490617882007120889L;
	private byte[] data;

	public static Passw fromByte(byte[] data)
	{
		Passw p = new Passw();
		p.data = Arrays.copyOf(data, 4);
		return p;
	}
	
	public static Passw fromString(String data)
	{
		Passw p = new Passw();
		p.data = Arrays.copyOf(data.getBytes(), 4);
		return p;
	}
	
	protected Passw()
	{
	}
	
	public byte[] getData()
	{
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Passw other = (Passw) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Passw [data=" + HEXUtils.toString(data) + "]";
	}
}
