package com.xieyu.eurekaclient.controller;

import com.xieyu.eurekaclient.result.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:测试提供服务
 *
 * @author 谢宇
 * Date: 2019/3/11 22:11
 */
@RestController
@Log
@Api(tags = "hello接口")
public class HelloController {
    @Autowired
    private DiscoveryClient client;

    @ApiOperation("后台打印服务提供者信息")
    @GetMapping("/index")
    public String index() {
        List<String> services = client.getServices();
        for (String service : services) {
            log.info(service);
        }
        List<ServiceInstance> instances = client.getInstances("hello-service");
        for (ServiceInstance instance : instances) {
            log.info(instance.getHost());
            log.info(instance.getServiceId());
        }
        return "Hello World";
    }

    @ApiOperation("报名字打招呼")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "name", value = "提供打招呼的姓名", paramType = "query", required = true, dataType = "String", defaultValue = "Harry")
    )
    @GetMapping("/hello")
    public RestMessage sayHello(@RequestParam(defaultValue = "Anonymous") String name) {
        return RestMessage.success("Hello " + name);
    }
}
