package com.iot.bean;

import com.iot.base.BaseEntity;

/**
 * BeaconUniqueInfo entity. @author MyEclipse Persistence Tools
 */

public class BeaconUniqueInfo  extends BaseEntity   {

	// Fields
	private String source_mac_address;
	private String ap_mac_address;
	private String frame_id;
	private long time_received;
	private String detected_rssi;
	private String payload;
	private String uniqueIndicatorPerSourceAp;
	private String uniqueIndicatorPersource;

	// Constructors

	/** default constructor */
	public BeaconUniqueInfo() {
	}

	public String getSource_mac_address() {
		return source_mac_address;
	}

	public void setSource_mac_address(String source_mac_address) {
		this.source_mac_address = source_mac_address;
	}

	public String getAp_mac_address() {
		return ap_mac_address;
	}

	public void setAp_mac_address(String ap_mac_address) {
		this.ap_mac_address = ap_mac_address;
	}

	public String getFrame_id() {
		return frame_id;
	}

	public void setFrame_id(String frame_id) {
		this.frame_id = frame_id;
	}

	public long getTime_received() {
		return time_received;
	}

	public void setTime_received(long time_received) {
		this.time_received = time_received;
	}

	public String getDetected_rssi() {
		return detected_rssi;
	}

	public void setDetected_rssi(String detected_rssi) {
		this.detected_rssi = detected_rssi;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getUniqueIndicatorPerSourceAp() {
		return uniqueIndicatorPerSourceAp;
	}

	public void setUniqueIndicatorPerSourceAp(String uniqueIndicatorPerSourceAp) {
		this.uniqueIndicatorPerSourceAp = uniqueIndicatorPerSourceAp;
	}

	public String getUniqueIndicatorPersource() {
		return uniqueIndicatorPersource;
	}

	public void setUniqueIndicatorPersource(String uniqueIndicatorPersource) {
		this.uniqueIndicatorPersource = uniqueIndicatorPersource;
	}

	/** full constructor */
	
}