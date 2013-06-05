package ru.g4.protocols.ce102;
import java.io.Serializable;
import java.nio.ByteBuffer;

import ru.g4.utils.HEXUtils;

public class PALRequest extends PALFrame implements Serializable {

	private static final long serialVersionUID = 7553151441336311732L;
	private Passw passw = new Passw();

	public PALRequest() {
	}

	public PALRequest(Passw passw, ClassAccessEnum access, CommandEnum command,
			ByteBuffer data) {
		super(new Serv(Serv.DIRECTION_REQ, access, data.array().length), command, data);
		this.passw = passw;
	}

	public Passw getPassw() {
		return passw;
	}

	public void setPassw(Passw passw) {
		this.passw = passw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((passw == null) ? 0 : passw.hashCode());
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
		if (passw == null) {
			if (other.passw != null)
				return false;
		} else if (!passw.equals(other.passw))
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
