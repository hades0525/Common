/** 
 * @ClassName: PageUtils 
 * @Description: TODO
 * @author: sb
 * @date: 2019年11月20日 上午11:07:37 
 *  
 */
package com.citycloud.ccuap.intellisense.utils;

import java.util.Map;

import com.citycloud.ccuap.intellisense.entity.BaseEntity;
import com.citycloud.ccuap.intellisense.entity.QueryEntity;
import com.citycloud.ccuap.ybhw.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/** 
 * @ClassName: PageUtils 
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月20日 上午11:07:37 
 *  
 */
@Slf4j
public class PageUtils {

	
	public static void getPageParam(QueryEntity params){
		log.warn("-----解析分页参数------" + params);
		Integer pageNum = params.getPage();
		Integer pageSize = params.getSize();
		if(pageNum >=1 && pageSize > 0){
			params.setOffsetNum((pageNum-1)*pageSize);
		}else{
			log.error("-------解析分页参数失败，使用默认参数------" + params);
			params.setPage(1);
			params.setSize(10);
			params.setOffsetNum(0);
		}
		//拼接日期格式
		String startTime = StringUtils.valueOf(params.getStartTime()).trim();
		String endTime = StringUtils.valueOf(params.getEndTime()).trim();
		params.setStartTime(startTime.length()==0?"":startTime+" 00:00:00");
		params.setEndTime(endTime.length()==0?"":endTime+" 23:59:59");
	}
	
	
	public static void getPageParam(BaseEntity params){
		log.warn("-----解析分页参数------" + params);
		Integer pageNum = params.getPage();
		Integer pageSize = params.getSize();
		if(pageNum >=1 && pageSize > 0){
			params.setOffsetNum((pageNum-1)*pageSize);
		}else{
			log.error("-------解析分页参数失败，使用默认参数------" + params);
			params.setPage(1);
			params.setSize(10);
			params.setOffsetNum(0);
		}
	}
	
	public static void getPageParam(Map<String,Object> params){
		log.warn("-----解析分页参数------" + params);
		Object pageNum = params.get("page");
		Object pageSize = params.get("size");
		if(StringUtils.isNumeric(pageNum) && StringUtils.isNumeric(pageSize)){
			params.put("offsetNum", ((Integer)pageNum-1)*(Integer)pageSize);
		}else{
			log.error("-------解析分页参数失败，使用默认参数------" + params);
			params.put("page", 1);
			params.put("size", 10);
			params.put("offsetNum", 0);
		}
	}

	/**
	 * @param params
	 * @param countInfos
	 * @param size
	 */
	public static void getPageInfo(Map<String, Object> params, int countInfos, int size) {
		// TODO Auto-generated method stub
		Object pageNum = params.get("page")==null?1:params.get("page");
		Object pageSize = params.get("size")== null ? 1 :params.get("size");
		params.put("totalElements", countInfos);
		params.put("numberOfElements", size);
		if(!StringUtils.isNumeric(pageNum) || !StringUtils.isNumeric(pageSize)){
			return;
		}
		
		Integer num = Integer.valueOf(StringUtils.valueOf(pageNum));
		Integer pSize = Integer.valueOf(StringUtils.valueOf(pageSize));
		int totalP = countInfos%pSize == 0?countInfos/pSize:countInfos/pSize+1;
		params.put("totalPages", totalP);
		if(num == 1){
			params.put("first", true);
		}else{
			params.put("first", false);
		}
		
		if(num == totalP){
			params.put("last", true);
		}else{
			params.put("last", false);
		}
	}
}
