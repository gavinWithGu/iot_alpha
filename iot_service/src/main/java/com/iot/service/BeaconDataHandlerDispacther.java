package com.iot.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class BeaconDataHandlerDispacther {

	public final static int SENSOR = 1;

	@Resource(name = "sensor")
	private ISensorDataHandler sensor;

	public ISensorDataHandler getHandler(int type) {
		switch (type) {
		case SENSOR:
			return sensor;
		}
		return sensor;
	}

	/**
	 * 私有构造方法，防止单例类被实例化
	 */
	private BeaconDataHandlerDispacther() {
	}

	private static class SingleHolder {
		private static BeaconDataHandlerDispacther instance = new BeaconDataHandlerDispacther();
		static {
			System.out.println("Instance init !");
		}
	}

	public static BeaconDataHandlerDispacther getInstance() {
		return SingleHolder.instance;
	}
}
