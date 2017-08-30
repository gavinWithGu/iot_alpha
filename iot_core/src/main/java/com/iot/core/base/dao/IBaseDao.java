package com.iot.core.base.dao;

import java.util.List;
import java.util.Map;

import com.iot.core.base.mapper.SqlMapper;

public interface IBaseDao<T, ID> {

	public List<T> getList(Map<String, Object> param);

	public int getCount(Map<String, Object> param);

	public String insert(T entity);

	public void update(T entity);

	public void delete(ID[] ids);

	public T load(ID id);

	public SqlMapper<T, ID> getSqlMapper();

	public void logicDelete(ID[] ids);

	public int isExist(Map<String, Object> param);

	public void online(ID[] ids);

	public void offline(ID[] ids);
	
	int insertReturnRowcount(T entity);
	// public void refreshCache();
}
