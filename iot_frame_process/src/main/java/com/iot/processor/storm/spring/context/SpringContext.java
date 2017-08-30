package com.iot.processor.storm.spring.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext{
	private static ApplicationContext applicationContext;

	public static synchronized void SpringContextInit() {
		if (applicationContext == null) {
//			String[] locations = new String[] { "classpath:applicationContext.xml" };
//			applicationContext = new ClassPathXmlApplicationContext(locations);
			applicationContext =new ClassPathXmlApplicationContext("applicationContext.xml");
		}
	}
}
