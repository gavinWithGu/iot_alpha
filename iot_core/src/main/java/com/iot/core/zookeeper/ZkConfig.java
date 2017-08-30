package com.iot.core.zookeeper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ZkConfig {

	public static Properties propsSystem = new Properties();

	public static String ZK_HOST;
	public static int ZK_CLIENT_TIMEOUT;

	public static String ZK_CONFIGS_ROOTNODE;
	public static String ZK_SERVICE_ROOTNODE;

	public static List<String> ZK_CONFIGS_FILE_COLLECTION = new ArrayList<String>();
	public static List<String> ZK_CONFIGS_FILE_SPRING_COLLECTION = new ArrayList<String>();

	
	private static boolean ZK_CLUSTER_ALIVE;
	public static boolean ZK_CONFIGS_ENABLED;
	static {
		InputStream inSystem = ZkConfig.class.getClassLoader()
				.getResourceAsStream("zookeeper.properties");

		try {
			propsSystem.load(inSystem);

			ZK_HOST = propsSystem.getProperty("zk.host").trim();

			ZK_CLIENT_TIMEOUT = Integer.parseInt(propsSystem.getProperty(
					"zk.client.timeout").trim());

			ZK_CONFIGS_ROOTNODE = propsSystem
					.getProperty("zk.configs.rootnode").trim();
			ZK_SERVICE_ROOTNODE = propsSystem
					.getProperty("zk.service.rootnode").trim();

			String configs = propsSystem.getProperty(
					"zk.configs.file.collection").trim();
			String springConfigs = propsSystem.getProperty(
					"zk.configs.file.spring.collection").trim();
			

			ZK_CONFIGS_FILE_COLLECTION = Arrays.asList(configs.split(","));

			ZK_CONFIGS_FILE_SPRING_COLLECTION = Arrays.asList(springConfigs
					.split(","));
			
			ZK_CONFIGS_ENABLED = Boolean.parseBoolean(propsSystem.getProperty("zk.config.enabled", "").trim());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
	}
	
	public static boolean isZK_CLUSTER_ALIVE() {
		return ZK_CLUSTER_ALIVE && ZK_CONFIGS_ENABLED;
	}
	public static void setClusterAlive(boolean zK_CLUSTER_ALIVE) {
		ZK_CLUSTER_ALIVE = zK_CLUSTER_ALIVE;
	}
	
}
