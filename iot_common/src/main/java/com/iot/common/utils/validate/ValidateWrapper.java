package com.iot.common.utils.validate;

import java.lang.reflect.Field;

import com.iot.common.utils.RegexType;
import com.iot.common.utils.RegexUtils;
import com.iot.common.utils.StringUtils;

/**
 * 注解解析
 */
public class ValidateWrapper {

	private static PojoValidate pv;

	public ValidateWrapper() {
		super();
	}

	// 解析的入口
	public static void valid(Object object) throws Exception {
		// 获取object的类型
		Class<? extends Object> clazz = object.getClass();
		// 获取该类型声明的成员
		Field[] fields = clazz.getDeclaredFields();
		// 遍历属性
		for (Field field : fields) {
			// 对于private私有化的成员变量，通过setAccessible来修改器访问权限
			field.setAccessible(true);
			validate(field, object);
			// 重新设置会私有权限
			field.setAccessible(false);
		}
	}

	public static void validate(Field field, Object object) throws Exception {

		String description;
		Object value;

		// 获取对象的成员的注解信息
		pv = field.getAnnotation(PojoValidate.class);
		value = field.get(object);

		if (pv == null)
			return;

		description = pv.description().equals("") ? field.getName() : pv
				.description();

		/************* 注解解析工作开始 ******************/
		if (!pv.nullable()) {
			if (value == null || StringUtils.isBlank(value.toString())) {
				throw new Exception(description + "不能为空");
			}
		}

		if (value.toString().length() > pv.maxLength() && pv.maxLength() != 0) {
			throw new Exception(description + "长度不能超过" + pv.maxLength());
		}

		if (value.toString().length() < pv.minLength() && pv.minLength() != 0) {
			throw new Exception(description + "长度不能小于" + pv.minLength());
		}

		if (pv.regexType() != RegexType.NONE) {
			switch (pv.regexType()) {
			case NONE:
				break;
			case SPECIALCHAR:
				if (RegexUtils.hasSpecialChar(value.toString())) {
					throw new Exception(description + "不能含有特殊字符");
				}
				break;
			case CHINESE:
				if (RegexUtils.isChinese2(value.toString())) {
					throw new Exception(description + "不能含有中文字符");
				}
				break;
			case EMAIL:
				if (!RegexUtils.isEmail(value.toString())) {
					throw new Exception(description + "地址格式不正确");
				}
				break;
			case IP:
				if (!RegexUtils.isIp(value.toString())) {
					throw new Exception(description + "地址格式不正确");
				}
				break;
			case NUMBER:
				if (!RegexUtils.isNumber(value.toString())) {
					throw new Exception(description + "不是数字");
				}
				break;
			case PHONENUMBER:
				if (!RegexUtils.isPhoneNumber(value.toString())) {
					throw new Exception(description + "不是数字");
				}
				break;
			default:
				break;
			}
		}

		if (!pv.regexExpression().equals("")) {
			if (value.toString().matches(pv.regexExpression())) {
				throw new Exception(description + "格式不正确");
			}
		}
		/************* 注解解析工作结束 ******************/
	}
}
