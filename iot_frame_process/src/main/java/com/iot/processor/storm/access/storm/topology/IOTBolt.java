package com.iot.processor.storm.access.storm.topology;

import java.util.LinkedList;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iot.common.utils.log.LogUtils;
import com.iot.service.IBeaconDataHandler;

public class IOTBolt extends BaseRichBolt {
	OutputCollector _collector;
	LinkedList<String> messages;

	private IBeaconDataHandler bean;

	private static transient ApplicationContext contextSpring = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		_collector = collector;
		try {
			bean = (IBeaconDataHandler)contextSpring.getBean("abstractDataHandlerRedis");
			
//			BeanFactory.getInstance().setContext(contextSpring);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	public void execute(Tuple input) {
		// 接收到一个句子
		String inputStr = input.getString(0);

		LogUtils.getInstance().debugSystem("Bolt Received tuples", inputStr);

		try {
			String[] reportData = { input.getString(0), input.getString(1),
					input.getString(2), input.getString(3), input.getString(4),
					input.getString(5), input.getString(6), input.getString(7),input.getString(8), input.getString(9)};

			bean.handleBeaconFrames(reportData);

			_collector.emit(input, new Values(inputStr));
			_collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
			_collector.fail(input);
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 定义一个字段
		declarer.declare(new Fields("beacon_message"));
	}

}
