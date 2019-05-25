package com.xieyu.eurekaclient.controller;

import com.xieyu.eurekaclient.entity.User;
import com.xieyu.eurekaclient.result.RestMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/27 8:37
 */
@RestController
@Api(tags = "用户接口")
public class UserController {
    @ApiOperation("根据用户ID获取用户信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "用户ID",
                    paramType = "path", required = true, dataType = "String", defaultValue = "1")
    )
    @GetMapping("/users/{id}")
    public RestMessage<User> getUser(@PathVariable String id) {
        User user = new User(id, "用户" + id);
        return RestMessage.success(user);
    }

    @ApiOperation("根据一组用户ID获取一组用户信息")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "ids", value = "一组逗号分隔的用户ID",
                    paramType = "query", required = true, dataType = "String", defaultValue = "1")
    )
    @GetMapping("/users")
    @Validated
    public RestMessage<List<User>> getUsers(@RequestParam @NotNull List<String> ids) {
        List<User> userList = new ArrayList<>();
        for (String id : ids) {
            User user = new User(id, "用户" + id);
            userList.add(user);
        }
        return RestMessage.success(userList);
    }
}
