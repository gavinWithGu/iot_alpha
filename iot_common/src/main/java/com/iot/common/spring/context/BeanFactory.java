package com.iot.common.spring.context;

import org.springframework.context.ApplicationContext;

/**
 * Spring容器实体获取单例类
 */
public class BeanFactory {
	private ApplicationContext context = null;

	/**
	 * 私有构造方法，防止被实例化
	 */
	private BeanFactory() {
	}

	private static class SingleHolder {
		private static BeanFactory instance = new BeanFactory();
		static {
			System.out.println("Instance init");
		}
	}

	/**
	 * 单例对象获取方法，第一次调用时，SingleHolder会加载，并且把instance实例化出来
	 * 
	 * @return
	 */
	public static BeanFactory getInstance() {
		return SingleHolder.instance;
	}

	/**
	 * 通过名称获取Spring容器中的实体
	 * 
	 * @param
	 * @return
	 * @throws
	 */
	public Object getBeanByName(String beanName) {
		if (context == null) {
			return null;
		}
		return context.getBean(beanName);
	}

	public void setContext(ApplicationContext context) {
		this.context = context;
	}
}
