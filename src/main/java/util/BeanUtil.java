package com.citycloud.ccuap.ybhw.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author meiming_mm@163.com
 * @since 2019/11/14
 */
public class BeanUtil {


    public static <T, E> T toEntity(E e, Class<T> clazz) {
        T t = null;
        try {
            Constructor<T> constructor = clazz.getConstructor();
            t = constructor.newInstance();

            Field[] fields = clazz.getDeclaredFields();

            Field[] declaredFields = e.getClass().getDeclaredFields();
            for (Field field : declaredFields) {

                ReflectUtil.setter(t,field.getName(),ReflectUtil.getter(e,field.getName()));
            }


        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
            e1.printStackTrace();
        }

        return t;


    }

}
