package com.iot.core.async.executor.vo.impl;

import com.iot.core.async.executor.vo.BaseExecutorVO;

public class User extends BaseExecutorVO {
	private static final long serialVersionUID = 4897045061767928601L;
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + "]";
	}
}
