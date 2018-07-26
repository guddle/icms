package com.ictframe.core.config;


import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.ictframe.core.Constants;
import com.ictframe.core.util.PropertiesUtil;
import com.ictframe.core.util.SercurityUtil;


@Configuration
@EnableConfigurationProperties(IctResourceProperties.class)
public class IctResourceAotuConfiguration{
	
	private final static Logger log = LoggerFactory.getLogger(IctResourceAotuConfiguration.class);
	
	private IctResourceProperties ictResourceProperties;
	
	private ConfigurableEnvironment env;
	
//	private ConfigurableEnvironment env;
//	private SpringApplication application;
//	
//	
	public IctResourceAotuConfiguration(ConfigurableEnvironment environment, IctResourceProperties ictResourceProperties) {
		this.env = environment;
//		this.application = application;
		this.ictResourceProperties = ictResourceProperties;
		loadResourceConfig(this.env);
	}
	
//	
//	@Override
//	public int getOrder() {
//		// +1 保证application.propertie里的内容能覆盖掉本配置文件中默认的
//        // 如果不想被覆盖 可以去掉 +1 或者 -1 试试
//        return ConfigFileApplicationListener.DEFAULT_ORDER + 1;
//	}
	
	protected void loadResourceConfig(PropertiesFactoryBean config) throws IOException {
//		environment = this.env;
//		MutablePropertySources propertySources = environment.getPropertySources();
//		PropertiesFactoryBean config = new PropertiesFactoryBean();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
		String propPath = this.ictResourceProperties.getPropsPath();
		String ymlPath = this.ictResourceProperties.getYmlPath();
		try {
			if(propPath==null||"".equals(propPath)) {
				log.warn("Property ict.resources.props.path is null.");
			}else {
				String[] paths = propPath.split(",");
				for (String path : paths) {
					log.debug("getConfig()@Load Properties {}", path);
					Resource[] resources = resolver.getResources(path);
					config.setLocations(resources);
					config.afterPropertiesSet();
				}
			}
			if(ymlPath==null||"".equals(ymlPath)) {
				log.warn("Property ict.resources.yml.path is null.");
			}else {
				String[] paths = ymlPath.split(",");
				for (String path : paths) {
					log.debug("getConfig()@Load .ymls {}", path);
					Resource[] resources = resolver.getResources(path);
					yaml.setResources(resources);
					config.setProperties(yaml.getObject());
					config.afterPropertiesSet();
				}
			}
			log.info("getConfig()@ get {},{}  Properties and .yml file config path Success."
					+ "and get property${ict.db.password}={}",
					propPath, ymlPath,config.getObject().get("ict.db.password"));
//			PropertiesPropertySource propertySource = new PropertiesPropertySource("thirenv", config.getObject());
//			log.debug("getConfig()@ get propertySource Name is {},and get Properties ${ict.db.password} = {}",
//					propertySource.getName(),propertySource.getProperty("ict.db.password"));
//			propertySources.addLast(propertySource);
		} catch (Exception e) {
			log.error("", e);
		}
	}

//	@Override
//	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
////		MutablePropertySources propertySources = environment.getPropertySources();
//		log.debug("Load config resource properies start........");
//		loadResourceConfig(environment);
////		environment = this.env;
//		try {
//            Map<String,String> props = environment.getSystemProperties();
//            for (Object key : props.keySet()) {
//            	String keyStr = key.toString();
//            	String value = props.get(keyStr);
//            	if ("ict.db.password".contains(keyStr)) {
//            		String dbkey = props.getProperty("ict.db.key");
//            		String dbsalt = props.getProperty("ict.db.salt");
//            		dbkey = StringUtils.isEmpty(dbkey)? Constants.DB_KEY : dbkey;
//                    dbsalt = StringUtils.isEmpty(dbsalt)? Constants.DB_ECRYPT_SALT : dbsalt;
//                    value = SercurityUtil.decrypt(value, dbkey, dbsalt.getBytes());
//                    props.setProperty("spring.datasource.password", value);
//                }
//                PropertiesUtil.getProperties().put(keyStr, value);
//			}
////            propertySources.addLast(new PropertiesPropertySource("thirdEnv", props));
//            log.debug("Load config resource properies Success!");
//        } catch (IOException e) {
//            log.error("", e);
//        }
//	}
	public void loadResourceConfig(ConfigurableEnvironment environment) {
		MutablePropertySources propertySources = environment.getPropertySources();
		log.debug("Load config resource properies start........");
		try {
			PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
			loadResourceConfig(propertiesFactoryBean);
			Properties props = propertiesFactoryBean.getObject();
            for (Object key : props.keySet()) {
            	String keyStr = key.toString();//ict.db.password
            	String value = props.getProperty(keyStr);
            	if ("ict.db.password".contains(keyStr)) {
            		String dbkey = props.getProperty("ict.db.key");
            		String dbsalt = props.getProperty("ict.db.salt");
            		dbkey = StringUtils.isEmpty(dbkey)? Constants.DB_KEY : dbkey;
                    dbsalt = StringUtils.isEmpty(dbsalt)? Constants.DB_ECRYPT_SALT : dbsalt;
                    value = SercurityUtil.decrypt(value, dbkey, dbsalt.getBytes());
                    props.setProperty("ict.db.password", value);
                }
                PropertiesUtil.getProperties().put(keyStr, value);
			}
//            props.setProperty("spring.datasource.password", props.getProperty("ict.db.password"));
//            PropertiesUtil.getProperties().put("spring.datasource.password", props.getProperty("ict.db.password"));
            PropertiesPropertySource propertySource = new PropertiesPropertySource("thirdEnv", props);
            propertySources.addLast(propertySource);
            log.debug("Load config resource properies Success!");
        } catch (IOException e) {
            log.error("", e);
        }
	}
}