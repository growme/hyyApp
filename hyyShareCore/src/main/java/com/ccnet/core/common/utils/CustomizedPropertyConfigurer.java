package com.ccnet.core.common.utils;


import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.ccnet.core.common.cache.ConfigCache;
/**
 * 参考：http://blog.csdn.net/smithdoudou88/article/details/47663021
 * @author Xiong Wei
 *
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {  
  
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
        //load properties to ctxPropertiesMap  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ConfigCache.refresConfig(keyStr, value);
        }  
    }  
    
}  