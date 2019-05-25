package com.xieyu.feignclient.controller;

import com.xieyu.feignclient.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/20 11:30
 */
@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String sayHello(@RequestParam(defaultValue = "xiaoxie") String name) {
        return helloService.sayHello(name);
    }
}
