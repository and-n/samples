package ru.g4.protocols.ce102;

import java.io.IOException;

public class CrcException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5975744224146892102L;

	public CrcException() {
	}

	public CrcException(String message) {
		super(message);
		
	}

	public CrcException(Throwable cause) {
		super(cause);
		
	}

	public CrcException(String message, Throwable cause) {
		super(message, cause);
	}

}
