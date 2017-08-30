package com.iot.core.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SqlMapper<T,ID> {
	public List<T> getList(Map<String, Object> param);

	public int getCount(Map<String, Object> param);

	public int insert(T entity);

	public void update(T entity);

	public void delete(@Param(value = "ids") ID[] ids);
	public void online(@Param(value = "ids") ID[] ids);
	public void offline(@Param(value = "ids") ID[] ids);
	
	public T load(ID id);

	public void refreshCache();

	public void logicDelete(@Param(value = "ids") ID[] ids);
	
	public void physicalDelete(@Param(value = "ids") ID[] ids);

	public int isExist(Map<String, Object> param);
	
	public void enable(@Param(value = "ids") String[] ids,
			@Param(value = "status") int status);
}
