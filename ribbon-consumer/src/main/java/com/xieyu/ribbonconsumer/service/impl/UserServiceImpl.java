package com.xieyu.ribbonconsumer.service.impl;

import com.xieyu.ribbonconsumer.entity.User;
import com.xieyu.ribbonconsumer.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/26 14:56
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public User find(String id) {
        return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}", User.class, id);
    }

    @Override
    public List<User> findAll(List<String> ids) {
        return restTemplate.getForObject("http://HELLO-SERVICE/users?ids={1}", List.class, StringUtils.join(ids, ","));
    }
}
