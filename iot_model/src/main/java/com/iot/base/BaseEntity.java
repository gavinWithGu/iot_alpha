package com.iot.base;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public String id;

	public <T extends BaseEntity, DTO extends BaseEntity> DTO convertToDto(
			T entity) throws Exception {
		BaseEntity dto = this;
		BeanUtils.copyProperties(entity, dto);
		return (DTO)dto;
	}

	public <T extends BaseEntity, DTO extends BaseEntity> T convertToPo(
			DTO entity) throws Exception {
		
		BaseEntity po = this;
		BeanUtils.copyProperties(entity, po);
		return (T)po;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
