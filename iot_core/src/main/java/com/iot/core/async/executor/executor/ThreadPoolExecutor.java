package com.iot.core.async.executor.executor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.iot.core.async.executor.vo.BaseExecutorVO;

/**
 * 通过线程，异步执行任务
 * 
 * @author gavin
 * 
 */
@Component
@Lazy(true)
public abstract class ThreadPoolExecutor extends AbstractAsyncExecutor {
	@Resource(name = "taskExecutor")
	protected TaskExecutor taskExecutor;

	abstract protected int executeThread(BaseExecutorVO vo);

	public int execute(BaseExecutorVO vo) {
		taskExecutor.execute(new Task(vo));
		return 1;
	}

	protected class Task implements Runnable {
		private StringBuilder threadName = new StringBuilder();
		private IAsyncExecutor executor;
		private BaseExecutorVO vo;

		public Task(BaseExecutorVO vo) {
			this.vo = vo;
		}

		public Task(IAsyncExecutor executor, BaseExecutorVO vo) {
			this.executor = executor;
			this.vo = vo;
		}

		public void run() {
			try {
				executeThread(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
