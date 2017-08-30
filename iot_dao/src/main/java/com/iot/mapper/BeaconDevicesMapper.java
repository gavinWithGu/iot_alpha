package com.iot.mapper;

import java.util.List;
import java.util.Map;

import com.iot.bean.BeaconInfo;
import com.iot.core.base.mapper.SqlMapper;

public interface BeaconDevicesMapper<T extends BeaconInfo, ID extends Object>
		extends SqlMapper<T, ID> {
	public List<BeaconInfo> getOwnshipList(Map<String, Object> param);

	public List<BeaconInfo> getBeaconTypeList(Map<String, Object> param);
	
	public int getOwnshipCount(Map<String, Object> param);

	public void updateOwnship(BeaconInfo entity);

	public BeaconInfo loadByBeaconId(String id);
	
	public BeaconInfo loadByMacAddress(ID id);
	
	public List<BeaconInfo> listByOwner(String ownerId);
}
