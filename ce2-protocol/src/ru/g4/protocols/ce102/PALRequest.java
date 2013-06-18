package ru.g4.protocols.ce102;
import java.io.Serializable;
import java.nio.ByteBuffer;

import ru.g4.utils.HEXUtils;

public class PALRequest extends PALFrame implements Serializable {

	private static final long serialVersionUID = 7553151441336311732L;
	private long passw = 0;

	public PALRequest() {
	}

	public PALRequest(long passw, ClassAccessEnum access, CommandEnum command,
			ByteBuffer data) {
		super(new Serv(Serv.DIRECTION_REQ, access, data.array().length), command, data);
		this.passw = passw;
	}

	public long getPassw() {
		return passw;
	}

	public void setPassw(long passw) {
		this.passw = passw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + new Long(passw).hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PALRequest other = (PALRequest) obj;
		if (passw != other.passw)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PALRequest [getPassw()=" + getPassw() + ", getServ()="
				+ getServ() + ", getCommand()=" + getCommand() + ", getData()="
				+ HEXUtils.toString(getData().array()) + "]";
	}

}
