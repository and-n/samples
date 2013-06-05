public class Serv {
	public static final int DIRECTION_REQ = 0x80;
	public static final int DIRECTION_RESP = 0x00;

	private int direction = DIRECTION_REQ;
	private int access = 0;
	private int dataLength = 0;

	public Serv() {
	}

	public Serv(int direction, ClassAccessEnum access, int dataLength) {
		super();
		setDirection(direction);
		this.access = access.getCode();
		setDataLength(dataLength);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		if (direction != DIRECTION_REQ && direction != DIRECTION_RESP) {
			throw new IllegalArgumentException("direction");
		}
		this.direction = direction;
	}

	public ClassAccessEnum getAccess() {
		return ClassAccessEnum.byCode(access);
	}
	
	public int getAccessCode() {
		return access;
	}

	public void setAccess(ClassAccessEnum access) {
		this.access = access.getCode();
	}
	
	public void setAccessCode(int code)
	{
		this.access = code;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		if ((dataLength & 0x0F) != dataLength)
		{
			throw new IllegalArgumentException("dataLength > 15");
		}
		this.dataLength = dataLength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + access;
		result = prime * result + dataLength;
		result = prime * result + direction;
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
		Serv other = (Serv) obj;
		if (access != other.access)
			return false;
		if (dataLength != other.dataLength)
			return false;
		if (direction != other.direction)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Serv [direction=" + direction + ", access=" + access
				+ ", dataLength=" + dataLength + "]";
	}
}
