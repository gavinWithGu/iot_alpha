package com.iot.vo;

import java.util.List;

import com.iot.base.BaseEntity;

public class BasePageResultVo<DTO extends BaseEntity> {
	private List<DTO> list;
	private int count;

	public List<DTO> getList() {
		return list;
	}

	public void setList(List<DTO> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
