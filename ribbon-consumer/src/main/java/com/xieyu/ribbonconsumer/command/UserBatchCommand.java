package com.xieyu.ribbonconsumer.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xieyu.ribbonconsumer.entity.User;
import com.xieyu.ribbonconsumer.service.UserService;

import java.util.List;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/26 14:55
 */

public class UserBatchCommand extends HystrixCommand<List<User>> {
    private UserService userService;
    private List<String> ids;

    public UserBatchCommand(UserService userService, List<String> ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
        this.userService = userService;
        this.ids = ids;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.findAll(ids);
    }
}
