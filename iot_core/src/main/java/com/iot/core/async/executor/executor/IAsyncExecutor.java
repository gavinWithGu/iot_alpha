package com.iot.core.async.executor.executor;

import com.iot.core.async.executor.vo.BaseExecutorVO;

public interface IAsyncExecutor<T extends BaseExecutorVO> {
	
	/**
	 * 超类异步执行方法：
	 * 
	 * @param vo
	 * @return:1.成功，-1:失败
	 */
	public int executorAsync(T vo);
}
