package com.iot.mapper;

import java.util.List;
import java.util.Map;

import com.iot.bean.SensorData;
import com.iot.core.base.mapper.SqlMapper;

public interface SensorDataMapper<T extends SensorData, ID extends Object>
		extends SqlMapper<T, ID> {
	public List<SensorData> listlastestBatchSensorData(String source_mac_address);
	
	public List<SensorData> listHistoryBatchSensorData(Map<String, Object> param);
}
