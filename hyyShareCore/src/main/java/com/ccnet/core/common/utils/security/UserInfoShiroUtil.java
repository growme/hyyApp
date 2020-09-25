package com.ccnet.core.common.utils.security;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.entity.UserInfo;

/**
 * 封装shiro用对象获取
 * 
 */
public class UserInfoShiroUtil {
	/**
	 * 获取当前对象的拷贝
	 * 
	 * @return
	 */
	public static UserInfo getCurrentUser() {
		UserInfo customer = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_USER);
			if (null != obj && obj instanceof UserInfo) {
				try {
					/**
					 * 不复制一份对象，防止被错误操作
					 */
					customer = (UserInfo) obj;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return customer;
	}

	/**
	 * 获取当前真实的对象，可以进行操作实体
	 * 
	 * @return
	 */
	public static UserInfo getRealCurrentUser() {
		UserInfo customer = null;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_USER);
			if (null != obj && obj instanceof UserInfo) {
				try {
					/**
					 * 复制一份对象，防止被错误操作
					 */
					customer = (UserInfo) BeanUtils.cloneBean((UserInfo) obj);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return customer;
	}
}
