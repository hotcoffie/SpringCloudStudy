package com.xieyu.ribbonconsumer.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.xieyu.ribbonconsumer.entity.User;
import com.xieyu.ribbonconsumer.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/27 16:18
 */

public class UserCollapseCommand extends HystrixCollapser<List<User>, User, String> {
    private UserService userService;
    private String id;

    public UserCollapseCommand(UserService userService, String id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userServiceCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userService = userService;
        this.id = id;
    }

    @Override
    public String getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, String>> collapsedRequests) {
        List<String> ids = new ArrayList<>(collapsedRequests.size());
        ids.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService, ids);
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, String>> collapsedRequests) {

    }
}
