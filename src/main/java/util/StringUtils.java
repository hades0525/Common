/** 
 * @ClassName: StringUtils 
 * @Description: TODO
 * @author: sb
 * @date: 2019年11月14日 上午10:44:48 
 *  
 */
package com.citycloud.ccuap.ybhw.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: StringUtils
 * @Description: TODO
 * @author: Hyman-->guonq@citycloud.com.cn
 * @date: 2019年11月14日 上午10:44:48
 * 
 */
public class StringUtils {

	
	
	public static void main(String[] args) {
		List<String> strs = new ArrayList<>();
		strs.add("base64_1");
		strs.add("base64_2");
		
		Map<String,Object> map = new HashMap<>();
		map.put("tf_device_id", "12123123");
		map.put("Warn_time", "2011-11-11 12:12:12");
		map.put("Warn_type", "1000");
		map.put("Warn_addr", "宜宾");
		map.put("Warn_entity", "沙县小吃");
		map.put("Warn_img", strs);
		
		Map<String,Object> map1 = new HashMap<>();
		map1.put("tf_device_id", "12123123");
		map1.put("Warn_time", "2011-11-11 12:12:12");
		map1.put("Warn_type", "1000");
		map1.put("Warn_addr", "宜宾");
		map1.put("Warn_entity", "川A88888");
		map1.put("Warn_img", strs);
		
		
		Map<String,Object> map2 = new HashMap<>();
		map2.put("tf_device_id", "12123123");
		map2.put("Warn_time", "2011-11-11 12:12:12");
		map2.put("Warn_type", "1000");
		map2.put("Warn_addr", "宜宾");
		map2.put("Warn_entity", "乱丢垃圾");
		map2.put("Warn_img", strs);
		
		 List<Map<String,Object>> list = new ArrayList<>();
		 list.add(map);
		 list.add(map1);
		 list.add(map2);
		 
		 JSONObject json = new JSONObject();
		 json.put("datas",list);
		 json.put("code", 200);
		 json.put("msg", "SUCCESS");
		 
		 System.out.println(json.toJSONString());
		
	}
	
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * @param obj
	 * @return
	 */
	public static String valueOf(Object obj) {
		// TODO Auto-generated method stub
		return obj == null ?"":String.valueOf(obj);
	}
	
	/**
	 * @param obj
	 * @return
	 */
	public static Boolean isNumeric(Object obj) {
		// TODO Auto-generated method stub
		if(null == obj){
			return false;
		}
		Integer i = null;
		try {
			i = Integer.valueOf(valueOf(obj));
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		return null == i?false:true;
	}
	
	

    /**
     * 过滤掉中文
     * @param str 待过滤中文的字符串
     * @return 过滤掉中文后字符串
     */
    public static String filterChinese(String str) {
        // 用于返回结果
        String result = str;
        boolean flag = isContainChinese(str);
        if (flag) {// 包含中文
            // 用于拼接过滤中文后的字符
            StringBuffer sb = new StringBuffer();
            // 用于校验是否为中文
            boolean flag2 = false;
            // 用于临时存储单字符
            char chinese = 0;
            // 5.去除掉文件名中的中文
            // 将字符串转换成char[]
            char[] charArray = str.toCharArray();
            // 过滤到中文及中文字符
            for (int i = 0; i < charArray.length; i++) {
                chinese = charArray[i];
                flag2 = isChinese(chinese);
                if (!flag2) {// 不是中日韩文字及标点符号
                    sb.append(chinese);
                }
            }
            result = sb.toString();
        }
        return result;
    }


    /**
     * 校验一个字符是否是汉字
     *
     * @param c
     *  被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChineseChar(char c) {
        try {
            return String.valueOf(c).getBytes("UTF-8").length > 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 验证字符串内容是否包含下列非法字符<br>
     * `~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆
     *
     * @param content
     *  字符串内容
     * @return 't'代表不包含非法字符，otherwise代表包含非法字符。
     */
    public static char validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
        char isLegalChar = 't';
        L1: for (int i = 0; i < content.length(); i++) {
            for (int j = 0; j < illegal.length(); j++) {
                if (content.charAt(i) == illegal.charAt(j)) {
                    isLegalChar = content.charAt(i);
                    break L1;
                }
            }
        }
        return isLegalChar;
    }

    /**
     * 验证是否是汉字或者0-9、a-z、A-Z
     *
     * @param c
     *  被验证的char
     * @return true代表符合条件
     */
    public static boolean isRightChar(char c) {
        return isChinese(c) || isWord(c);
    }

    /**
     * 校验某个字符是否是a-z、A-Z、_、0-9
     *
     * @param c
     *  被校验的字符
     * @return true代表符合条件
     */
    public static boolean isWord(char c) {
        String regEx = "[\\w]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("" + c);
        return m.matches();
    }

    /**
     * 判定输入的是否是汉字
     *
     * @param c
     *  被校验的字符
     * @return true代表是汉字
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 校验String是否全是中文
     *
     * @param name
     *  被校验的字符串
     * @return true代表全是汉字
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }
    
    /**
     * 判断对象是否为空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        boolean result = false;
        if (o == null) {
            result = true;
        } else {
            if ("".equals(o.toString())) {
                result = true;
            }
        }
        return result;
    }
    
    /**
     * 判断对象是否为空
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }
    
    /**
     * 判断字符串是否全部都为小写
     *
     * @param value
     *                 待判断的字符串
     * @return
     */
    public static boolean isAllLowerCase(String value){
        if(value == null || "".equals(value)){
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (Character.isLowerCase(value.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否全部大写
     *
     * @param value
     *                 待判断的字符串
     * @return
     */
    public static boolean isAllUpperCase(String value){
        if(value == null || "".equals(value)){
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (Character.isUpperCase(value.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 反转字符串
     *
     * @param value
     *                 待反转的字符串
     * @return
     */
    public static String reverse(String value){
        if(value == null){
            return null;
        }
        return new StringBuffer(value).reverse().toString();
    }
}
