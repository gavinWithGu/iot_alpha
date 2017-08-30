package com.iot.core.zookeeper.config.listener.impl;

import org.I0Itec.zkclient.ZkClient;

import com.iot.common.utils.log.LogUtils;
import com.iot.core.wrapper.ConfigFileWrapper;
import com.iot.core.zookeeper.ZkConfig;
import com.iot.core.zookeeper.config.listener.IDynamicConfigFileListener;
import com.iot.core.zookeeper.config.placeholderhelper.GeneralPropertyPlaceholder;
import com.iot.core.zookeeper.config.subcriber.ConfigChangeSubscriber;
import com.iot.core.zookeeper.config.subcriber.impl.ZkConfigChangeSubscriberImpl;
import com.iot.core.zookeeper.config.wrapper.ZkClientWrapper;

public class DynamicConfigFileListenerImpl implements
		IDynamicConfigFileListener {

	private ConfigChangeSubscriber zkConfig = new ZkConfigChangeSubscriberImpl();

	private ZkClient zkClient = ZkClientWrapper.getInstance().getZkClient();

	@Override
	public void registerListener() {
		for (final String path : ZkConfig.ZK_CONFIGS_FILE_COLLECTION) {

			try {
				GeneralPropertyPlaceholder.getInstance().updateConfigs(path,
						zkClient);
			} catch (Exception e1) {
				LogUtils.getInstance().errorSystem("SpringPropertyPlaceholder",
						"Update config properties error!", e1);
			}

			this.zkConfig.subscribe(path, new ConfigChangeListener() {
				public void configChanged(String key, String value) {
					LogUtils.getInstance()
							.infoSystem(
									"Received Config File Change notice from zookeeper",
									key, value);
					try {
						GeneralPropertyPlaceholder.getInstance().updateConfigs(
								key, zkClient);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		ConfigFileWrapper.refresh();
	}
}
