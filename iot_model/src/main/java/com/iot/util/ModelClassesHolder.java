package com.iot.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.iot.base.BaseEntity;
import com.iot.common.utils.log.LogUtils;

public class ModelClassesHolder {

	private static final String BASE_DTO_PACKAGE = "com.cloudParking.dto";

	private static final String BASE_PO_PACKAGE = "com.cloudParking.bean";

	//<dto包全名，dto Class对象>
	private static Map<String, Class<? extends BaseEntity>> dtoClassHolder = new HashMap<String, Class<? extends BaseEntity>>();

	//<dto包全名，dto Class对象>
//	private static Map<String, Class<? extends BaseEntity>> poClassHolderDtoName = new HashMap<String, Class<? extends BaseEntity>>();
	
	private static Map<String, Class<? extends BaseEntity>> poClassHolder = new HashMap<String, Class<? extends BaseEntity>>();

	private static Map<String, Field[]> poFieldHolder = new HashMap<String, Field[]>();

	/**
	 * 私有构造方法，方式单例类被实例化
	 */
	private ModelClassesHolder() {
	}

	/**
	 * 私有类，包装单例类的对象，防止在加载时，单例对象被提前加载
	 * 
	 * @author gavin
	 * 
	 */
	private static class SingleHolder {
		private static ModelClassesHolder instance = new ModelClassesHolder();
		static {
			System.out.println("Instance init");
		}
	}

	/**
	 * 单例对象获取方法，第一次调用时，SingleHolder会加载，并且把instance实例化出来
	 * 
	 * @return
	 */
	public static ModelClassesHolder getInstance() {
		return SingleHolder.instance;
	}

	public void init() {
		// 扫描并反射dto
		Set<Class<?>> classes = ScanPackage.getClasses(BASE_DTO_PACKAGE);

		LogUtils.getInstance().debugSystem("Common", "Load dto classes from ",
				BASE_DTO_PACKAGE);

		for (Class<?> clz : classes) {
			LogUtils.getInstance().debugSystem("Common",
					BASE_DTO_PACKAGE + " Class:", clz.getName());

			boolean isFather = BaseEntity.class.isAssignableFrom(clz);

			if (isFather) {
				dtoClassHolder.put(clz.getName(),
						(Class<? extends BaseEntity>) clz);
				
				String poFakeName =clz.getName().replaceAll("dto", "bean");
				poClassHolder.put(poFakeName.substring(0,poFakeName.indexOf("Dto")),
						(Class<? extends BaseEntity>) clz);
			}
		}

		// 扫描并反射po
		classes = ScanPackage.getClasses(BASE_PO_PACKAGE);

		LogUtils.getInstance().debugSystem("Common", "Load dto classes from ",
				BASE_PO_PACKAGE);

		for (Class<?> clz : classes) {
			LogUtils.getInstance().debugSystem("Common",
					BASE_PO_PACKAGE + " Class:", clz.getName());

			boolean isFather = BaseEntity.class.isAssignableFrom(clz);

			if (isFather) {
				poFieldHolder.put(clz.getName(), clz.getDeclaredFields());
				
				
			}
		}

	}

	public Class<? extends BaseEntity> getClassByDtoName(String name) {
		return dtoClassHolder.get(name);
	}

	public Class<? extends BaseEntity> getClassByPoName(String name) {
		return poClassHolder.get(name);
	}
	
	public Field[] getFieldByPoName(String name) {
		return poFieldHolder.get(name);
	}
}
