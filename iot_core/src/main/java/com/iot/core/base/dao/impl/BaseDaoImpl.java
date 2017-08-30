package com.iot.core.base.dao.impl;

import java.util.List;
import java.util.Map;

import com.iot.base.BaseEntity;
import com.iot.core.base.dao.IBaseDao;

public abstract class BaseDaoImpl<T extends BaseEntity, ID extends Object>
		implements IBaseDao<T, ID> {

	@Override
	public List<T> getList(Map<String, Object> param) {
		return getSqlMapper().getList(param);
	}

	@Override
	public int getCount(Map<String, Object> param) {
		return getSqlMapper().getCount(param);
	}

	@Override
	public String insert(T entity) {
		getSqlMapper().insert(entity);
		return entity.getId();
	}
	
	@Override
	public int insertReturnRowcount(T entity) {
		return getSqlMapper().insert(entity);
	}

	@Override
	public void update(T entity) {
		getSqlMapper().update(entity);
	}

	@Override
	public void delete(ID[] ids) {
		getSqlMapper().delete(ids);
	}

	@Override
	public T load(ID id) {
		return getSqlMapper().load(id);
	}

	@Override
	public void logicDelete(ID[] ids) {
		getSqlMapper().logicDelete(ids);
	}

	@Override
	public int isExist(Map<String, Object> param) {
		return getSqlMapper().isExist(param);
	}

	@Override
	public void online(ID[] ids) {
		getSqlMapper().online(ids);
	}

	@Override
	public void offline(ID[] ids) {
		getSqlMapper().offline(ids);
	}
	// @Override
	// public void refreshCache() {
	// sqlMapper.refreshCache();
	// }

}
