package com.iot.processor.storm.access.resolve.impl;

import java.util.Map;

import com.iot.common.utils.JsonUtils;
import com.iot.common.utils.log.LogUtils;
import com.iot.processor.storm.access.resolve.IMessageResolver;

/**
 * 针对传感器进行json格式解析报文
 * 
 * @author gavin
 * 
 */
public class SensorMessageResolverJson implements IMessageResolver {

	/**
	 * 消息类型：data={
	 * 'source_mac_address':'112233','frame_id':'223344','time_received':'2017-04-
	 * 2 9
	 * 01:28:28.782694','detected_rssi':'-70','payload':'for','ap_mac_address
	 * ':' 112233445566'}
	 */
	public String resolve(String message) {
		try {

			// _source_mac_address|_ap_mac_address|_frame_id|_time_received|_rssi_at_receiving|battery|temp温度|humid湿度
			StringBuilder result = new StringBuilder();

			Map<String, String> mapJson = JsonUtils.getMapFromJson(message.substring(message.indexOf("=")+1));
			if (!mapJson.isEmpty()) {
				result.append(mapJson.get("source_mac_address"));
				result.append("|");
				result.append(mapJson.get("ap_mac_address"));
				result.append("|");
				result.append(mapJson.get("frame_id"));
				result.append("|");
				result.append(mapJson.get("time_received"));
				result.append("|");
				result.append(mapJson.get("detected_rssi"));
				result.append("|");

				String payload = mapJson.get("payload");
				String[] split = payload.split(";");
				
				String uuid = "";
				String transmit_rssi = "";
				for (String string : split) {
					String[] split2 = string.split("=");
					if ("uuid".equals(split2[0])){
						uuid = split2[1];
					}else if ("transmit_rssi".equals(split2[0])){
						transmit_rssi =  split2[1];
					}
					else{
						result.append(split2[1]);
						result.append("|");
					}
				}
				
				result.append(uuid);
				result.append("|");
				
				result.append(transmit_rssi);
				result.append("|");
				
				return result.toString();
			}
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem("SensorMessageResolverJson", e,
					message);
		}
		return null;
	}

}
