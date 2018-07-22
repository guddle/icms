package com.ictframe.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ictframe.data.entity.User;


public class AuthSubjectUtil {

	 /**
     * 系统管理员账号
     */
    public static final String SUPPLIER_ADMIN_USERNAME = "admin";

    public static boolean isPatformAdmin(String principal) {
        if (StringUtils.isEmpty(principal))
            return false;
        return SUPPLIER_ADMIN_USERNAME.equalsIgnoreCase(principal);
    }

    /**
     * 判断是否平台管理员账号登录
     */
    public static boolean isPatformAdmin() {
        Subject subject = SecurityUtils.getSubject();
        if (subject == null)
            return false;
        User user = (User) subject.getPrincipal();
        return SUPPLIER_ADMIN_USERNAME.equalsIgnoreCase(user.getUsername().trim());
    }
}
