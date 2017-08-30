package com.iot.processor.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iot.common.spring.context.BeanFactory;
import com.iot.core.wrapper.ConfigFileWrapper;
import com.iot.core.zookeeper.ZkConfig;
import com.iot.core.zookeeper.config.listener.IDynamicConfigFileListener;
import com.iot.core.zookeeper.config.wrapper.DynamicConfigFileListenerWrapper;
import com.iot.processor.storm.access.MqttTopology.IOTBolt;
import com.iot.processor.storm.access.MqttTopology.MQTTSpout;
import com.iot.service.ISystemStoreService;

public class Startup {

	public static void main(String[] args) throws Exception {

		// 1. 初始化zookeeper注册监听
		IDynamicConfigFileListener dynamicConfigFileListener = DynamicConfigFileListenerWrapper
				.getInstance().getListener();
		if (ZkConfig.isZK_CLUSTER_ALIVE()) {
			dynamicConfigFileListener.registerListener();
		}

		// 2. 加载spring context
		String[] locations = new String[] { "classpath:applicationContext.xml" };
		ApplicationContext _context = new ClassPathXmlApplicationContext(
				locations);
		BeanFactory.getInstance().setContext(_context);

		// 3. 加载内存数据库预处理数据:memcache处理方式
		ISystemStoreService systemStoreService = (ISystemStoreService) _context
				.getBean("systemStoreServiceRedis");
		systemStoreService.prepareSystemData();

		// 4. 启动storm topology
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("mqtt-spout", new MQTTSpout(
				ConfigFileWrapper.MQTT_URL, "storm-mqtt-test",
				ConfigFileWrapper.MQTT_TOPIC_NAME), 1);

		IOTBolt bolt = new IOTBolt();
		builder.setBolt("iot-process-bolt", bolt,
				ConfigFileWrapper.STORM_EXECUTOR_NUMBER).shuffleGrouping(
				"mqtt-spout");

		Config conf = new Config();
		conf.setDebug(false);

		if (args != null && args.length > 0) {
			System.out.print("[--------------]StormSubmitter Start\n");
			conf.setNumWorkers(ConfigFileWrapper.STORM_WORKER_NUMBER);
			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
			System.out.print("[--------------]StormSubmitter End\n");

		} else {
			System.out.print("[--------------]LocalCluster Start\n");
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("mqtt", conf, builder.createTopology());
			System.out.print("[--------------]LocalCluster End\n");
			Thread.sleep(6000000);// 睡眠完成以后关闭local cluster
			cluster.shutdown();
			System.out.print("[--------------]Cluster Shutdown\n");
		}

	}
}
