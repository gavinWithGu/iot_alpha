package com.iot.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.iot.bean.BeaconUniqueInfo;
import com.iot.bean.BeaconWifi;
import com.iot.common.exception.BasicException;
import com.iot.common.utils.DateUtils;
import com.iot.common.utils.GeneralUtils;
import com.iot.core.base.mapper.SqlMapper;
import com.iot.core.base.service.impl.BaseServiceCRUD;
import com.iot.core.cache.redis.MyJedisPool;
import com.iot.core.wrapper.ConfigFileWrapper;
import com.iot.dto.BeaconWifiDto;
import com.iot.mapper.BeaconFramesMapper;
import com.iot.mapper.BeaconUniqueInfoMapper;
import com.iot.service.BeaconDataHandlerDispacther;
import com.iot.service.IBeaconDataHandler;
import com.iot.service.ISensorDataHandler;
import com.iot.service.ISystemStoreService;

@Service("abstractDataHandlerRedis")
public class AbstractDataHandler extends
		BaseServiceCRUD<BeaconWifi, BeaconWifiDto, String> implements
		IBeaconDataHandler {

	@Autowired
	private BeaconUniqueInfoMapper<BeaconUniqueInfo, String> beaconUniqueInfoMapper;

	@Autowired
	private BeaconFramesMapper<BeaconWifi, String> beaconFramesMapper;

	@Resource(name = "systemStoreServiceRedis")
	private ISystemStoreService systemStoreService;

	private byte[] lock = new byte[0];

	private byte[] mockValue = new byte[0];

	@Autowired
	private BeaconDataHandlerDispacther handlerDispatcher;

	private void handleSourceMacFrame(Jedis jedis, Timestamp currentTs,
			String key, String frameId, String sourceMacAddress,
			String timeReceived, String humidity, String tempurate,
			String battery) throws BasicException {
		String lastestSourceFrameObj = jedis.get(key);

		// 最近上报时间为空,则说明source_mac+frame_id记录未曾上报过,则先插入缓存,再直接插入sensor data表
		if (GeneralUtils.isNullOrZeroLenght(lastestSourceFrameObj)) {
			jedis.set(key, String.valueOf(currentTs.getTime()));

			String typeObj = jedis.get(sourceMacAddress);
			int type;
			if (GeneralUtils.isNullOrZeroLenght(typeObj)) {
				type = systemStoreService.refreshBeaconType(sourceMacAddress);
			} else {
				type = Integer.valueOf(typeObj);
			}
			ISensorDataHandler handler = handlerDispatcher.getHandler(type);
			System.out.println("=========" + type);
			handler.handlerSensorData(frameId, sourceMacAddress, timeReceived,
					humidity, tempurate, battery);
		}
		// 否则,需要判断interval
		else {
			Timestamp lastestSourceFrame = new Timestamp(
					Long.valueOf(lastestSourceFrameObj));
			int minusSecond = DateUtils.minusSecond(currentTs,
					lastestSourceFrame);
			// 在interval之外,则说明需要插入sensor data表,更新memcache中的缓存记录
			if (minusSecond > ConfigFileWrapper.QNIQUE_BEACON_ROUND_INTERVAL) {

				jedis.set(key, String.valueOf(currentTs.getTime()));

				int type;
				String typeObj = jedis.get(sourceMacAddress);
				if (GeneralUtils.isNullOrZeroLenght(typeObj)) {
					type = systemStoreService
							.refreshBeaconType(sourceMacAddress);
				} else {
					type = Integer.valueOf(typeObj);
				}
				ISensorDataHandler handler = handlerDispatcher.getHandler(type);
				handler.handlerSensorData(frameId, sourceMacAddress,
						timeReceived, humidity, tempurate, battery);
			}
		}
	}

	private void handleSourceMacApFrame(Jedis jedis, Timestamp currentTs,
			String key, String frameId, String sourceMacAddress,
			String apMacAddress, String timeReceived, String rssiAtReceiving,
			String humidity, String tempurate, String battery, String payload)
			throws BasicException {
		String lastestSourceApFrameObj = jedis.get(key);
		// 为空,则说明source_mac+ap+frame_id记录未曾上报过,则先插入缓存,再直接插入sensor data表
		if (GeneralUtils.isNullOrZeroLenght(lastestSourceApFrameObj)) {
			jedis.set(key, String.valueOf(currentTs.getTime()));

			BeaconUniqueInfo entity = new BeaconUniqueInfo();
			entity.setAp_mac_address(apMacAddress);
			entity.setDetected_rssi(rssiAtReceiving);
			entity.setFrame_id(frameId);
			entity.setPayload(payload);
			entity.setSource_mac_address(sourceMacAddress);
			entity.setTime_received(DateUtils.str2TimestampV2(timeReceived)
					.getTime());
			beaconUniqueInfoMapper.insert(entity);
		}
		// 否则,需要判断interval
		else {
			Timestamp lastestSourceFrame = new Timestamp(
					Long.valueOf(lastestSourceApFrameObj));
			int minusSecond = DateUtils.minusSecond(currentTs,
					lastestSourceFrame);
			// 在interval之外,则说明需要插入sensor data表,更新memcache中的缓存记录
			if (minusSecond > ConfigFileWrapper.QNIQUE_BEACON_ROUND_INTERVAL) {
				jedis.set(key, String.valueOf(currentTs.getTime()));
				BeaconUniqueInfo entity = new BeaconUniqueInfo();
				entity.setAp_mac_address(apMacAddress);
				entity.setDetected_rssi(rssiAtReceiving);
				entity.setFrame_id(frameId);
				entity.setPayload(humidity + "|" + tempurate + "|" + battery);
				entity.setSource_mac_address(sourceMacAddress);
				entity.setTime_received(DateUtils.str2TimestampV2(timeReceived)
						.getTime());
				beaconUniqueInfoMapper.insert(entity);

			}
		}
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public void handleBeaconFrames(String[] reportData) throws BasicException {

		String sourceMacAddress = reportData[0];
		String apMacAddress = reportData[1];
		String frameId = reportData[2];
		String timeReceived = reportData[3];
		String rssiAtReceiving = reportData[4];
		String battery = reportData[5];
		String tempurate = reportData[6];
		String humidity = reportData[7];

		String uuid =  reportData[8];
		String transmit_rssi =  reportData[9];

//		String payload = humidity + "|" + tempurate + "|" + battery;
		
		String payload = "uuid="+uuid+";transmit_rssi="+transmit_rssi+";battery="+battery+";humdity="+humidity+";temperature="+tempurate+";" ;
		 
		Timestamp currentTs = DateUtils.getCurrentTimestamp();
		
		Timestamp currentTsFromAp =  DateUtils.str2TimestampV2(
				timeReceived);

		String key = sourceMacAddress + frameId;

		String keyLock = key + "-lock";  //redis分布式锁:sourceMacAddress + frameId

		Jedis jedis = MyJedisPool.getInstance().getPool().getResource();
		try {
			// 使用jedis缓存锁，进行数据锁定,超时时间暂定180秒
			Long i = jedis.setnx(keyLock, String.valueOf(mockValue));
			while (true) {
				if (i == 1) {
					// 设置默认的超时时间，防止死锁
					jedis.expire(keyLock, 60);
					break;
				} else {
					synchronized (lock) {
						lock.wait(2);
						i = jedis.setnx(keyLock, String.valueOf(mockValue));
					}
				}
			}
			// 计算与存储source_mac+frame_id:sensor_data
			this.handleSourceMacFrame(jedis, currentTsFromAp, key, frameId,
					sourceMacAddress, timeReceived, humidity, tempurate,
					battery);

			key = sourceMacAddress + apMacAddress + frameId;
			
			// 处理unique_ap_beacon_frames
			this.handleSourceMacApFrame(jedis, currentTsFromAp, key, frameId,
					sourceMacAddress, apMacAddress, timeReceived,
					rssiAtReceiving, humidity, tempurate, battery, payload);

			// 记录ap上报记录：插入beacon_frames表
			BeaconWifi beaconWifi = new BeaconWifi();
			beaconWifi.setAp_mac_address(apMacAddress);
			beaconWifi.setCloudReceivedTime(currentTs);
			beaconWifi.setDetected_rssi(rssiAtReceiving);
			beaconWifi.setFrame_id(frameId);
			beaconWifi.setSource_mac_address(sourceMacAddress);
			beaconWifi.setPayload(payload);
			beaconWifi.setTime_received_db(DateUtils.str2TimestampV2(
					timeReceived).getTime());
			beaconFramesMapper.insert(beaconWifi);
		} catch (BasicException e) {
			throw new BasicException(-1, e.getErrorMsg());
		} catch (Exception e) {
			throw new BasicException(-1, e);
		} finally {
			//释放锁
			if (GeneralUtils.isNotNull(jedis)) {
				jedis.del(keyLock);
				MyJedisPool.getInstance().returnResource(jedis);
			}
		}
	}

	@Override
	public SqlMapper<BeaconWifi, String> getBaseDao() throws BasicException {
		// TODO Auto-generated method stub
		return null;
	}

}
