package com.xieyu.attachment.conf;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Description: springboot集成mybatis的基本入口
 *
 * @author 谢宇
 * Date: 2019/4/11 9:52
 */
@Configuration
@MapperScan(basePackages = {"com.xieyu.*.mapper"})
@EnableTransactionManagement
public class MyBatisConfig {

    /**
     * 配置mybatis的分页插件pageHelper
     */
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        // 如果 pageSize=0 或者 RowBounds.limit = 0 就会查询出全部的结果
        // （相当于没有执行分页查询，但是返回结果仍然是 Page 类型）
        properties.setProperty("reasonable", "true");
        //配置mysql数据库的方言
        properties.setProperty("dialect", "mysql");
        properties.setProperty("helperDialect", "mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

}