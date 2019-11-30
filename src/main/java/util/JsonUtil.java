package com.citycloud.ccuap.ybhw.util;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Json 工具类
 *
 * @author meiming_mm@163.com
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static ObjectMapper objectMapperNonNull;


    /**
     * json convert to bean
     */
    public static <T> T json2Bean(String json, Class<T> clazz) {

        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            T value = objectMapper.readValue(json, clazz);

            return value;
        } catch (Exception e) {
            log.error("json2Bean:{}", "convert error", e);
        }
        return null;
    }

    /**
     * json convert to Collection<Bean>
     */
    public static <T> List<T> json2Bean(String json,
                                        Class<? extends List> collectionClass, Class<T> clazz) {

        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            CollectionType collectionType = objectMapper.getTypeFactory()
                    .constructCollectionType(collectionClass, clazz);

            return objectMapper.readValue(json, collectionType);

        } catch (IOException e) {

            log.error("json2Bean:{}", "convert error", e);

        }
        return null;
    }

    /**
     * json convert
     */
    /**
     * @param javaType constructParametricType
     */
    public static <T> T json2Bean(String json, JavaType javaType) {

        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(json, javaType);

        } catch (IOException e) {

            log.error("json2Bean:{}", "convert error", e);

        }
        return null;
    }


    /**
     * bean convert to json(String)
     */
    public static String bean2Json(Object object) {
        return bean2Json(object, true);
    }

    public static String bean2Json(Object object,boolean nonNull,boolean orderKey) {
        if(orderKey){

            if(nonNull){
                if(objectMapperNonNull==null){
                    objectMapperNonNull= new ObjectMapper();
                    objectMapperNonNull.setSerializationInclusion(Include.NON_NULL);
                }
                objectMapperNonNull.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            }else{
                objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            }
        }
        return bean2Json(object,nonNull);
    }

    /**
     * bean convert to json(String)
     *
     * @param nonNull 是否转换为Null的字段
     */
    public static String bean2Json(Object object, boolean nonNull) {

        try {
            if (nonNull) {
                if(objectMapperNonNull==null){
                    objectMapperNonNull= new ObjectMapper();
                    objectMapperNonNull.setSerializationInclusion(Include.NON_NULL);
                }
                return objectMapperNonNull.writeValueAsString(object);
            }else{

                return objectMapper.writeValueAsString(object);
            }


        } catch (Exception e) {

            log.error("bean2Json:{}", "to json  error", e);

            return null;
        }
    }

    /**
     * @param parametrized
     * @param parameterClasses
     * @return
     */
    public static JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return objectMapper.getTypeFactory()
                .constructParametricType(parametrized, parameterClasses);
    }

}