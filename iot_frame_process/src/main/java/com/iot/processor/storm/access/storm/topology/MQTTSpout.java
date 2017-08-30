package com.iot.processor.storm.access.storm.topology;

import java.util.LinkedList;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.iot.common.utils.GeneralUtils;
import com.iot.common.utils.log.LogUtils;
import com.iot.processor.storm.access.resolve.BeaconMessageResolveHandlerDispacther;

public class MQTTSpout  implements MqttCallback, IRichSpout{
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
			client = new MqttClient(_broker_url, _client_id + this.toString());
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
				LogUtils.getInstance().debugSystem("Received tuple", message);
				
				String tuple = BeaconMessageResolveHandlerDispacther.getInstance().getResolver(BeaconMessageResolveHandlerDispacther.SENSOR_JSON).resolve(message);
				
				if (GeneralUtils.isNotNullOrZeroLength(tuple)) {
					String[] split = tuple.split("\\|");
					if (split.length == 10) {
						_collector.emit(new Values(split[0], split[1],
								split[2], split[3], split[4], split[5],
								split[6], split[7], split[8], split[9]));
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
		declarer.declare(new Fields("_source_mac_address", "_ap_mac_address",
				"_frame_id", "_time_received", "_rssi_at_receiving", "battery",
				"tempurate", "humidity","uuid","transmit_rssi"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
