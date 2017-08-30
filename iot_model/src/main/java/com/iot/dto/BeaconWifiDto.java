package com.iot.dto;

import com.iot.base.BaseEntity;
import com.iot.bean.BeaconWifi;
import com.iot.common.utils.CopyUtils;

public class BeaconWifiDto extends BaseEntity {

	// Fields
	private String source_mac_address;
	private String frame_id;
	private String time_received;
	private int detected_rssi;
	private String payload;
	private String version;
	
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

	public int getDetected_rssi() {
		return detected_rssi;
	}

	public void setDetected_rssi(int detected_rssi) {
		this.detected_rssi = detected_rssi;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	@Override
	public <T extends BaseEntity, DTO extends BaseEntity> DTO convertToDto(
			T entity) throws Exception {

		BeaconWifiDto dto = new BeaconWifiDto();

		BeaconWifi po = (BeaconWifi) entity;
		CopyUtils.copyProperty(dto, po);
		return (DTO) dto;
	}

	@Override
	public <T extends BaseEntity, DTO extends BaseEntity> T convertToPo(
			DTO entity) throws Exception {
		BeaconWifiDto dto = (BeaconWifiDto) entity;
		BeaconWifi po = new BeaconWifi();
		CopyUtils.copyProperty(po, dto);
		return (T) po;
	}
}