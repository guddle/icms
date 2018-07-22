package com.ictframe.core.exception;

import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import com.ictframe.core.HttpCode;
/**
 * IctFrame 框架基础异常处理类
 * @author jerry
 * @Date Jul 12, 2018
 */
public abstract class BaseException extends RuntimeException {

	public BaseException() {
	}

	public BaseException(Throwable ex) {
		super(ex);
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable ex) {
		super(message, ex);
	}

	public void handler(ModelMap modelMap) {
		modelMap.put("code", getCode().value());
		if (!StringUtils.isEmpty(getMessage())) {
			modelMap.put("msg", getMessage());
		} else {
			modelMap.put("msg", getCode().msg());
		}
		modelMap.put("timestamp", System.currentTimeMillis());
	}

	protected abstract HttpCode getCode();
}
