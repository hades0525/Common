package com.citycloud.ccuap.ybhw.controller;

import com.citycloud.ccuap.ybhw.common.DeleteType;
import com.citycloud.ccuap.ybhw.common.OperateType;
import com.citycloud.ccuap.ybhw.model.Model;
import com.citycloud.ccuap.ybhw.model.dto.BaseDto;
import com.citycloud.ccuap.ybhw.util.BeanUtil;
import com.citycloud.ccuap.ybhw.util.DateUtil;
import com.citycloud.ccuap.ybhw.util.ReflectUtil;
import com.citycloud.ccuap.ybhw.uuid.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author meiming_mm@163.com
 * @since 2019/11/12
 */
public class BaseController {


    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 只做基础转换 dto的元素转换需自己操作
     * VO to DO (RETURN DTO)
     *
     * @param vo     controller 接收传入值 VO
     * @param mClass 对应的 domain class
     * @param tClass 对应的 DTO class
     * @return DTO
     */
    protected <E, M extends Model, T extends BaseDto<M>> T toDto(E vo, Class<M> mClass, Class<T> tClass, int operType) {


        M m = BeanUtil.toEntity(vo, mClass);

        T t = null;

        try {

            t = tClass.getConstructor().newInstance();
            t.setCurrentUser(null);
            // TODO: 2019/11/17
            String userName = null;

            List<Field> fields = BeanUtil.getAllFields(mClass);
            Field idField = null;
            for (Field field : fields) {
                //新增时  自动生成ID
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                    break;
                }
            }

            if (idField != null) {

                List<Field> voFields = BeanUtil.getAllFields(vo.getClass());

                for (Field field : voFields) {
                    //映射 vo ->do ID字段不一致的情况
                    if (field.isAnnotationPresent(Id.class)) {
                        ReflectUtil.setter(m, idField.getName(), ReflectUtil.getter(vo,field.getName()));
                        break;
                    }
                }
            }

            //新增公共逻辑
            if ((OperateType.ADD & operType) > 0) {

                if (idField != null) {
                    ReflectUtil.setter(m, idField.getName(), UUIDGenerator.nextId());
                }

                m.setCreatUser(userName);
                m.setCreatTime(DateUtil.getCurrentDateTime());
                m.setDelFlag(DeleteType.NORMAL);

                m.setUpdateTime(DateUtil.getCurrentDateTime());
                m.setUpdateUser(userName);
            }

            //编辑公共逻辑
            if ((OperateType.EDIT & operType) > 0) {

                m.setUpdateTime(DateUtil.getCurrentDateTime());
                m.setUpdateUser(userName);

            }
            //删除公共逻辑
            if ((OperateType.DELETE & operType) > 0) {

                m.setDelUser(userName);
                m.setUpdateTime(DateUtil.getCurrentDateTime());
                m.setDelFlag(DeleteType.DELETED);
            }

            t.setRecord(m);


        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return t;

    }


    protected <E,T> T toVO(E e,Class<T> tClass){
        return BeanUtil.toEntity(e,tClass);
    }
    protected <E,T> List<T> toListVO(List<E> es,Class<T> tClass){
        if(es!=null){
            List<T> dataList = new ArrayList<>();
            for (E e : es) {
                T t = BeanUtil.toEntity(e, tClass);
                if(t!=null){
                    dataList.add(t);
                }
            }
            return dataList;
        }else{
            return null;
        }
    }


}
