package com.iot.common.utils.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.iot.common.utils.RegexType;

/**
 * 数据验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface PojoValidate {

	// 是否可以为空:true：可以，false：不能为空
	boolean nullable() default true;

	// 最大长度:0不作校验
	int maxLength() default 0;

	// 最小长度:0不作校验
	int minLength() default 0;

	// 提供几种常用的正则验证
	RegexType regexType() default RegexType.NONE;

	// 自定义正则验证
	String regexExpression() default "";

	// 数据库该字段是否唯一
	String dbUniqueName() default "";

	// 字段描述
	String description() default "";
	
	// 适用范围
	String usedScope() default "";
}
