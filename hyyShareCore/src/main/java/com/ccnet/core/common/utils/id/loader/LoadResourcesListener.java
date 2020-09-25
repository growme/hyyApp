package com.ccnet.core.common.utils.id.loader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LoadResourcesListener implements ServletContextListener {
    
	public void contextInitialized(ServletContextEvent pServletContextEvent) {
		final String WEB_HOME = pServletContextEvent.getServletContext().getRealPath("/");
		ResourcesLoader.load(WEB_HOME);
	}

	public void contextDestroyed(ServletContextEvent pServletContextEvent) {
	}

}
