package com.iot.service;

import com.iot.common.exception.BasicException;

/**
 * 
 * @author gavin
 * 
 */
public interface ISystemStoreService {
	public void prepareSystemData() throws BasicException;

	public int refreshBeaconType(String beaconMacAddress) throws BasicException;
}
