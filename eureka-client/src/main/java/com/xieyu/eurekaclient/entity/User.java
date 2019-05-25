package com.xieyu.eurekaclient.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/27 8:43
 */
@Data
@RequiredArgsConstructor
public class User {
    @NonNull
    String id;
    @NonNull
    String userName;
}
