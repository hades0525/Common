/** 
 * @ClassName: SpringBeanUtils 
 * @Description: TODO
 * @author: sb
 * @date: 2019年11月21日 下午6:18:38 
 *  
 */
package com.citycloud.ccuap.ybhw.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName: SpringBeanUtils
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月21日 下午6:18:38
 * 
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		SpringContextUtils.applicationContext = arg0;
	}

	public static <T> T getBean(String beanName) {
		if (applicationContext.containsBean(beanName)) {
			return (T) applicationContext.getBean(beanName);
		} else {
			return null;
		}
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {
		return applicationContext.getBeansOfType(baseType);

	}
}
