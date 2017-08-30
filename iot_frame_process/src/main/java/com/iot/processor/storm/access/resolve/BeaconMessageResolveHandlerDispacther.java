package com.iot.processor.storm.access.resolve;

import com.iot.processor.storm.access.resolve.impl.SensorMessageResolverJson;
import com.iot.processor.storm.access.resolve.impl.SensorMessageResolverString;


public class BeaconMessageResolveHandlerDispacther {

	public final static int SENSOR_JSON = 1;
	public final static int SENSOR_STRING = 2;
	
	private IMessageResolver sensorJsonresolver =new SensorMessageResolverJson();
	private IMessageResolver sensorStringresolver =new SensorMessageResolverString();

	public IMessageResolver getResolver(int type) {
		switch (type) {
		case SENSOR_JSON:
			return sensorJsonresolver;
		case SENSOR_STRING:
			return sensorStringresolver;
		}
		return sensorJsonresolver;
	}

	private BeaconMessageResolveHandlerDispacther() {
	}

	private static class SingleHolder {
		private static BeaconMessageResolveHandlerDispacther instance = new BeaconMessageResolveHandlerDispacther();
		static {
			System.out.println("Instance init !");
		}
	}

	public static BeaconMessageResolveHandlerDispacther getInstance() {
		return SingleHolder.instance;
	}
}
