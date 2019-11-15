package com.citycloud.ccuap.ybhw.wapper;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author meiming_mm@163.com
 * @since 2019/11/12
 */

@Data
@NoArgsConstructor
@ApiModel("统一响应结果封装")
public class RWrapper {


    private boolean status;

    private Object data;

    private ErrorWrapper error;

    /**
     * 成功返回数据包
     *
     * @param data 数据包
     * @param <T>  业务对象
     */
    public <T> RWrapper(T data) {
        this.data = data;
        this.status = true;
    }

    public RWrapper(SWrapper sWrapper) {

        this.status = sWrapper.isSuccess();
        if (sWrapper.isSuccess()) {
            this.data = sWrapper.getData();
        } else {
            writeError(sWrapper.getCode(), sWrapper.getMessage());
        }
    }

    /**
     * 失败返回信息
     *
     * @param code    错误码
     * @param message 错误信息
     */
    public RWrapper(int code, String message) {
        writeError(code, message);
    }

    private void writeError(int code, String message) {
        this.status = false;
        this.error = new ErrorWrapper();
        this.error.setStatusCode(code);
        this.error.setMessage(message);
    }


    public static RWrapper wrap(SWrapper sWrapper) {
        return new RWrapper(sWrapper);
    }

    public static <T> RWrapper ok(T t) {
        return new RWrapper(t);
    }

    public static RWrapper error(String message) {
        return new RWrapper(ErrorWrapper.FAILED_CODE, message);
    }

}
