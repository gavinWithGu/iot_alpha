package com.iot.dto;

import com.iot.base.BaseEntity;
import com.iot.bean.BeaconInfo;
import com.iot.common.utils.CommonUtils;
import com.iot.common.utils.CopyUtils;
import com.iot.common.utils.DateUtils;

public class BeaconInfoDto extends BaseEntity {

	// Fields
	private String beaconId;
	private String content;
	private String h5Url;
	private int status;
	private String location;
	private Integer floor;
	private String picUrl;
	private String createdTime;
	private String rssi;
	private String ownerId;
	private String beaconOwnerName;
	private String beaconOwnerEmail;
	private String type;
	private String typeName;
	private String h5TmplId;

	private String longitude;
	private String latitude;

	private String addressDetail;
	private String boAddress;
	
	private String beaconMacAddress;
	
	public String getBoAddress() {
		return boAddress;
	}

	public void setBoAddress(String boAddress) {
		this.boAddress = boAddress;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(String beaconId) {
		this.beaconId = beaconId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getH5Url() {
		return h5Url;
	}

	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}

	public int getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getRssi() {
		return rssi;
	}

	public void setRssi(String rssi) {
		this.rssi = rssi;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getBeaconOwnerName() {
		return beaconOwnerName;
	}

	public void setBeaconOwnerName(String beaconOwnerName) {
		this.beaconOwnerName = beaconOwnerName;
	}

	public String getH5TmplId() {
		return h5TmplId;
	}

	public void setH5TmplId(String h5TmplId) {
		this.h5TmplId = h5TmplId;
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public String getBeaconOwnerEmail() {
		return beaconOwnerEmail;
	}

	public void setBeaconOwnerEmail(String beaconOwnerEmail) {
		this.beaconOwnerEmail = beaconOwnerEmail;
	}

	public String getBeaconMacAddress() {
		return beaconMacAddress;
	}

	public void setBeaconMacAddress(String beaconMacAddress) {
		this.beaconMacAddress = beaconMacAddress;
	}

	@Override
	public <T extends BaseEntity, DTO extends BaseEntity> DTO convertToDto(
			T entity) throws Exception {

		BeaconInfoDto dto = new BeaconInfoDto();

		BeaconInfo po = (BeaconInfo) entity;

		CopyUtils.copyProperty(dto, po);
		if (CommonUtils.isNotEmpty(po.getCreatedTime())) {
			dto.setCreatedTime(DateUtils.time2String(po.getCreatedTime()));
		}
		return (DTO) dto;
	}

	@Override
	public <T extends BaseEntity, DTO extends BaseEntity> T convertToPo(
			DTO entity) throws Exception {
		BeaconInfoDto dto = (BeaconInfoDto) entity;
		BeaconInfo po = new BeaconInfo();
		CopyUtils.copyProperty(po, dto);
		return (T) po;
	}
}