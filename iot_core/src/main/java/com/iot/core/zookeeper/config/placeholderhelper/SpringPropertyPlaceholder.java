package com.iot.core.zookeeper.config.placeholderhelper;

import java.util.Properties;

import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.iot.common.utils.log.LogUtils;
import com.iot.core.wrapper.ConfigFileWrapper;
import com.iot.core.zookeeper.ZkConfig;
import com.iot.core.zookeeper.config.wrapper.ZkClientWrapper;

public class SpringPropertyPlaceholder extends PropertyPlaceholderConfigurer {
	public static final String PATH = "zoo.paths";

	private static final byte[] lock = new byte[0];
	private String rootNode = ZkConfig.ZK_CONFIGS_ROOTNODE;
	private Stat stat = new Stat();

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		try {
			updateConfigs(props);

			LogUtils.getInstance().infoSystem("SpringPropertyPlaceholder",
					"Load Spring properties from zookeeper successfully!",
					props);
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem("SpringPropertyPlaceholder",
					"Load Spring properties from zookeeper Error!", e);
		}

		super.processProperties(beanFactoryToProcess, props);
	}

	public void updateConfigs(Properties props) throws Exception {
		synchronized (lock) {

			if (ZkConfig.isZK_CLUSTER_ALIVE()) {
				for (final String path : ZkConfig.ZK_CONFIGS_FILE_SPRING_COLLECTION) {
					String data = ZkClientWrapper.getInstance().getZkClient()
							.readData(rootNode + "/" + path, stat);
					props.putAll(PropertyPlaceholderHelper
							.parseFromString(data));
				}
			}else{
				props.put("jdbc.driverClassName", ConfigFileWrapper.JDBC_DRIVERCLASSNAME);
				props.put("jdbc.url", ConfigFileWrapper.JDBC_URL);
				props.put("jdbc.username", ConfigFileWrapper.JDBC_USERNAME);
				props.put("jdbc.password", ConfigFileWrapper.JDBC_PASSWORD);
				
				props.put("jms.queue.name", ConfigFileWrapper.JMS_QUEUE_NAME);
				props.put("jms.broke.url", ConfigFileWrapper.JMS_BROKE_URL);
			}

		}

		LogUtils.getInstance().infoSystem("SpringPropertyPlaceholder",
				"server configs updated!", props.size());
	}
}
