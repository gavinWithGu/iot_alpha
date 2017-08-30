package com.iot.core.async.executor.vo;

import java.io.Serializable;

/**
 * 异步执行基础逻辑vo
 * 
 * @author gavin
 * 
 */
public class BaseExecutorVO implements Serializable {
	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}
