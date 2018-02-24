package com.bbg.open.b2b4pos.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * 工具类
 * @author kevin
 *
 */
public class SpringFactory implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	public void setBeanFactory(BeanFactory factory) throws BeansException {
		SpringFactory.beanFactory = factory;
	}

	/**
	 * 根据beanName名字取得bean
	 * 
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(beanName);
		}
		return null;
	}
	
	/**
	 * 根据beanName名字取得bean
	 * 
	 * @param beanName
	 * @return
	 */
	public static <T> T getBean(Class<T> clz) {
		if (null != beanFactory) {
			return (T) beanFactory.getBean(clz);
		}
		return null;
	}

}