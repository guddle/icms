package com.ictframe.core.exception;

import com.ictframe.core.HttpCode;

public class BusinessException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1265980782683126509L;

	public BusinessException() {
	}

	public BusinessException(Throwable ex) {
		super(ex);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable ex) {
		super(message, ex);
	}

	protected HttpCode getCode() {
		return HttpCode.CONFLICT;
	}
}
