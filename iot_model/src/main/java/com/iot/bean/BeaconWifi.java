package com.iot.bean;

import java.sql.Timestamp;

import com.iot.base.BaseEntity;

public class BeaconWifi extends BaseEntity {

	// Fields
	private String source_mac_address;
	private String frame_id;
	private String time_received;
	private String detected_rssi;
	private String payload;
	private String ap_mac_address;
	
	private Timestamp cloudReceivedTime;

	private long time_received_db;

	private int isProcessed;

	public String getSource_mac_address() {
		return source_mac_address;
	}

	public void setSource_mac_address(String source_mac_address) {
		this.source_mac_address = source_mac_address;
	}

	public String getFrame_id() {
		return frame_id;
	}

	public void setFrame_id(String frame_id) {
		this.frame_id = frame_id;
	}

	public String getTime_received() {
		return time_received;
	}

	public void setTime_received(String time_received) {
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

	public String getAp_mac_address() {
		return ap_mac_address;
	}

	public void setAp_mac_address(String ap_mac_address) {
		this.ap_mac_address = ap_mac_address;
	}

	public long getTime_received_db() {
		return time_received_db;
	}

	public void setTime_received_db(long time_received_db) {
		this.time_received_db = time_received_db;
	}

	public int getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(int isProcessed) {
		this.isProcessed = isProcessed;
	}

	public Timestamp getCloudReceivedTime() {
		return cloudReceivedTime;
	}

	public void setCloudReceivedTime(Timestamp cloudReceivedTime) {
		this.cloudReceivedTime = cloudReceivedTime;
	}

}