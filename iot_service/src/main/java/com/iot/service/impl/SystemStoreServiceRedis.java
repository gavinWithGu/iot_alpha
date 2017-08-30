package com.iot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.iot.bean.BeaconInfo;
import com.iot.bean.BeaconWifi;
import com.iot.common.exception.BasicException;
import com.iot.core.cache.redis.MyJedisPool;
import com.iot.mapper.BeaconDevicesMapper;
import com.iot.mapper.BeaconFramesMapper;
import com.iot.service.ISystemStoreService;

@Service("systemStoreServiceRedis")
public class SystemStoreServiceRedis implements ISystemStoreService {

	@Autowired
	private BeaconFramesMapper<BeaconWifi, String> beaconFramesMapper;

	@Autowired
	private BeaconDevicesMapper<BeaconInfo, String> beaconDevicesMapper;

	@Override
	public void prepareSystemData() throws BasicException {
		Jedis jedis = MyJedisPool.getInstance().getPool().getResource();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			// (1)<beacon_mac_address,beacon_type>键值对
			List<BeaconInfo> beaconTypeList = beaconDevicesMapper
					.getBeaconTypeList(param);
			for (BeaconInfo beaconInfo : beaconTypeList) {
				jedis.set(beaconInfo.getBeaconMacAddress(),
						String.valueOf(beaconInfo.getApplicationType()));
			}

			// (2)<beacon_mac_address+frame_id,最近处理时间>键值对
			List<BeaconWifi> latestMacList = beaconFramesMapper
					.getLatestMacList();
			for (BeaconWifi beaconWifi : latestMacList) {
				jedis.set(
						beaconWifi.getSource_mac_address()
								+ beaconWifi.getFrame_id(), String
								.valueOf(beaconWifi.getCloudReceivedTime()
										.getTime()));
			}
			// (3)<beacon_mac_address+ap_mac_address+frame_id,最近处理时间>键值对
			List<BeaconWifi> latestMacAPList = beaconFramesMapper
					.getLatestMacAPList();
			for (BeaconWifi beaconWifi : latestMacAPList) {
				jedis.set(
						beaconWifi.getSource_mac_address()
								+ beaconWifi.getAp_mac_address()
								+ beaconWifi.getFrame_id(), String
								.valueOf(beaconWifi.getCloudReceivedTime()
										.getTime()));
			}

		} finally {

			MyJedisPool.getInstance().returnResource(jedis);
		}
	}

	@Override
	public int refreshBeaconType(String beaconMacAddress) throws BasicException {

		Jedis jedis = MyJedisPool.getInstance().getPool().getResource();
		try {

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("beaconMacAddressEqual", beaconMacAddress);
			// (1)<beacon_mac_address,beacon_type>键值对
			List<BeaconInfo> beaconTypeList = beaconDevicesMapper
					.getBeaconTypeList(param);
			if (beaconTypeList.isEmpty()) {
				return -1;
			}
			BeaconInfo beaconInfo = beaconTypeList.get(0);
			jedis.set(beaconInfo.getBeaconMacAddress(),
					String.valueOf(beaconInfo.getApplicationType()));
			return beaconInfo.getApplicationType();
		} finally {

			MyJedisPool.getInstance().returnResource(jedis);
		}
	}

}
