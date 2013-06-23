package ru.g4.protocols.ce201;

import java.io.IOException;

public class ParityCheckException extends IOException {

	private static final long serialVersionUID = -3090385878279523527L;

	public ParityCheckException() {
		super();
	}

	public ParityCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParityCheckException(String message) {
		super(message);
	}

	public ParityCheckException(Throwable cause) {
		super(cause);
	}
}
