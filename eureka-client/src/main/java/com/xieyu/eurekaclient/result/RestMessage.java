package com.xieyu.eurekaclient.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/24 16:40
 */

@ApiModel(description = "返回响应数据")
@Data
public class RestMessage<T> implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private boolean success = true;
    @ApiModelProperty(value = "返回对象")
    private T data;
    @ApiModelProperty(value = "错误编号")
    private Integer errCode;
    @ApiModelProperty(value = "错误信息")
    private String message;

    /**
     * 返回成功数据
     *
     * @param data 返回对象
     */
    public static <T> RestMessage<T> success(T data) {
        RestMessage<T> rmsg = new RestMessage<>();
        rmsg.setSuccess(true);
        rmsg.setData(data);
        return rmsg;
    }

    /**
     * 返回失败数据
     *
     * @param errCode 错误编号
     * @param msg     错误信息
     */
    public static RestMessage error(Integer errCode, String msg) {
        RestMessage rmsg = new RestMessage();
        rmsg.setSuccess(false);
        rmsg.setMessage(msg);
        rmsg.setErrCode(errCode);
        return rmsg;
    }
}