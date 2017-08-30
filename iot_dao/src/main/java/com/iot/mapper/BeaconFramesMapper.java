package com.iot.mapper;

import java.util.List;

import com.iot.bean.BeaconWifi;
import com.iot.core.base.mapper.SqlMapper;

public interface BeaconFramesMapper<T extends BeaconWifi, ID extends Object>
		extends SqlMapper<T, ID> {
	
	public List<BeaconWifi> getLatestMacList();

	public List<BeaconWifi> getLatestMacAPList();
}
