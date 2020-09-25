package com.ccnet.core.common.utils.taglib;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.util.CollectionUtils;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;

/**
 * <p>User: jackie wang
 * <p>Date: 16-12-08
 * <p>Version: 1.0
 */
public class Functions {

    public static boolean in(Iterable iterable, Object element) {
        if(iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }

    public static String principal(Session session) {
    	String loginAccount = null;
        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
       if(CPSUtil.isNotEmpty(principalCollection)){
    	   loginAccount = (String)principalCollection.getPrimaryPrincipal();
       }
       return loginAccount;
    }
    
    public static boolean isForceLogout(Session session) {
        return session.getAttribute(Const.SESSION_FORCE_LOGOUT_KEY) != null;
    }
    
}

