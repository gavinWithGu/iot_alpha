package com.iot.core.base.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.iot.base.BaseEntity;
import com.iot.common.exception.BasicException;
import com.iot.common.messagecode.MsgCode;
import com.iot.common.utils.GeneralUtils;
import com.iot.common.utils.RegexType;
import com.iot.common.utils.RegexUtils;
import com.iot.common.utils.StringUtils;
import com.iot.common.utils.validate.PojoValidate;
import com.iot.core.base.service.IBaseService;
import com.iot.util.ModelClassesHolder;

public abstract class BaseServiceValidation<T extends BaseEntity, DTO extends BaseEntity, ID extends Object>
		implements IBaseService<T, DTO, ID> {

	private PojoValidate pv;

	// 解析的入口:反射获取field
	protected void valid(BaseEntity object, String usedScope) throws Exception {
		// 获取object的类型
		Class<? extends BaseEntity> clazz = object.getClass();

		// 获取该类型声明的成员
		Field[] fields = clazz.getDeclaredFields();

		// Mybatits数据库查询<k-v>对象
		Map<String, Object> param = new HashMap<String, Object>();

		// 遍历属性
		for (Field field : fields) {
			// 对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			validate(field, object, param, usedScope);
			// 重新设置会私有权限
			field.setAccessible(false);
		}
	}

	// 解析的入口:从缓存中获取field，免去反射的步骤
	protected void validFromCache(BaseEntity object, String usedScope)
			throws Exception {
		
		// 获取该类型声明的成员
		Field[] fields = ModelClassesHolder.getInstance().getFieldByPoName(
				object.getClass().getName());

		// Mybatits数据库查询<k-v>对象
		Map<String, Object> param = new HashMap<String, Object>();

		// 遍历属性
		for (Field field : fields) {
			// 对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			validate(field, object, param, usedScope);
			// 重新设置会私有权限
			field.setAccessible(false);
		}
	}

	/**
	 * 校验方法
	 * 
	 * @param field
	 * @param object
	 * @param param
	 * @throws BasicException
	 */
	private void validate(Field field, BaseEntity object,
			Map<String, Object> param, String usedScope) throws BasicException {

		try {
			String description;
			Object value;

			// 获取对象的成员的注解信息
			pv = field.getAnnotation(PojoValidate.class);

			if (pv == null)
				return;

			// 当前字段是否需要校验
			if (GeneralUtils.isNotNullOrZeroLength(pv.usedScope())
					&& !pv.usedScope().contains(usedScope)) {
				return;
			}

			value = field.get(object);

			description = pv.description().equals("") ? field.getName() : pv
					.description();

			if (!pv.nullable()) {
				if (value == null || StringUtils.isBlank(value.toString())) {
					throw new BasicException(description + "不能为空");
				}
			}

			if (GeneralUtils.isNotNull(value)
					&& GeneralUtils.isNotNullOrZeroLength(value.toString())) {

				// 校验最小最大长度
				if (pv.maxLength() != 0
						&& value.toString().length() > pv.maxLength()) {
					throw new BasicException(description + "长度不能超过"
							+ pv.maxLength());
				}

				if (pv.minLength() != 0
						&& value.toString().length() < pv.minLength()) {
					throw new BasicException(description + "长度不能小于"
							+ pv.minLength());
				}

				// 校验常用正则表达式是否符合
				if (pv.regexType() != RegexType.NONE) {
					switch (pv.regexType()) {
					case NONE:
						break;
					case SPECIALCHAR:
						if (RegexUtils.hasSpecialChar(value.toString())) {
							throw new BasicException(description + "不能含有特殊字符");
						}
						break;
					case CHINESE:
						if (RegexUtils.isChinese2(value.toString())) {
							throw new BasicException(description + "不能含有中文字符");
						}
						break;
					case EMAIL:
						if (!RegexUtils.isEmail(value.toString())) {
							throw new BasicException(description + "地址格式不正确");
						}
						break;
					case IP:
						if (!RegexUtils.isIp(value.toString())) {
							throw new BasicException(description + "地址格式不正确");
						}
						break;
					case NUMBER:
						if (!RegexUtils.isNumber(value.toString())) {
							throw new BasicException(description + "格式不正确");
						}
						break;
					case PHONENUMBER:
						if (!RegexUtils.isPhoneNumber(value.toString())) {
							throw new BasicException(description + "格式不正确");
						}
						break;
					default:
						break;
					}
				}

				// 校验自定义的正则表达式是否符合
				if (!pv.regexExpression().equals("")) {
					if (value.toString().matches(pv.regexExpression())) {
						throw new BasicException(description + "格式不正确");
					}
				}

				// 校验唯一性
				if (GeneralUtils.isNotNullOrZeroLength(pv.dbUniqueName())) {
					param.clear();
					param.put("id", object.getId());
					param.put(pv.dbUniqueName(), value);
					int count = this.getBaseDao().isExist(param);
					if (count > 0) {
						throw new BasicException(description + "已存在!");
					}
				}

			}

		} catch (BasicException e) {
			throw e;
		} catch (Exception e) {
			throw new BasicException(MsgCode.MSGCODE_ERROR_VALIDATE, e);
		}

	}
}
