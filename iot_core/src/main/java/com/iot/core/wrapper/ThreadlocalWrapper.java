package com.iot.core.wrapper;

public class ThreadlocalWrapper {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setType(String type) {
		contextHolder.set(type);
	}

	public static String getType() {
		return contextHolder.get();
	}

}
