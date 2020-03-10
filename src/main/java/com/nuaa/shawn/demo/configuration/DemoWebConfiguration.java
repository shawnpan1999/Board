package com.nuaa.shawn.demo.configuration;

import com.nuaa.shawn.demo.interceptor.AddMessageInterceptor;
import com.nuaa.shawn.demo.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * 把拦截器注册到链路中
 */
@Component
public class DemoWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    HandlerInterceptor addMessageInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //！！！拦截器注册的顺序一定要注意！
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(addMessageInterceptor).addPathPatterns("/addmsg*");
        super.addInterceptors(registry);
    }
}
