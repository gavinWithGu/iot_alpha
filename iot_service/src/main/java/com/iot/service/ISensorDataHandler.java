package com.iot.service;

import com.iot.common.exception.BasicException;

/**
 * 
 * @author gavin
 * 
 */
public interface ISensorDataHandler {
	public void handlerSensorData(String frameId, String sourceMacAddress,
			String timeReceived, String humidity, String temp, String battery)
			throws BasicException;
}
