package com.ictframe.core;

import com.ictframe.core.config.Resources;

public enum HttpCode {

	/** 200 Request Success */
	OK(200),
	/** 404 No found */
	NOT_FOUND(404),
	/** 500服务器出错 */
	INTERNAL_SERVER_ERROR(500),
	/** 207 Send Request  frequently*/
	MULTI_STATUS(207),
	/** 303 Login fail */
	LOGIN_FAIL(303),
	/** 400 Bad request*/
	BAD_REQUEST(400),
	/** 401 no login and pls login */
	UNAUTHORIZED(401),
	/** 403 Forbidden */
	FORBIDDEN(403),
	/** 408 Timeout */
	REQUEST_TIMEOUT(408),
	/** 409发生冲突 */
	CONFLICT(409),
	/** 410已被删除 */
	GONE(410),
	/** 423已被锁定 */
	LOCKED(423);
	

	private final Integer value;

	private HttpCode(Integer value) {
		this.value = value;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public Integer value() {
		return this.value;
	}

	public String msg() {
		return Resources.getMessage("HTTPCODE_" + this.value);
	}

	public String toString() {
		return this.value.toString();
	}
}
