package com.iot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.iot.bean.SensorData;
import com.iot.common.exception.BasicException;
import com.iot.common.utils.DateUtils;
import com.iot.mapper.SensorDataMapper;
import com.iot.service.ISensorDataHandler;

/**
 * 传感器数据处理实现类
 * 
 * @author gavin
 * 
 */
@Service("sensor")
public class BeaconDataHandlerSensor<T extends SensorData> implements
		ISensorDataHandler {

	@Autowired
	private SensorDataMapper<SensorData, String> dao;

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public void handlerSensorData(String frameId, String sourceMacAddress,
			String timeReceived, String humidity, String temp, String battery)
			throws BasicException {
		// 组装sensor data
		SensorData sData = new SensorData();
		sData.setFrameId(frameId);
		sData.setSourceMacAddress(sourceMacAddress);
		sData.setTimeReceived(DateUtils.str2TimestampV2(
				timeReceived).getTime());


		sData.setSensorType("humidity");
		sData.setSensorValue(humidity);
		dao.insert(sData);
		
		sData.setSensorType("tempurate");
		sData.setSensorValue(temp);
		dao.insert(sData);
		
		
		sData.setSensorType("battery");
		sData.setSensorValue(battery);
		dao.insert(sData);

	}
}
