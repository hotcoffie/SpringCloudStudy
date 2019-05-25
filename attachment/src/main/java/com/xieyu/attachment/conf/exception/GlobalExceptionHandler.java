package com.xieyu.attachment.conf.exception;

import com.xieyu.attachment.entity.RestMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Description: 全局异常处理
 *
 * @author 谢宇
 * Date: 2018/11/9 19:46
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());
    /**
     * 软件当前状态
     */
    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 声明要捕获的异常
     *
     * @param e 捕获到的异常
     * @return 错误提示信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestMessage defultExcepitonHandler(Exception e) {
        RestMessage msg;
        if (e instanceof ApplicationException) {
            ApplicationException ae = (ApplicationException) e;
            logger.error("业务异常：" + ae.getMessage(), ae);
            msg = RestMessage.error(RestMessage.APP_EXCEPTION, e.getMessage());
        } else {
            //未知错误
            logger.error("系统异常：" + e.getMessage(), e);
            msg = RestMessage.error(RestMessage.SYS_EXCEPTION, "操作失败，请稍后再试！");
        }
        if ("dev".equals(active) || "test".equals(active)) {
            //测试模式下返回详细错误信息到前台
            msg.setData(e.getMessage());
        }
        return msg;
    }

}
