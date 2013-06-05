public enum CommandEnum {
	ReadDateTime(0x0120),
	WriteDateTime(0x0121),
	ReadСonfig(0x0101),
	ReadTariffValue(0x0130),
	ReadTariffSum(0x0131),
	ReadPower(0x0132),
	ReadIntervalEnergy(0x0134),
	ReadLimPwrN(0x0156),
	WriteLimPwrN(0x0157);

	private int code;
	
	private CommandEnum(int code)
	{
		this.code = code;
	}
	
	public int getAddr() {
		return code;
	}

	public byte getAddrH() {
		return (byte) (code >> 8);
	}

	public byte getAddrL() {
		return (byte) code;
	}
	
	public static CommandEnum byAddr(int code) {
		for (CommandEnum e : CommandEnum.values()) {
			if (e.getAddr() == code) {
				return e;
			}
		}
		return null;
	}
}