package com.xieyu.ribbonconsumer.service;

import com.xieyu.ribbonconsumer.entity.User;

import java.util.List;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/26 14:55
 */

public interface UserService {
    User find(String id);

    List<User> findAll(List<String> ids);
}
