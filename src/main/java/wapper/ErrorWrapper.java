package com.citycloud.ccuap.ybhw.wapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author meiming_mm@163.com
 * @since 2019/11/12
 */
@Data
@NoArgsConstructor
@ApiModel("错误结果封装")
public class ErrorWrapper {


    public final static int FAILED_CODE = 500;

    @ApiModelProperty("错误代码")
    private int statusCode;

    @ApiModelProperty("错误信息")
    private String message;

}
