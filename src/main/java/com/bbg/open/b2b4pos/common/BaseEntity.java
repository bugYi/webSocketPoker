package com.bbg.open.b2b4pos.common;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BaseEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	
	@SuppressWarnings("rawtypes")
	private static Object getJsonValue(Object value) throws Exception {
		if (value == null) {
			return value;
		}
		if (value instanceof BaseEntity) {
			value = BaseEntity.json((BaseEntity)value);
			return value;
		}
		if (value instanceof Collection) {
			JSONArray array = new JSONArray();
			Collection source = (Collection) value;
			for (Object o : source) {
				array.add(getJsonValue(o));
			}
			return array;
		}
		return value;
	}
	
	/**
	 * 转换为json对象
	 * @param source
	 * @return
	 */
	public static  <T extends BaseEntity> JSONObject json(T source){
		return json(source,null);
	}
	
	/**
	 * 指定字段转换为json对象
	 * @param source
	 * @param fields
	 * @return
	 */
	public static  <T extends BaseEntity> JSONObject json(T source,Collection<String> fields){
		try {
			JSONObject json = new JSONObject();
			for (Class<?> clazz = source.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
				for (Field field : clazz.getDeclaredFields()) {
					if(fields != null){
						if(!fields.contains(field.getName())){
							continue;
						}
					}
					int modifiers = field.getModifiers();
					if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
						continue;
					}
					if (field.isAccessible()) {
						Object value = field.get(source);
						json.put(field.getName(), value);
					} else {
						field.setAccessible(true);
						Object value = field.get(source);
						json.put(field.getName(), getJsonValue(value));
						field.setAccessible(false);
					}
				}
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
