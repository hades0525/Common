package com.citycloud.ccuap.ybhw.util;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author meiming_mm@163.com
 * @since 2019/11/14
 */
public class BeanUtil {


    public static <T, E> T toEntity(E e, Class<T> clazz) {


        String s = JsonUtil.bean2Json(e);
        T t = JsonUtil.json2Bean(s, clazz);


        return t;
    }

    public static <T> List<Field>  getAllFields(Class<T> tClass){

        Class tempClass = tClass;

        List<Field> fields = new ArrayList<>();

        while (tempClass!=null){

            Field[] declaredFields = tempClass.getDeclaredFields();

            fields.addAll(Arrays.asList(declaredFields));

            tempClass = tempClass.getSuperclass();

            Field field;


        }
        return fields;



    }

}
