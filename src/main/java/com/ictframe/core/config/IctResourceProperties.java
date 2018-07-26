package com.ictframe.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ict.resources")
public class IctResourceProperties {

	private String ymlPath;
	
	private String propsPath;

	public String getYmlPath() {
		return ymlPath;
	}

	public void setYmlPath(String ymlPath) {
		this.ymlPath = ymlPath;
	}

	public String getPropsPath() {
		return propsPath;
	}

	public void setPropsPath(String propsPath) {
		this.propsPath = propsPath;
	}
	
	
	
}
