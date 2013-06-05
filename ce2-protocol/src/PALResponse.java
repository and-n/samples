import java.nio.ByteBuffer;

import ru.g4.utils.HEXUtils;

public class PALResponse extends PALFrame{

	private static final long serialVersionUID = -418508573961900194L;

	public PALResponse()
	{
		super();
	}
	
	public PALResponse(ClassAccessEnum access, CommandEnum command,
			ByteBuffer data) {
		super(new Serv(Serv.DIRECTION_RESP, access, data.array().length), command, data);
	}

	@Override
	public String toString() {
		return "PALResponse [getServ()=" + getServ() + ", getCommand()="
				+ getCommand() + ", getData()=" + HEXUtils.toString(getData().array()) + "]";
	}
}