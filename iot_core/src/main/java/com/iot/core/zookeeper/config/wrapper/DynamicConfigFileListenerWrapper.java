package com.iot.core.zookeeper.config.wrapper;

import com.iot.core.zookeeper.config.listener.IDynamicConfigFileListener;
import com.iot.core.zookeeper.config.listener.impl.DynamicConfigFileListenerImpl;

public class DynamicConfigFileListenerWrapper {
	private static IDynamicConfigFileListener listener;

	private static final DynamicConfigFileListenerWrapper instance = new DynamicConfigFileListenerWrapper();

	static {
		listener = new DynamicConfigFileListenerImpl();
	}

	private DynamicConfigFileListenerWrapper() {
	}

	public static DynamicConfigFileListenerWrapper getInstance() {
		return instance;
	}

	public IDynamicConfigFileListener getListener() {
		return listener;
	}

}
