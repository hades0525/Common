/** 
 * @ClassName: GlobalLogManage 
 * @Description: TODO
 * @author: sb
 * @date: 2019年11月23日 下午5:25:08 
 *  
 */
package com.citycloud.ccuap.ybhw.config;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: GlobalLogManage
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月23日 下午5:25:08
 * 
 */
@Component
@Slf4j
@Aspect
public class GlobalLogManage {

	/**
	 * 定义切点
	 */
	@Pointcut("execution(public * com.citycloud.ccuap.ybhw.controller.*.*(..))")
	private void controllerAspect() {
	}

	/**
	 * 打印请求信息
	 * @param joinPoint
	 */
	@Before(value = "controllerAspect()")
	public void methodBefore(JoinPoint joinPoint) {
		log.info("---------Request data ：---------");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		try {
			// 开始打印请求内容
			log.info("request addr:" + request.getRequestURL().toString());
			log.info("request method:" + request.getMethod());
			log.info("ququest class detail:" + joinPoint.getSignature());
			log.info("request parameter:" + JSONObject.toJSONString(joinPoint.getArgs(), true));
		} catch (Exception e) {
			log.error("[LogAspectServiceApi.class methodBefore()] ERROR:", e);
		}
		log.info("---------end request data ---------");
	}

		/**
		 * 打印响应信息
		 * @param o
		 */
		@AfterReturning(returning = "o", pointcut = "controllerAspect()")
		public void methodAfterReturing(Object o) {
			//开始打印响应内容
			log.info("--------------response data----------------");
			try {
				log.info("Response data:" + o);
			} catch (Exception e) {
				log.error("[LogAspectServiceApi.class methodAfterReturing()] ERROR:", e);
			}
			log.info("--------------end response data----------------");
		}
}
