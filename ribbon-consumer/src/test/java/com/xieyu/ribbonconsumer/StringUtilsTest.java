package com.xieyu.ribbonconsumer;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author 谢宇
 * Date: 2019/3/26 14:41
 */

public class StringUtilsTest {
    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        String result = StringUtils.join(ids, ",");
        System.out.println(result);
    }
}
