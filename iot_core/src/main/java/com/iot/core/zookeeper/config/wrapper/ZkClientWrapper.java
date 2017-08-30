package com.iot.core.zookeeper.config.wrapper;

import org.I0Itec.zkclient.ZkClient;

import com.iot.common.utils.ZkUtils;
import com.iot.common.utils.log.LogUtils;
import com.iot.core.zookeeper.ZkConfig;

public class ZkClientWrapper {
	private static ZkClient zkClient;

	private static final ZkClientWrapper instance = new ZkClientWrapper();

	static {
		try {
			LogUtils.getInstance().infoSystem("ZkClientWrapper",
					"Begin connecting to zookeeper cluster......");
			zkClient = new ZkClient(ZkConfig.ZK_HOST,
					ZkConfig.ZK_CLIENT_TIMEOUT,
					ZkConfig.ZK_CLIENT_TIMEOUT,
					new ZkUtils.StringSerializer("UTF-8"));

			LogUtils.getInstance().infoSystem("ZkClientWrapper",
					"Connect  to zookeeper cluster successfully!");
			
			ZkConfig.setClusterAlive(true);
		} catch (Exception e) {
			LogUtils.getInstance().warnSystem("ZkClientWrapper",
					"Connect to zookeeper cluster!!", e);
			
			ZkConfig.setClusterAlive(false);
		}

	}

	private ZkClientWrapper() {
	}

	public static ZkClientWrapper getInstance() {
		return instance;
	}

	public ZkClient getZkClient() {
		return zkClient;
	}

}
