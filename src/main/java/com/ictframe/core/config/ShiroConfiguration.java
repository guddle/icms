package com.ictframe.core.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.ictframe.core.cache.shiro.RedisCacheManager;
import com.ictframe.core.shiro.IctShiroRealm;

@Configuration
public class ShiroConfiguration {
	
	private final static Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

//	@Bean(name = "credentialsMatcher")
//	public CredentialsMatcher credentialsMatcher() {
//		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//		credentialsMatcher.setHashAlgorithmName(AuthConstant.hashAlgorithmName);
//		// credentialsMatcher.setHashIterations(AuthConstant.hashIterations);
//		credentialsMatcher.setHashIterations(1);
//		// credentialsMatcher.setStoredCredentialsHexEncoded(AuthConstant.hexEncodedEnabled);默认就是这个
//		return credentialsMatcher;
//	}

	
	
	@Bean(name = "shiroRealm")
	@DependsOn("lifecycleBeanPostProcessor")
	public IctShiroRealm ictShiroRealm(CacheManager shiroRedisCacheManager) {
		IctShiroRealm ictShiroRealm = new IctShiroRealm();
//		ictShiroRealm.setCredentialsMatcher(credentialsMatcher);
		ictShiroRealm.setCacheManager(shiroRedisCacheManager);
		return ictShiroRealm;
	}

//	@Bean(name = "ehCacheManager")
//	@DependsOn("lifecycleBeanPostProcessor")
//	public EhCacheManager ehCacheManager() {
//		EhCacheManager ehCacheManager = new EhCacheManager();
//		return ehCacheManager;
//	}

	@Bean(name = "shiroRedisCacheManager")
	@DependsOn("lifecycleBeanPostProcessor")
	public CacheManager shiroRedisCacheManager(RedisTemplate<?, ?> redisTemplate) {
		return new RedisCacheManager(redisTemplate);
	}
	/**
	 * 凭证匹配器
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * ）
	 * @return
	 */
//	@Bean
//	public HashedCredentialsMatcher hashedCredentialsMatcher(){
//		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//		hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
//		return hashedCredentialsMatcher;
//	}
	
	/**
	 * 管理所有Subject，SecurityManager 是 Shiro 架构的核心，配合内部安全组件共同组成安全伞。
	 * 
	 * @author jerry
	 * @Date Jul 16, 2018
	 * @return
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager(CacheManager shiroRedisCacheManager, IctShiroRealm shiroRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 看了源码 默认就是这个 没必要在此构建了
		// SessionManager sessionManager = new DefaultWebSessionManager();
		// securityManager.setSessionManager(sessionManager);
		securityManager.setRealm(shiroRealm);
		securityManager.setCacheManager(shiroRedisCacheManager);
		SecurityUtils.setSecurityManager(securityManager);
		
		return securityManager;
	}

	/**
	 * anon:所有url都都可以匿名访问
	 * authc: 需要认证才能进行访问
	 * user:配置记住我或认证通过可以访问
	 * @author jerry
	 * @Date Jul 20, 2018
	 * @param securityManager
	 * @return
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//配置登陆页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登陆成功后跳转的页面
		shiroFilterFactoryBean.setSuccessUrl("/index");
		//未授权页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		// 配置拦截器
		Map<String, String> filterChainDefinitionManager = new LinkedHashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionManager.put("/static/**", "anon");
		filterChainDefinitionManager.put("/templates/**", "anon");
		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionManager.put("/logout", "logout");
		//说明访问/add这个链接必须要有“权限添加”这个权限才可以访问，如果在shiro配置文件中添加了
		//filterChainDefinitionMap.put(“/add”, “perms[权限添加]”);
		// filterChainDefinitionManager.put("/user/**", "authc,roles[user]");
		// filterChainDefinitionManager.put("/shop/**", "authc,roles[shop]");
		// filterChainDefinitionManager.put("/admin/**", "authc,roles[admin]");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionManager.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
		logger.debug("Shiro拦截器注入成功----------");
		return shiroFilterFactoryBean;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager);
		return aasa;
	}

	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver
	createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
		mappings.setProperty("UnauthorizedException","403");
		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("error");    // No default
		r.setExceptionAttribute("ex");     // Default is "exception"
		//r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}
}
