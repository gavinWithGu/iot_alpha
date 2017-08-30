package com.iot.core.zookeeper.config.placeholderhelper;

import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.iot.common.utils.log.LogUtils;
import com.iot.core.wrapper.ConfigFileWrapper;
import com.iot.core.zookeeper.ZkConfig;
import com.iot.core.zookeeper.config.PropRepos;

public class GeneralPropertyPlaceholder extends PropertyPlaceholderHelper {

	private String rootNode = ZkConfig.ZK_CONFIGS_ROOTNODE;
	private ZooKeeper zk;
	private Stat stat = new Stat();

	private static final byte[] lock = new byte[0];

	private static final GeneralPropertyPlaceholder instance = new GeneralPropertyPlaceholder();

	private GeneralPropertyPlaceholder() {
	}

	public static GeneralPropertyPlaceholder getInstance() {
		return instance;
	}

	public void updateConfigs(String path, ZkClient zkClient) throws Exception {
		synchronized (lock) {
			// 获取并监听子节点变化
			// watch参数为true, 表示监听子节点变化事件.
			// 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
			String data = zkClient.readData(rootNode + "/" + path, stat);

			// 获取每个子节点下关联的server地址
			Properties prop = this.parseFromString(data);

			for (Entry<Object, Object> element : prop.entrySet()) {
				PropRepos.put(element.getKey().toString(), element.getValue()
						.toString());
			}
		
		}
		LogUtils.getInstance().infoSystem("General Property Placeholder",
				"Server configs updated", "Total:", PropRepos.count(),
				"Detail:", PropRepos.getAllConfigs());
	}

	public void updateAllConfigs() throws Exception {
		synchronized (lock) {
			// 获取并监听子节点变化
			// watch参数为true, 表示监听子节点变化事件.
			// 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
			List<String> subList = zk.getChildren(rootNode, true);
			for (String subNode : subList) {
				// 获取每个子节点下关联的server地址
				byte[] data = zk.getData(rootNode + "/" + subNode, false, stat);
				Properties prop = this
						.parseFromString(new String(data, "utf-8"));

				for (Entry<Object, Object> element : prop.entrySet()) {
					PropRepos.put(element.getKey().toString(), element
							.getValue().toString());
				}
				ConfigFileWrapper.refresh();
			}

			System.out.println("Server configs updated,total:"
					+ PropRepos.count());
		}
	}
}
