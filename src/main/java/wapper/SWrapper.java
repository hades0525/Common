package com.citycloud.ccuap.ybhw.wapper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * service 层需封包返回处理
 *
 * @author meiming_mm@163.com
 * @since 2019/11/14
 */
@NoArgsConstructor
@Data
public class SWrapper<T> {

    public final static int SUCCESS_CODE = 200;

    public final static int FAILED_CODE = 500;


    private int code;

    private T data;

    private String message;

    public boolean isSuccess() {

        return this.code == SUCCESS_CODE;

    }

    public static <T> SWrapper<T> ok(T t) {
        SWrapper<T> wrapper = new SWrapper<>();
        wrapper.setData(t);
        wrapper.setCode(SUCCESS_CODE);
        return wrapper;
    }

    public static <T> SWrapper<T> error() {
        SWrapper<T> wrapper = new SWrapper<>();
        wrapper.setCode(FAILED_CODE);
        wrapper.setMessage("操作失败");
        return wrapper;
    }
}
