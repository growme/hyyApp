package com.ccnet.core.common.utils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ccnet.core.common.threadpool.ExecutorProcessPool;
/**
 * Spring上下文获取工具类
 *
 */
public class SpringWebContextUtil implements ServletContextListener {
	protected Logger log = Logger.getLogger(getClass());
	protected boolean success = true;
	
	/**
	 * 上下文对象
	 */
	protected static WebApplicationContext applicationContext;
	/**
	 * 	上下文销毁时执行方法
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ExecutorProcessPool.getInstance().shutdown();
	}
	/**
	 * 上下文初始时执行方法
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ExecutorProcessPool.getInstance();
		log.debug("开始初始化上下文对象...");
		ServletContext sc = sce.getServletContext();
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
		init();
		log.debug("初始化上下文对象完毕...");
	}
	
	protected void init(){};
	
	/**
	 * 获取上下文对象方法
	 * @return 上下方对象
	 */
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
	public static WebApplicationContext getWebApplicationContext() {
		return applicationContext;
	}
	
	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	/**
	 * 获取类型为requiredType的对象
	 * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
	 * 
	 * @param name
	 *            bean注册名
	 * @param requiredType
	 *            返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static <T> T getBean(String name, Class<T> requiredType)
			throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
	 * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象的类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class getType(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	/**
	 * 如果给定的bean名字在bean定义中有别名，则返回这些别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name)
			throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases(name);
	}
	

}
