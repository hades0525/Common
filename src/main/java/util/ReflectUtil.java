package com.citycloud.ccuap.ybhw.util;


import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 反射工具类
 *
 * @author meiming_mm@163.com
 */
@Slf4j
public class ReflectUtil {

    /**
     * 反射调用指定构造方法创建对象
     *
     * @param clazz    对象类型
     * @param argTypes 参数类型
     * @param args     构造参数
     * @return 返回构造后的对象
     */
    public static <T> T invokeConstructor(Class<T> clazz, Class<?>[] argTypes,
                                          Object[] args) {
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor(argTypes);
            return constructor.newInstance(args);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            log.error("reflect error", e);
        }

        return null;
    }

    /**
     * 反射调用指定对象属性的getter方法
     *
     * @param <T>       泛型
     * @param target    指定对象
     * @param fieldName 属性名
     * @return 返回调用后的值
     */
    public static <T> Object getter(T target, String fieldName) {

        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, target.getClass());
            Method rM = pd.getReadMethod();
            return rM.invoke(target);
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            log.error("reflect error", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用指定对象属性的setter方法
     *
     * @param <T>       泛型
     * @param target    指定对象
     * @param fieldName 属性名
     * @param args      参数列表
     */
    public static <T> void setter(T target, String fieldName, Object args) {

        Class<?> clazz = target.getClass();

        try {

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, args);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("reflect error", e);
        }

    }


}