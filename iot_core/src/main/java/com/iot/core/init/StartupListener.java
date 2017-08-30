package com.iot.core.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iot.common.spring.context.BeanFactory;
import com.iot.core.zookeeper.ZkConfig;
import com.iot.core.zookeeper.config.listener.IDynamicConfigFileListener;
import com.iot.core.zookeeper.config.wrapper.DynamicConfigFileListenerWrapper;
import com.iot.util.ModelClassesHolder;

/**
 * @author gavin
 */
public class StartupListener extends ContextLoaderListener {
	private Logger logger = Logger.getLogger(StartupListener.class);

	private ApplicationContext context;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
		/**
		 * 注册动态配置文件服务类
		 */
		// 监听配置文件内容 变化，动态更新
		IDynamicConfigFileListener dynamicConfigFileListener = DynamicConfigFileListenerWrapper
				.getInstance().getListener();

		if (ZkConfig.isZK_CLUSTER_ALIVE()){
			dynamicConfigFileListener.registerListener();
		}
		
		/**
		 * spring context初始化
		 */
		super.contextInitialized(event);
		ServletContext servletContext = event.getServletContext();
		context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		logger.info("Load Spring context into BeanFactory...");
		BeanFactory.getInstance().setContext(context);
		
		/**
		 * Pojo,Dto反射初始化
		 */
		ModelClassesHolder.getInstance().init();
	}
}
