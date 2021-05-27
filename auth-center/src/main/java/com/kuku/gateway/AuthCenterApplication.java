package com.kuku.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 1、如果不需要nacos需要注释掉否则会自动连接nacos
 * 2、下面是不加载WebSecurityConfigurerAdapter，以为在LoginConfigurer继承了他会加载一次，防止二次加载也可以在yml中增加参数覆盖
 * @author wxf
 */
@SpringBootApplication
@ComponentScan(excludeFilters =
        {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurerAdapter.class)})
public class AuthCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthCenterApplication.class, args);
    }

}
