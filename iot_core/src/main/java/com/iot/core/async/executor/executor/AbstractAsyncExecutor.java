package com.iot.core.async.executor.executor;

import org.springframework.stereotype.Component;

import com.iot.common.utils.log.LogUtils;
import com.iot.core.async.executor.vo.BaseExecutorVO;

@Component
public abstract class AbstractAsyncExecutor implements
		IAsyncExecutor<BaseExecutorVO> {
	abstract protected int execute(BaseExecutorVO vo);

	/**
	 * 模板模式，可以扩展做其他的应用
	 * 
	 * @param vo
	 * @return:1.成功，-1:失败
	 */
	public int executorAsync(BaseExecutorVO vo) {
		LogUtils.getInstance().debugSystem(this.toString(),
				"Start execute Asynchronize job", vo.toString());

		int result = this.execute(vo);

		LogUtils.getInstance().debugSystem(this.toString(),
				"End executor Asynchronize job", vo.toString());
		
		return result;
	}
}
