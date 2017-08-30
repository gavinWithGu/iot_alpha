package com.iot.core.base.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.iot.base.BaseEntity;
import com.iot.common.exception.BasicException;
import com.iot.common.messagecode.MsgCode;
import com.iot.common.utils.log.LogUtils;
import com.iot.common.utils.validate.UsedScope;
import com.iot.util.ModelClassesHolder;
import com.iot.vo.BasePageResultVo;

public abstract class BaseServiceCRUD<T extends BaseEntity, DTO extends BaseEntity, ID extends Object>
		extends BaseServiceValidation<T, DTO, ID> {

	// public abstract void checkValidForSave(DTO dto) throws BasicException;
	//
	// public abstract void checkValidForModify(DTO dto) throws BasicException;

	private static final String DYNAMIC_WORD_CONTENT = "word";
	private static final String DYNAMIC_PIC_CONTENT = "pic";
	private static final String DYNAMIC_CONTENT = "dynamicContent";

	@SuppressWarnings("unchecked")
	protected T transferToPo(DTO dto) throws Exception {
		BaseEntity entity = (BaseEntity) dto;

		Class<? extends BaseEntity> clz = ModelClassesHolder.getInstance()
				.getClassByDtoName(entity.getClass().getName());

		BaseEntity result = (BaseEntity) clz.getMethod("convertToPo",
				BaseEntity.class).invoke(clz.newInstance(), dto);

		return (T) result;
	}

	@SuppressWarnings("unchecked")
	protected DTO transferToDto(T t) throws Exception {
		if (null == t)
			return null;

		BaseEntity entity = (BaseEntity) t;

		Class<? extends BaseEntity> clz = ModelClassesHolder.getInstance()
				.getClassByPoName(entity.getClass().getName());

		Method method = clz.getMethod("convertToDto", BaseEntity.class);

		BaseEntity result = (BaseEntity) method.invoke(clz.newInstance(), t);

		return (DTO) result;
	}

	@Override
	public BasePageResultVo<DTO> pageFind(Map<String, Object> param)
			throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute pageFind method pageFind()",
					"Service name", this.getClass(), "Param", param);

			List<T> page = getBaseDao().getList(param);

			List<DTO> list = new ArrayList<DTO>();

			for (T po : page) {
				list.add(this.transferToDto(po));
			}
			int count = getBaseDao().getCount(param);

			BasePageResultVo<DTO> result = new BasePageResultVo<DTO>();
			result.setCount(count);
			result.setList(list);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Finish executing pageFind method", "Service name",
					this.getClass(), "Count", count);

			return result;
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_ADD,
					"Find by page error!", e);
		}
	}
	
	
	@Override
	public List<DTO> find(Map<String, Object> param)
			throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute find method pageFind()",
					"Service name", this.getClass(), "Param", param);

			List<T> page = getBaseDao().getList(param);

			List<DTO> list = new ArrayList<DTO>();

			for (T po : page) {
				list.add(this.transferToDto(po));
			}


			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Finish executing find method", "Service name",
					this.getClass());

			return list;
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_ADD,
					"Find by page error!", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ID save(DTO dto) throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute save method", "Service name",
					this.getClass(), "Dto name", dto);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Convert from dto to bean");
			// dto转换成po
			T po = transferToPo(dto);

			// 校验合法性
			this.validFromCache(po, UsedScope.ADD);
			// this.checkValidForSave(dto);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Save bean to DB", "Pojo", po);

			// 执行插入操作
			getBaseDao().insert(po);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Save successfull, return id to controller", po.getId());
			return (ID) po.getId();
		} catch (BasicException e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_ADD,
					e.getErrorMsg(), e);
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_ADD,
					"Save data error!", e);
		}

	}

	@Override
	public void modify(DTO dto) throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute modify method", "Service name",
					this.getClass().toString());

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Convert from dto to bean");

			T po = this.transferToPo(dto);

			this.validFromCache(po, UsedScope.UPDATE);
			// this.checkValidForModify(dto);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Update bean to DB");
			getBaseDao().update(po);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Update successfull, return id to controller", po.getId());

		} catch (BasicException e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_ADD,
					e.getErrorMsg(), e);
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_UPDATE,
					"Modify data error!", e);
		}
	}

	@Override
	public DTO load(ID id) throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute load(ID id) method", "Service name",
					this.getClass().toString(), "id", id);

			T bean = getBaseDao().load(id);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Find dto by id");

			DTO dto = this.transferToDto(bean);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Convert from bean to dto");
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Finish executing load(ID id) method.", dto);
			return dto;

		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_QUERY,
					"Find data error!", e);
		}
	}

	@Override
	public void remove(ID[] ids) throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute delete(ID[] ids) method", "Service name",
					this.getClass().toString(), "ids", ids);

			getBaseDao().delete(ids);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Finish deleting data", "Service name",
					this.getClass().toString(), "ids", ids);
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_DELETE,
					"Delete data error!", e);
		}
	}

	@Override
	public void online(ID[] ids) throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute online(ID[] ids) method", "Service name",
					this.getClass().toString(), "ids", ids);

			getBaseDao().online(ids);
			
			getBaseDao().load(ids[0]);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Finish online", "Service name",
					this.getClass().toString(), "ids", ids);
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_DELETE,
					"Delete data error!", e);
		}
	}

	@Override
	public void offline(ID[] ids) throws BasicException {
		try {
			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Begin to execute online(ID[] ids) method", "Service name",
					this.getClass().toString(), "ids", ids);

			getBaseDao().offline(ids);

			LogUtils.getInstance().debugSystem(LogUtils.MODULE_SERVICE,
					"Finish online", "Service name",
					this.getClass().toString(), "ids", ids);
		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
			throw new BasicException(MsgCode.MSGCODE_ERROR_DELETE,
					"Delete data error!", e);
		}
	}

	protected void copyFolder(File src, File dest) throws IOException {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String files[] = src.list();
			for (String file : files) {
				File srcFile = new File(src, file);
				File destFile = new File(dest, file);
				// 递归复制
				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buffer = new byte[1024];

			int length;

			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();
		}
	}

	protected void replaceContent(File in, String path, Object[] wordContents,
			Object[] picContents) throws IOException {
		Document doc = Jsoup.parse(in, "UTF-8", path);

		Elements words = doc.getElementsByAttribute(DYNAMIC_CONTENT);

		int i = 0;
		int j = 0;
		for (Element element : words) {
			String contentFlag = element.attr(DYNAMIC_CONTENT);

			if (DYNAMIC_WORD_CONTENT.equals(contentFlag)
					&& wordContents.length > i) {
				element.appendText(wordContents[i++].toString());
			} else if (DYNAMIC_PIC_CONTENT.equals(contentFlag)
					&& picContents.length > j) {
				element.attr("src", picContents[j++].toString());
			}
		}

		FileOutputStream fos = null;
		Writer out = null;
		try {
			fos = new FileOutputStream(path, false);
			out = new OutputStreamWriter(fos, "UTF-8");
			out.write(doc.toString());

		} catch (Exception e) {
			LogUtils.getInstance().errorSystem(LogUtils.MODULE_SERVICE, e);
		} finally {
			if (null != out)
				out.close();

			if (null != fos)
				fos.close();
		}

	}
}
