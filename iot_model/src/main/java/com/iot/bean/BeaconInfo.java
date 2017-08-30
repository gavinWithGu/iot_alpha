package com.iot.bean;

import java.sql.Timestamp;

import com.iot.base.BaseEntity;
import com.iot.common.utils.validate.PojoValidate;
import com.iot.common.utils.validate.UsedScope;

public class BeaconInfo extends BaseEntity {

	// Fields
	@PojoValidate(description = "beacon id", dbUniqueName = "beaconId", maxLength = 32, minLength = 1, nullable = false, usedScope = UsedScope.ADD_UPDATE)
	private String beaconId;
	private String content;
	private String h5Url;
	private Short status;
	private String location;
	private Integer floor;
	private String picUrl;
	private Timestamp createdTime;
	private String rssi;
	private String ownerId;
	private String beaconOwnerName;
	private String beaconOwnerEmail;
	private String type;
	private String typeName;

	private String h5TmplId;
	private String addressDetail;
	private String boAddress;

	@PojoValidate(description = "beacon mac address", dbUniqueName = "beaconMacAddress", maxLength = 32, minLength = 1, nullable = false, usedScope = UsedScope.ADD_UPDATE)
	private String beaconMacAddress;
	private int applicationType;

	// Constructors

	/** default constructor */
	public BeaconInfo() {
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
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

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getBoAddress() {
		return boAddress;
	}

	public void setBoAddress(String boAddress) {
		this.boAddress = boAddress;
	}

	public String getBeaconMacAddress() {
		return beaconMacAddress;
	}

	public void setBeaconMacAddress(String beaconMacAddress) {
		this.beaconMacAddress = beaconMacAddress;
	}

	public int getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(int applicationType) {
		this.applicationType = applicationType;
	}

}