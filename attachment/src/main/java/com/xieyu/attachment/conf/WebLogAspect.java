package com.xieyu.attachment.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Description: 所有请求的切片日志
 *
 * @author 谢宇
 * Date: 2018/11/13 16:39
 **/
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    private final ObjectMapper mapper;

    public WebLogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Pointcut("execution(public * com.xieyu.attachment.controller..*.*(..))")//两个..代表所有子目录，最后括号里的两个..代表所有参数
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        log.info("请求地址 : " + request.getRequestURL().toString());
        log.info("HTTP METHOD : " + request.getMethod());
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(
            //returning的值和doAfterReturning的参数名一致
            returning = "ret",
            pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容(返回值太复杂时，打印的是物理存储空间的地址)
        if (ret instanceof OutputStream||ret instanceof ResponseEntity) {
            log.info("数据以I/O流的形式返回");
        } else {
            log.info("返回值 : " + mapper.writeValueAsString(ret));
        }
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        // ob 为方法的返回值
        Object ob = pjp.proceed();
        log.info("耗时 : " + (System.currentTimeMillis() - startTime));
        return ob;
    }
}
