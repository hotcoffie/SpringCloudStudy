package com.xieyu.ribbonconsumer.controller;

import com.xieyu.ribbonconsumer.entity.User;
import com.xieyu.ribbonconsumer.service.RibbonService;
import com.xieyu.ribbonconsumer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:测试发现服务，消费服务
 *
 * @author 谢宇
 * Date: 2019/3/12 9:53
 */
@RestController
public class ConsumerController {

    @Autowired
    private RibbonService ribbonService;
    @Autowired
    private UserService userService;

    @GetMapping("/restTemplate")
    public String testRestTemplate() {
        return ribbonService.testRestTemplate();
    }

    @GetMapping("/webClient")
    public String testWebClient() {
        return ribbonService.testWebClient();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable String id) {
        return userService.find(id);
    }

    @GetMapping("/users")
    public List<User> getUser(@RequestParam List<String> ids) {
        return userService.findAll(ids);
    }
}
