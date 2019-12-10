package com.citycloud.ccuap.ybhw.model.vo.resp;

import lombok.Data;

/**
 * @author tenglinzhi886@gmail.com
 * @date 2019/12/5
 */

@Data
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回对象
     */
    private T data;

    public ResponseResult() {
        super();
    }

    public ResponseResult(Integer status) {
        super();
        this.status = status;
    }

    public ResponseResult(Integer status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public ResponseResult(Integer status, Throwable throwable) {
        super();
        this.status = status;
        this.message = throwable.getMessage();
    }

    public ResponseResult(Integer status, T data) {
        super();
        this.status = status;
        this.data = data;
    }

    public ResponseResult(Integer status, String message, T data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ResponseResult<?> other = (ResponseResult<?>) obj;
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (status == null) {
            if (other.status != null) {
                return false;
            }
        } else if (!status.equals(other.status)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseResult [status=" + status + ", message=" + message + ", data=" + data + "]";
    }
}