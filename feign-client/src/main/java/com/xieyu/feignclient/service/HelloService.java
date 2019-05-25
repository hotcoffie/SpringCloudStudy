package com.xieyu.feignclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/20 10:50
 */
@Service
public class HelloService {
    @Autowired
    private com.xieyu.feignclient.feign.HelloService eurekaClientFeign;
    public String sayHello(String name){
        return eurekaClientFeign.sayHello(name);
    }
}
