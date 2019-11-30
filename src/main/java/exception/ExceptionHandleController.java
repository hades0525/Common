/** 
 * @ClassName: ExceptionHandleController 
 * @Description: TODO
 * @author: sb
 * @date: 2019年11月18日 上午10:53:35 
 *  
 */
package com.citycloud.ccuap.ybhw.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citycloud.ccuap.ybhw.wapper.RWrapper;

import lombok.extern.slf4j.Slf4j;

/** 
 * @ClassName: ExceptionHandleController 
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月18日 上午10:53:35 
 *  
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandleController {
	
	/**
	 * 错误码
	 */
    private static final int ERROR_CODE = 999;

    /**
     * 统一捕获异常接口
     * @param ex 抛出封装后异常
     * @return 统一封装返回错误异常信息
     */
    @ExceptionHandler({Exception.class})
    public RWrapper exception(Exception ex) {
    	log.error("----------出错啦！！！--------" + ex.getMessage());
    	ex.printStackTrace();
        return resultFormat(ERROR_CODE, new Exception("信息出错啦！！！！！"));
    }

    private <T extends Throwable> RWrapper resultFormat(Integer code, T ex) {
        ex.printStackTrace();
        return new RWrapper(code, ex.getMessage());
    }
}
