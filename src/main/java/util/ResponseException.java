package com.citycloud.ccuap.ybhw.util;

/**
 * @author cci
 */
public class ResponseException extends RuntimeException {

	private static final long serialVersionUID = 8231621016525879032L;

	private int errorCode;
	private String errorMessage;
	
	public ResponseException() {
		this.errorCode = -1;
	}

	public ResponseException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public ResponseException(String errorMessage) {
		super(errorMessage);
		this.errorCode = -1;
		this.errorMessage = errorMessage;
	}

	public ResponseException(Throwable cause) {
		super(cause);
		this.errorCode = -1;
	}

	public ResponseException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		
		this.errorCode = -1;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}
