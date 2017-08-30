package com.iot.processor.storm.spring.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanutil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		 this.applicationContext=applicationContext;  
	}

	public static <T> T getBean(String name,Class<T> clazz){  
        if (applicationContext==null){  
            SpringContext.SpringContextInit();  
        }  
        System.out.println("==========="+applicationContext);
        System.out.println("==========="+applicationContext);
        return applicationContext.getBean(name,clazz);  
    }  
}
