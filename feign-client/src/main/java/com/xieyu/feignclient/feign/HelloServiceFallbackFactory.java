package com.xieyu.feignclient.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/24 22:10
 */
@Component
@Slf4j
public class HelloServiceFallbackFactory implements FallbackFactory<HelloService> {

    @Override
    public HelloService create(Throwable cause) {
        return new HelloService() {
            @Override
            public String sayHello(String name) {
                log.error("服务查询失败！", cause);
                return "Hi!";
            }
        };
    }
}
