public enum ClassAccessEnum {
	Request(0x05), AccessError(0x07);

	private int code;

	private ClassAccessEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static ClassAccessEnum byCode(int code) {
		for (ClassAccessEnum e : ClassAccessEnum.values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		return null;
	}
}
