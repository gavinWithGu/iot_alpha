package com.iot.core.async.executor.executor.impl;

import org.springframework.stereotype.Component;

import com.iot.core.async.executor.executor.ThreadPoolExecutor;
import com.iot.core.async.executor.vo.BaseExecutorVO;
import com.iot.core.async.executor.vo.impl.User;

@Component("threadPoolExecutorMock")
public class ThreadPoolExecutorMock extends ThreadPoolExecutor {

	/**
	 * 模拟任务：打印用户
	 */
	protected int executeThread(BaseExecutorVO vo) {
		User user = (User) vo;
		System.out.println(user);
		return 1;
	}
	
	
}
