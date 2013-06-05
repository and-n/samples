import java.io.Serializable;
import java.nio.ByteBuffer;

import ru.g4.utils.HEXUtils;

public abstract class PALFrame implements Serializable{

	private static final long serialVersionUID = 2106296785845013119L;
	private Serv serv = new Serv();
	private int command = 0;
	private ByteBuffer data = ByteBuffer.wrap(new byte[0]);

	public PALFrame() {
		super();
	}
	
	public PALFrame(Serv serv, CommandEnum command, ByteBuffer data) {
		super();
		this.serv = serv;
		this.command = command.getAddr();
		this.data = data;
	}

	public Serv getServ() {
		return serv;
	}
	
	public void setServ(Serv serv)
	{
		this.serv = serv;
	}
	
	public CommandEnum getCommand() {
		return CommandEnum.byAddr(getCommandCode());
	}
	
	public int getCommandCode() {
		return command;
	}

	public void setCommand(CommandEnum command) {
		this.command = command.getAddr();
	}
	
	public void setCommandCode(int command) {
		this.command = command;
	}

	public ByteBuffer getData() {
		return data;
	}

	public void setData(ByteBuffer data) {
		getServ().setDataLength(data.array().length);
		this.data = data;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + command;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((serv == null) ? 0 : serv.hashCode());
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
		PALFrame other = (PALFrame) obj;
		if (command != other.command)
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (serv == null) {
			if (other.serv != null)
				return false;
		} else if (!serv.equals(other.serv))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PALResponse [getAccess()=" + getServ() + ", getCommand()="
				+ getCommand() + ", getData()=" + HEXUtils.toString(getData().array()) + "]";
	}
}