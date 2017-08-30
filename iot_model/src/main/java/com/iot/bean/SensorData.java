package com.iot.bean;

import com.iot.base.BaseEntity;

/**
 * SensorDataId entity. @author MyEclipse Persistence Tools
 */

public class SensorData extends BaseEntity {

	// Fields

	private String sourceMacAddress;
	private String frameId;
	private long timeReceived;
	private String sensorType;
	private String sensorValue;

	// Constructors

	/** default constructor */
	public SensorData() {
	}

	/** full constructor */
	public SensorData(String sourceMacAddress, String frameId,
			long timeReceived, String sensorType, String sensorValue) {
		this.sourceMacAddress = sourceMacAddress;
		this.frameId = frameId;
		this.timeReceived = timeReceived;
		this.sensorType = sensorType;
		this.sensorValue = sensorValue;
	}

	// Property accessors

	public String getSourceMacAddress() {
		return this.sourceMacAddress;
	}

	public void setSourceMacAddress(String sourceMacAddress) {
		this.sourceMacAddress = sourceMacAddress;
	}

	public String getFrameId() {
		return this.frameId;
	}

	public void setFrameId(String frameId) {
		this.frameId = frameId;
	}

	public long getTimeReceived() {
		return this.timeReceived;
	}

	public void setTimeReceived(long timeReceived) {
		this.timeReceived = timeReceived;
	}

	public String getSensorType() {
		return this.sensorType;
	}
	
	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getSensorValue() {
		return this.sensorValue;
	}

	public void setSensorValue(String sensorValue) {
		this.sensorValue = sensorValue;
	}

}