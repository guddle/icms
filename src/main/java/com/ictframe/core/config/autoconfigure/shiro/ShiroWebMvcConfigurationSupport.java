package com.ictframe.core.config.autoconfigure.shiro;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.ictframe.core.config.autoconfigure.shiro.annotation.SessionUserArgumentResolver;
@Configuration
public class ShiroWebMvcConfigurationSupport extends WebMvcConfigurationSupport {

	  @Override
	    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
	        argumentResolvers.add(new SessionUserArgumentResolver());
	    }
	
}
