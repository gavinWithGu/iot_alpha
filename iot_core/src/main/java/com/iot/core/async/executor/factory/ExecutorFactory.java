package com.iot.core.async.executor.factory;

import com.iot.common.spring.context.BeanFactory;
import com.iot.core.async.executor.executor.IAsyncExecutor;
import com.iot.core.async.executor.vo.BaseExecutorVO;

public class ExecutorFactory {

	public final static int THREAD = 1;
	public final static int MQ = 2;

	public IAsyncExecutor<BaseExecutorVO> getExecutor(int type) {
		switch (type) {
		case THREAD:
			return (IAsyncExecutor) BeanFactory.getInstance().getBeanByName(
					"threadPoolExecutorMock");
		case MQ:
			return (IAsyncExecutor) BeanFactory.getInstance().getBeanByName(
					"apiCallLogExecutor");
		}
		return null;
	}

	/**
	 * 私有构造方法，方式单例类被实例化
	 */
	private ExecutorFactory() {
	}

	/**
	 * 私有类，包装单例类的对象，防止在class加载时，单例对象被提前加载
	 * 
	 * @author gavin
	 * 
	 */
	private static class SingleHolder {
		private static ExecutorFactory instance = new ExecutorFactory();
		static {
			System.out.println("Instance init");
		}
	}

	/**
	 * 单例对象获取方法，第一次调用时，SingleHolder会加载，并且把instance实例化出来
	 * 
	 * @return
	 */
	public static ExecutorFactory getInstance() {
		return SingleHolder.instance;
	}
}
