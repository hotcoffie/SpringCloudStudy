package com.xieyu.feignclient.feign;

import com.xieyu.feignclient.conf.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/20 10:37
 */

@FeignClient(value = "hello-service", configuration = FeignConfig.class, fallbackFactory = HelloServiceFallbackFactory.class)
public interface HelloService {
    @GetMapping("/hello")
    String sayHello(@RequestParam(value = "name") String name);
}
