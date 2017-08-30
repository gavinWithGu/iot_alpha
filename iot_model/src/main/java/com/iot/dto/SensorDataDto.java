package com.iot.dto;

import org.springframework.beans.BeanUtils;

import com.iot.base.BaseEntity;
import com.iot.bean.SensorData;
import com.iot.common.utils.CopyUtils;

/**
 * SensorDataId entity. @author MyEclipse Persistence Tools
 */

public class SensorDataDto extends BaseEntity {

	// Fields

	private String sourceMacAddress;
	private String frameId;
	private long timeReceived;
	private String sensorType;
	private String sensorValue;

	// Constructors

	/** default constructor */
	public SensorDataDto() {
	}

	/** full constructor */
	public SensorDataDto(String sourceMacAddress, String frameId,
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

	
	@Override
	public <T extends BaseEntity, DTO extends BaseEntity> DTO convertToDto(
			T entity) throws Exception {

		SensorDataDto dto = new SensorDataDto();
		SensorData po = (SensorData) entity;

		CopyUtils.copyProperty(dto, po);
	
		return (DTO) dto;
	}

	@Override
	public <T extends BaseEntity, DTO extends BaseEntity> T convertToPo(
			DTO entity) throws Exception {
		SensorDataDto dto = (SensorDataDto) entity;
		SensorData po = new SensorData();
		BeanUtils.copyProperties(dto, po);
		return (T) po;
	}
}