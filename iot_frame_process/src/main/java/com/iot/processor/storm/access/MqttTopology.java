package com.iot.processor.storm.access;

import java.util.LinkedList;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iot.common.spring.context.BeanFactory;
import com.iot.common.utils.GeneralUtils;
import com.iot.common.utils.log.LogUtils;
import com.iot.processor.storm.access.resolve.BeaconMessageResolveHandlerDispacther;
import com.iot.service.impl.AbstractDataHandler;

public class MqttTopology {

	public static class MQTTSpout implements MqttCallback, IRichSpout {
		MqttClient client;
		SpoutOutputCollector _collector;
		LinkedList<String> messages;

		String _broker_url;
		String _client_id;
		String _topic;

		public MQTTSpout(String broker_url, String clientId, String topic) {
			_broker_url = broker_url;
			_client_id = clientId;
			_topic = topic;
			messages = new LinkedList<String>();
		}

		public void messageArrived(String topic, MqttMessage message)
				throws Exception {
			messages.add(message.toString());
		}

		public void connectionLost(Throwable cause) {
		}

		public void deliveryComplete(IMqttDeliveryToken token) {
		}

		public void open(Map conf, TopologyContext context,
				SpoutOutputCollector collector) {
			_collector = collector;

			try {
				client = new MqttClient(_broker_url, _client_id
						+ this.toString());
				client.connect();
				client.setCallback(this);
				client.subscribe(_topic);

			} catch (MqttException e) {
				e.printStackTrace();
			}
		}

		public void close() {
		}

		public void activate() {
		}

		public void deactivate() {
		}

		public void nextTuple() {
			try {
				while (!messages.isEmpty()) {
					String message = messages.poll();
					LogUtils.getInstance().debugSystem("Received tuple",
							message);
					
					String tuple = BeaconMessageResolveHandlerDispacther.getInstance().getResolver(BeaconMessageResolveHandlerDispacther.SENSOR_JSON).resolve(message);
					
					if (GeneralUtils.isNotNullOrZeroLength(tuple)) {
						String[] split = message.split("\\|");
						if (split.length == 8) {
							_collector.emit(new Values(split[0], split[1],
									split[2], split[3], split[4], split[5],
									split[6], split[7]));
						}
					}
				}
			} catch (Exception e) {
				LogUtils.getInstance().errorSystem("Received tuple error", e);

			}

		}

		public void ack(Object msgId) {
		}

		public void fail(Object msgId) {
		}

		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			// 定义一个字段
			declarer.declare(new Fields("_source_mac_address",
					"_ap_mac_address", "_frame_id", "_time_received",
					"_rssi_at_receiving", "battery", "tempurate", "humidity"));
		}

		public Map<String, Object> getComponentConfiguration() {
			return null;
		}
	}

	public static class IOTBolt extends BaseRichBolt {
		OutputCollector _collector;
		private AbstractDataHandler bean;
		
		private static transient ApplicationContext contextSpring = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
		private void initBean(){
			bean = (AbstractDataHandler) contextSpring
					.getBean("abstractDataHandlerRedis");
		}
		
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			_collector = collector;

			try {
				this.initBean();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}

		public void execute(Tuple input) {

			// 接收到一个句子
			String inputStr = input.getString(0);

			LogUtils.getInstance()
					.debugSystem("Bolt Received tuples", inputStr);

			try {
				String[] reportData = { input.getString(0), input.getString(1),
						input.getString(2), input.getString(3),
						input.getString(4), input.getString(5),
						input.getString(6), input.getString(7) };

				System.out.println("===========" + bean);
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

}
