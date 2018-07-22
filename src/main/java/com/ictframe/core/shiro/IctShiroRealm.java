package com.ictframe.core.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.jpa.service.ILoginService;
import com.ictframe.core.util.AuthSubjectUtil;
import com.ictframe.data.entity.Permission;
import com.ictframe.data.entity.Role;
import com.ictframe.data.entity.User;
/**
 * 用于进行权限信息的验证，我们自己实现。Realm 本质上是一个特定的安全 DAO：它封装与数据源连接的细节，
 * 得到Shiro 所需的相关的数据。在配置 Shiro 的时候，你必须指定至少一个Realm 来实现认证（authentication）和/或授权（authorization）
 * @author jerry
 * @Date Jul 16, 2018
 */
public class IctShiroRealm extends AuthorizingRealm {
	
	private final static Logger logger = LoggerFactory.getLogger(IctShiroRealm.class);

	@Autowired
	private ILoginService loginService;

	/**
	 * 权限认证
     *
     * @param principalCollection
     * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("权限配置-->IctShiroRealm.doGetAuthorizationInfo()");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
//        String loginName = (String) super.getAvailablePrincipal(principalCollection);
		User user  = (User)principals.getPrimaryPrincipal();
//		String name = (String) principals.getPrimaryPrincipal();
//		User user = loginService.findByNmae(name);
		if(user != null) {
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			if (AuthSubjectUtil.isPatformAdmin()) {
				authorizationInfo.addStringPermission("*");
            }
			//权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			for (Role role : user.getRoles()) {
				authorizationInfo.addRole(role.getRole());
				for (Permission permission : role.getPermissions()) {
					authorizationInfo.addStringPermission(permission.getPermission());
				}
			}
			return authorizationInfo;
		}
		return null;
	}

	/**
	 * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("IctShiroRealm.doGetAuthenticationInfo()-----------");
		if (token == null) {
			return null;
		}
		String username = (String) token.getPrincipal();
		logger.info("验证当前Subject时获取到token为：{}",token.getCredentials()) ;
		///实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		User user = loginService.findByNmae(username);
		logger.info("获取当前验证的用户信息是：{}",user);
		if (user != null) {
			// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            // @link org.apache.shiro.realm.AuthenticatingRealm#assertCredentialsMatch
//			if(user.getRoles() == null) {
//				logger.info("用户角色为空，重载{}角色", user.getUsername());
//				List<Role> roles = loginService.findRolePermissions(user);
//				user.setRoles(roles);
//			}
			SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(
					user, 
					user.getPassword(),
					//ByteSource.Util.bytes(user.getSalt()),////salt=username+salt
					user.getUsername());
			return sai;
		} 
		return null;
	}

}
