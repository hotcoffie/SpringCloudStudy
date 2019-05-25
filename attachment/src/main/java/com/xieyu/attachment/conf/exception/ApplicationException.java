package com.xieyu.attachment.conf.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 自定义应用异常
 *
 * @author 谢宇
 * Date: 2018/11/9 18:48
 */
@Getter
@Setter
public class ApplicationException extends RuntimeException {

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException(String msg, Exception e) {
        super(msg, e);
    }
}
