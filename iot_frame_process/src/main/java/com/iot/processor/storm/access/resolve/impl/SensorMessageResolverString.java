package com.iot.processor.storm.access.resolve.impl;

import com.iot.common.utils.log.LogUtils;
import com.iot.processor.storm.access.resolve.IMessageResolver;

/**
 * 针对传感器进行json格式解析报文
 * 
 * @author gavin
 * 
 */
public class SensorMessageResolverString implements IMessageResolver {

	/**
	 * 消息类型： _source_mac_address|_ap_mac_address|_frame_id|_time_received|
	 * _rssi_at_receiving|battery|temp温度|humid湿度
	 */
	public String resolve(String message) {
		try {
			return message;
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem("SensorMessageResolverJson", e,
					message);
		}
		return null;
	}

}
