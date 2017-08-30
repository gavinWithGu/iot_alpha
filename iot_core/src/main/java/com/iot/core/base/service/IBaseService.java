package com.iot.core.base.service;

import java.util.List;
import java.util.Map;

import com.iot.base.BaseEntity;
import com.iot.common.exception.BasicException;
import com.iot.core.base.mapper.SqlMapper;
import com.iot.vo.BasePageResultVo;

public interface IBaseService<T extends BaseEntity, DTO extends BaseEntity, ID extends Object> {

	/**
	 * 获取列表
	 * @param param
	 * @return
	 * @throws BasicException
	 */
	public BasePageResultVo<DTO> pageFind(Map<String, Object> param) throws BasicException;

	/**
	 * 执行插入操作
	 * @param entity
	 * @return
	 * @throws BasicException
	 */
	public ID save(DTO entity) throws BasicException;

	/**
	 * 执行更新操作
	 * @param entity
	 * @return
	 * @throws BasicException
	 */
	public void modify(DTO entity) throws BasicException;

	/**
	 * 删除
	 * 
	 * @param ids
	 * @throws BasicException
	 */
	public void remove(ID[] ids) throws BasicException;

	/**
	 * 获取详情
	 * @param id
	 * @return
	 * @throws BasicException
	 */
	public DTO load(ID id) throws BasicException;

	/**
	 * 获取basedao
	 * @return
	 * @throws BasicException
	 */
	public SqlMapper<T, ID> getBaseDao() throws BasicException;
	
	public void online(ID[] ids) throws BasicException;
	
	public void offline(ID[] ids) throws BasicException;
	
	List<DTO> find(Map<String, Object> param)
			throws BasicException ;
}