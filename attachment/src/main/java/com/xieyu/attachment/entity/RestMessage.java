package com.xieyu.attachment.entity;

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
public class RestMessage implements Serializable {
    /**
     * 系统异常
     */
    public static final int SYS_EXCEPTION = 1;
    /**
     * 业务异常
     */
    public static final int APP_EXCEPTION = 2;
    /**
     * 业务异常
     */
    public static final int SUCCESS = 3;

    @ApiModelProperty(value = "状态码，1系统异常2业务异常3成功")
    private Integer code = 2;
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty(value = "返回对象")
    private Object data;

    /**
     * 返回成功数据
     *
     * @param data 返回对象
     */
    public static RestMessage success(Object data) {
        RestMessage rmsg = new RestMessage();
        rmsg.setCode(SUCCESS);
        rmsg.setData(data);
        return rmsg;
    }


    /**
     * 返回失败数据
     *
     * @param e       错误对象（active=test时会把这个对象传给前台便于调试错误）
     * @param errCode 错误编号
     * @param msg     错误信息
     */
    public static RestMessage error(Integer errCode, String msg, Exception e) {
        RestMessage rmsg = new RestMessage();
        rmsg.setMessage(msg);
        rmsg.setCode(errCode);
        rmsg.data = e;
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
        rmsg.setMessage(msg);
        rmsg.setCode(errCode);
        return rmsg;
    }
}