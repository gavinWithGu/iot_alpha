package com.iot.service;

import com.iot.bean.BeaconWifi;
import com.iot.common.exception.BasicException;
import com.iot.core.base.service.IBaseService;
import com.iot.dto.BeaconWifiDto;

/**
 * 处理ap上报的数据
 * 
 * @author gavin
 * 
 */
public interface IBeaconDataHandler extends
		IBaseService<BeaconWifi, BeaconWifiDto, String> {
	public void handleBeaconFrames(String[] report) throws BasicException;
}
